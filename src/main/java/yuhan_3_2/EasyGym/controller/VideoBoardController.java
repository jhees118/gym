package yuhan_3_2.EasyGym.controller;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yuhan_3_2.EasyGym.entity.*;
import yuhan_3_2.EasyGym.repository.*;
import yuhan_3_2.EasyGym.service.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/menu/board")
public class VideoBoardController {

    @Autowired
    private VideoBoardService videoBoardService;
    @Autowired
    private VideoBoardRepository videoBoardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VideoHeartRepository videoHeartRepository;
    @Autowired
    private VideoHeartService videoHeartService;
    @Autowired
    private VideoCommentService videoCommentService;
    @Autowired
    private VideoCommentRepository videoCommentRepository;

    @GetMapping("/image/{id}")
    @ResponseBody
    public UrlResource gymImage(@PathVariable("id") Long id, Model model) throws IOException{

        VideoBoard file = videoBoardRepository.findById(id).orElse(null);
        return new UrlResource("file:" +file.getSavedPath());
    }



    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/video/{id}")
    public ResponseEntity<ResourceRegion> gymVideo(@PathVariable("id") Long id, @RequestHeader HttpHeaders headers) throws IOException {
        logger.debug("VideoController.getVideo");
        VideoBoard file = videoBoardRepository.findById(id).orElse(null);
        UrlResource video = new UrlResource("file:"+file.getSavedPath());
        ResourceRegion resourceRegion;

        final long chunkSize = 1000000L;
        long contentLength = video.contentLength();

        Optional<HttpRange> optional = headers.getRange().stream().findFirst();
        HttpRange httpRange;
        if (optional.isPresent()) {
            httpRange = optional.get();
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            long rangeLength = Long.min(chunkSize, end - start + 1);
            resourceRegion = new ResourceRegion(video, start, rangeLength);
        } else {
            long rangeLength = Long.min(chunkSize, contentLength);
            resourceRegion = new ResourceRegion(video, 0, rangeLength);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resourceRegion);
    }

    @GetMapping("/video-list")
    public String abs(Model model,@PageableDefault(page = 0,size = 4,sort = "videoHeartCount",direction = Sort.Direction.DESC)Pageable pageable,Authentication authentication) {
        if(authentication == null){ //사용자가 로그인중이아니면
            model.addAttribute("currentUser", 1);  //html 사용자 권한에따른 구성으로 설정값입력
        }else{
            model.addAttribute("currentUser", 0);
        }

        List<VideoBoard> videoList = videoBoardService.videoList();
        Page<VideoBoard> videoHeartList = videoBoardService.videoHeartList(pageable);//하트 순 4개정렬
        model.addAttribute("videoHeartList",videoHeartList);
        model.addAttribute("videoList",videoList);
        return "/menu/board/video-list";
    }

    @GetMapping("/video-write")
    public String videoWrite(Model model, @RequestParam(required = false) Long id,Authentication authentication){

        if(id == null) {
            model.addAttribute("videoBoard", new VideoBoard());
        } else {
            VideoBoard videoBoard = videoBoardRepository.findById(id).orElse(null);
            model.addAttribute("videoBoard",videoBoard);
        }




        return "/menu/board/video-write";
    }



    @PostMapping("/video-write")  //getMapping에서 post로변환
    public String greetingSubmitVideo(@RequestParam("file") MultipartFile file,@Valid VideoBoard videoBoard, BindingResult bindingResult, Model model, @RequestParam(required = false) Long id,
                                 Authentication authentication) throws IOException {
        if(bindingResult.hasErrors()){
            return "/menu/board/video-write";
        }

        String username = authentication.getName();



        if(id == null&& !file.isEmpty()){

            videoBoardService.saveVideoFile(file,videoBoard,username);
            model.addAttribute("message","글작성이 완료되었습니다");
        }else{
            model.addAttribute("message","영상을 업로드 해주세요");
        }





        model.addAttribute("searchUrl","/menu/board/video-list");
        return "/menu/message";
    }

    @GetMapping("/video-view")
    @Transactional
    public String getVideoView(Authentication authentication, @Valid VideoComment videoComment, BindingResult bindingResult, Model model, @RequestParam(required = false) Long id,
                              User user, Pageable pageable, VideoBoard videoBoard,HttpServletRequest request,HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "/menu/board/video-view";
        }


        videoBoard = videoBoardRepository.findById(id).get(); //조회수증가를위한 레포지토리 아이디값


