package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yuhan_3_2.EasyGym.entity.Comment;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.Heart;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.CommentRepository;
import yuhan_3_2.EasyGym.repository.FreeBoardRepository;
import yuhan_3_2.EasyGym.repository.HeartRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;
import yuhan_3_2.EasyGym.service.CommentService;
import yuhan_3_2.EasyGym.service.FreeBoardService;
import yuhan_3_2.EasyGym.service.HeartService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/menu/board")
public class FreeBoardController {

    @Autowired
    private FreeBoardService freeBoardService;
    @Autowired
    private FreeBoardRepository freeBoardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HeartService heartService;
    @Autowired
    private HeartRepository heartRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/free-list")
    public String freeList(Model model,FreeBoard freeBoard,@PageableDefault(page = 0,size = 8,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                          Authentication authentication){

        if(authentication == null){
            model.addAttribute("currentUser",1);
        }

        Page<FreeBoard> freeList = freeBoardService.freeList(pageable);

        int  nowPage = freeList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4,1);
        int endPage = Math.min(nowPage +5,freeList.getTotalPages());

        model.addAttribute("nowPage",nowPage);
        model.addAttribute("freelist",freeList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        List<FreeBoard> freeHeartList =freeBoardService.freeHeartList();

        model.addAttribute("freeHeartList",freeHeartList);

        return  "/menu/board/free-list";
    }
    @GetMapping("/free-write")
    public String freeWrite(Model model, @RequestParam(required = false) Long id,Authentication authentication){





        if(id == null) {
            model.addAttribute("freeBoard", new FreeBoard());
        } else {
            FreeBoard freeBoard = freeBoardRepository.findById(id).orElse(null);
            model.addAttribute("freeBoard",freeBoard);
        }



        return "/menu/board/free-write";
    }
    @PostMapping("/free-write")  //getMapping에서 post로변환
    public String greetingSubmit(@Valid FreeBoard freeBoard, BindingResult bindingResult, Model model, @RequestParam(required = false) Long id,
                                 Authentication authentication){
        if(bindingResult.hasErrors()){
            return "/menu/board/free-write";
        }

        String username = authentication.getName();


        if(id == null){
            freeBoardService.freeBoardWrite(username,freeBoard);
            model.addAttribute("message","글작성이 완료되었습니다");
        }else{
            freeBoardService.freeBoardWrite(username,freeBoard);
            model.addAttribute("message","글작성이 수정되었습니다");
        }

        model.addAttribute("searchUrl","/menu/board/free-list");
        return "/menu/message";
    }

    @GetMapping("/free-view")
    @Transactional
    public String getFreeView(Authentication authentication, @Valid Comment comment, BindingResult bindingResult, Model model, @RequestParam(required = false) Long id,
                              User user, Pageable pageable, FreeBoard freeBoard,HttpServletRequest request,HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "/menu/board/free-view";
        }


        freeBoard = freeBoardRepository.findById(id).get(); //조회수증가를위한 레포지토리 아이디값


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
                       freeBoard.setViewCount(freeBoard.getViewCount() + 1); //조회수 증가
                   }
               }
           }
           if (visitor == 0) {
               Cookie cookie1 = new Cookie("visit", request.getParameter("id"));
               response.addCookie(cookie1);
               freeBoard.setViewCount(freeBoard.getViewCount() + 1); //조회수증가
           }
       }

        if(authentication == null){ //사용자가 로그인중이아니면
            model.addAttribute("currentUser", null);  //html 사용자 권한에따른 구성으로 설정값입력

        }else {
            model.addAttribute("currentUser", authentication.getName());
            String username = authentication.getName();
            Long userId = userRepository.findByUsername(username).getId();
            Heart heartId = heartRepository.findByUserAndFreeBoard(userRepository.getReferenceById(userId), freeBoardRepository.getReferenceById(id));

            if(heartId == null){
                model.addAttribute("heartCheck", 0);

            }else{
                model.addAttribute("heartCheck", 1);

            }
        }
        List<Heart> heartCount = heartService.heartCount(freeBoard);

        List<Comment> commentList = commentService.commentList(freeBoard);
        model.addAttribute("freeBoard", freeBoardService.view(id));
        model.addAttribute("commentList",commentList);
        model.addAttribute("heartCount",heartCount);
        if(comment.getContent()==null){}else {
            String username = authentication.getName();
            commentService.write(comment, id, username);
            if (request.getHeader("Referer")!=null) {               //이전페이지로 리다이렉트하는 if문
                return  "redirect:" + request.getHeader("Referer");
            }else{
                return "redirect:/";
            }
        }


        return "/menu/board/free-view";
    }



    @GetMapping("/free-delete")
    public String freeDelete(Authentication authentication,Long id)
    {
        String username = authentication.getName();
        FreeBoard board = freeBoardService.view(id); //id값의 보드 리스트 를 가져온후

        if (!username.equals(board.getUser().getUsername())){
            return "redirect:/menu/board/free-list";
        }

        freeBoardService.freeDelete(id);

        return "redirect:/menu/board/free-list";
    }

    @GetMapping("/free-modify/{id}")
    public String freeUpdate(@PathVariable("id") Long id,Model model,Authentication authentication){
        String username = authentication.getName();
        FreeBoard board = freeBoardService.view(id);
        model.addAttribute("freeBoard",freeBoardService.view(id));
        if (username.equals(board.getUser().getUsername())){ //id값에맞는 보드에 유저안에 유저이름을 가져온다.
            return "/menu/board/free-modify";
        }

        return "redirect:/menu/board/free-list";
    }



    @GetMapping("/free-heart/{id}")
    @Transactional
    public String heartCheck(Model model,Heart heart,@PathVariable("id") Long id,Authentication authentication)
    {

        String username = authentication.getName();
        Long userId = userRepository.findByUsername(username).getId();  //유저 아이디를통해 프라이머리키 찾기
        Heart heartId = heartRepository.findByUserAndFreeBoard(userRepository.getReferenceById(userId), freeBoardRepository.getReferenceById(id)); // 유저Id 보드Id 를통해 하트 Id찾기
        FreeBoard freeBoard = freeBoardRepository.findById(id).get();

        if (heartId == null) { //하트Id가 null 값이면 새로운 데이터 생성
            heartService.HeartClick(heart, id, username);
            freeBoard.setHeartCount(freeBoard.getHeartCount()+1);
        } else { //null이 아니면 데이터 삭제
            freeBoard.setHeartCount(freeBoard.getHeartCount()-1);
            heartService.heartDelete(heartId.getId());

        }

        model.addAttribute("freeBoard", freeBoardService.view(id));
        return "redirect:/menu/board/free-view?id={id}";
    }
    @GetMapping("/comment-delete")
    public String commentDelete(Authentication authentication,Long id,HttpServletRequest request)
    {
        String username = authentication.getName();


        if(commentRepository.findById(id).get().getUser().getUsername().equals(username)){
            commentRepository.deleteById(id);
        }

        if (request.getHeader("Referer")!=null) {               //이전페이지로 리다이렉트하는 if문
            return  "redirect:" + request.getHeader("Referer");
        }else{
            return "redirect:/";
        }
    }



}