        Cookie[] cookies = request.getCookies();
        int visitor = 0;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("visit")) {
                    visitor = 1;
                    if (cookie.getValue().contains(request.getParameter("id"))) {

                    } else {
                        cookie.setValue(cookie.getValue() + "_" + request.getParameter("id"));
                        response.addCookie(cookie);
                        videoBoard.setVideoViewCount(videoBoard.getVideoViewCount() + 1); //조회수 증가
                    }
                }
            }
            if (visitor == 0) {
                Cookie cookie1 = new Cookie("visit", request.getParameter("id"));
                response.addCookie(cookie1);
                videoBoard.setVideoViewCount(videoBoard.getVideoViewCount() + 1); //조회수증가
            }
        }

        if(authentication == null){ //사용자가 로그인중이아니면
            model.addAttribute("currentUser", null);  //html 사용자 권한에따른 구성으로 설정값입력

        }else {
            model.addAttribute("currentUser", authentication.getName());
            String username = authentication.getName();
            Long userId = userRepository.findByUsername(username).getId();
           VideoHeart heartId = videoHeartRepository.findByUserAndVideoBoard(userRepository.getReferenceById(userId), videoBoardRepository.getReferenceById(id));

            if(heartId == null){
                model.addAttribute("heartCheck", 0);

            }else{
                model.addAttribute("heartCheck", 1);

            }
        }
        List<VideoHeart> videoHeartCount = videoHeartService.videoHeartCount(videoBoard);

        List<VideoComment> videoCommentList = videoCommentService.videoCommentList(videoBoard);
        model.addAttribute("videoBoard", videoBoardService.videoView(id));
        model.addAttribute("videoCommentList",videoCommentList);
        model.addAttribute("videoHeartCount",videoHeartCount);
        if(videoComment.getContent()==null){}else {
            String username = authentication.getName();
            videoCommentService.videoWrite(videoComment, id, username);
            if (request.getHeader("Referer")!=null) {               //이전페이지로 리다이렉트하는 if문
                return  "redirect:" + request.getHeader("Referer");
            }else{
                return "redirect:/";
            }
        }


        return "/menu/board/video-view";
    }



    @GetMapping("/video-delete")
    public String videoDelete(Authentication authentication,Long id)
    {
        String username = authentication.getName();
        VideoBoard video = videoBoardService.videoView(id); //id값의 보드 리스트 를 가져온후

        if (!username.equals(video.getUser().getUsername())){
            return "redirect:/menu/board/video-list";
        }

        videoBoardService.videoDelete(id);

        return "redirect:/menu/board/video-list";
    }

    @GetMapping("/video-modify/{id}")
    public String videoUpdate(@PathVariable("id") Long id,Model model,Authentication authentication){
        String username = authentication.getName();
        VideoBoard board = videoBoardService.videoView(id);
        model.addAttribute("videoBoard",videoBoardService.videoView(id));
        if (username.equals(board.getUser().getUsername())){ //id값에맞는 보드에 유저안에 유저이름을 가져온다.
            return "/menu/board/video-modify";
        }

        return "redirect:/menu/board/video-list";
    }



    @GetMapping("/video-heart/{id}")
    @Transactional
    public String heartCheck(Model model,VideoHeart heart,@PathVariable("id") Long id,Authentication authentication)
    {

        String username = authentication.getName();
        Long userId = userRepository.findByUsername(username).getId();  //유저 아이디를통해 프라이머리키 찾기
        VideoHeart heartId = videoHeartRepository.findByUserAndVideoBoard(userRepository.getReferenceById(userId), videoBoardRepository.getReferenceById(id)); // 유저Id 보드Id 를통해 하트 Id찾기
        VideoBoard videoBoard = videoBoardRepository.findById(id).get();

        if (heartId == null) { //하트Id가 null 값이면 새로운 데이터 생성
            videoHeartService.videoHeartClick(heart, id, username);
            videoBoard.setVideoHeartCount(videoBoard.getVideoHeartCount() +1);
        } else { //null이 아니면 데이터 삭제
            videoBoard.setVideoHeartCount(videoBoard.getVideoHeartCount()-1);
            videoHeartService.videoHeartDelete(heartId.getId());

        }

        model.addAttribute("videoBoard", videoBoardService.videoView(id));
        return "redirect:/menu/board/video-view?id={id}";
    }
    @GetMapping("/videoComment-delete")
    public String videoCommentDelete(Authentication authentication,Long id,HttpServletRequest request)
    {
        String username = authentication.getName();


        if(videoCommentRepository.findById(id).get().getUser().getUsername().equals(username)){
            videoCommentRepository.deleteById(id);
        }

        if (request.getHeader("Referer")!=null) {               //이전페이지로 리다이렉트하는 if문
            return  "redirect:" + request.getHeader("Referer");
        }else{
            return "redirect:/";
        }
    }



}