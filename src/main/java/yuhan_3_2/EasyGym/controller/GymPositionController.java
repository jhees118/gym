package yuhan_3_2.EasyGym.controller;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yuhan_3_2.EasyGym.entity.*;
import yuhan_3_2.EasyGym.repository.GymHeartRepository;
import yuhan_3_2.EasyGym.repository.GymPositionRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;
import yuhan_3_2.EasyGym.service.GymHeartService;
import yuhan_3_2.EasyGym.service.GymPositionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Controller
public class GymPositionController{

    @Autowired
    private GymPositionService gymPositionService;
    @Autowired
    private GymPositionRepository gymPositionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GymHeartRepository gymHeartRepository;
    @Autowired
    private GymHeartService gymHeartService;


    @GetMapping("/menu/gym-position/view")
    public String View(Model model) {

        List<GymPosition> gymLegList = gymPositionService.gymLegList();

        model.addAttribute("gymLegList",gymLegList);
        return "/menu/gym-position/view";
    }

    @GetMapping("/menu/gym-position/upload")
    public String testUploadForm() {

        return "/menu/gym-position/upload";
    }

    @PostMapping("/menu/gym-position/upload")
    public String uploadFile(@RequestParam("files") List<MultipartFile> files,@Valid GymPosition gymPosition,BindingResult bindingResult,Authentication authentication) throws IOException {

       String username = authentication.getName();
        for (MultipartFile multipartFile : files) {
            gymPositionService.saveGymFile(multipartFile,gymPosition,username);
        }

        return "/menu/gym-position/upload";
    }
    @GetMapping("/menu/gym-position/images/{id}")
    @ResponseBody
    public UrlResource gymImage(@PathVariable("id") Long id, Model model) throws IOException{

        GymPosition file = gymPositionRepository.findById(id).orElse(null);
        return new UrlResource("file:" +file.getSavedPath());
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/menu/gym-position/video/{id}")
    public ResponseEntity<ResourceRegion> gymVideo(@PathVariable("id") Long id,@RequestHeader HttpHeaders headers) throws IOException {
       logger.debug("VideoController.getVideo");
    GymPosition file = gymPositionRepository.findById(id).orElse(null);
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

    @GetMapping("/menu/gym-position/abs")
    public String abs(Model model) {
        List<GymPosition> gymAbsList = gymPositionService.gymAbsList();
        model.addAttribute("gymAbsList",gymAbsList);
        return "/menu/gym-position/abs";
    }
    @GetMapping("/menu/gym-position/shoulder")
    public String shoulder(Model model) {
        List<GymPosition> gymShoulderList = gymPositionService.gymShoulderList();
        model.addAttribute("gymShoulderList",gymShoulderList);
        return "/menu/gym-position/shoulder";
    }
    @GetMapping("/menu/gym-position/chest")
    public String chest(Model model) {
        List<GymPosition> gymChestList = gymPositionService.gymChestList();
        model.addAttribute("gymChestList",gymChestList);
        return "/menu/gym-position/chest";
    }
    @GetMapping("/menu/gym-position/arm")
    public String arm(Model model) {
        List<GymPosition> gymArmList = gymPositionService.gymArmList();
        model.addAttribute("gymLegList",gymArmList);
        return "/menu/gym-position/arm";
    }
    @GetMapping("/menu/gym-position/back")
    public String back(Model model) {
        List<GymPosition> gymBackList = gymPositionService.gymBackList();

        model.addAttribute("gymBackList",gymBackList);
        return "/menu/gym-position/back";
    }
    @GetMapping("/menu/gym-position/leg")
    public String leg(Model model) {
        List<GymPosition> gymLegList = gymPositionService.gymLegList();

        model.addAttribute("gymLegList",gymLegList);

        return "/menu/gym-position/leg";
    }

    @GetMapping("/menu/gym-position/gym-view")
    public String gymView(Authentication authentication,  Model model, @RequestParam(required = false) Long id,
                          User user, Pageable pageable, GymPosition gymPosition, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("gymPosition", gymPositionService.view(id));
        if(authentication == null){ //사용자가 로그인중이아니면
            model.addAttribute("currentUser", null);  //html 사용자 권한에따른 구성으로 설정값입력

        }else {
            model.addAttribute("currentUser", authentication.getName());
            String username = authentication.getName();
            Long userId = userRepository.findByUsername(username).getId();
            GymHeart gymHeartId = gymHeartRepository.findByUserAndGymPosition(userRepository.getReferenceById(userId), gymPositionRepository.getReferenceById(id));

            if(gymHeartId == null){
                model.addAttribute("gymHeartCheck", 0);

            }else{
                model.addAttribute("gymHeartCheck", 1);

            }
        }
        List<GymHeart> gymHeartCount = gymHeartService.gymHeartCount(gymPosition);
        model.addAttribute("gymHeartCount",gymHeartCount);

        return "/menu/gym-position/gym-view";
    }
    @GetMapping("/menu/gym-position/gym-heart/{id}")
    @Transactional
    public String gymHeartCheck(Model model, GymHeart gymHeart, @PathVariable("id") Long id, Authentication authentication)
    {

        String username = authentication.getName();
        Long userId = userRepository.findByUsername(username).getId();  //유저 아이디를통해 프라이머리키 찾기
        GymHeart heartId = gymHeartRepository.findByUserAndGymPosition(userRepository.getReferenceById(userId), gymPositionRepository.getReferenceById(id)); // 유저Id 보드Id 를통해 하트 Id찾기
        GymPosition gymPosition = gymPositionRepository.findById(id).get();

        if (heartId == null) { //하트Id가 null 값이면 새로운 데이터 생성
            gymHeartService.gymHeartClick(gymHeart, id, username);
            gymPosition.setGymHeartCount(gymPosition.getGymHeartCount()+1);
        } else { //null이 아니면 데이터 삭제
            gymPosition.setGymHeartCount(gymPosition.getGymHeartCount()-1);
            gymHeartService.gymHeartDelete(heartId.getId());

        }

        model.addAttribute("gymPosition", gymPositionService.view(id));
        return "redirect:/menu/gym-position/gym-view?id={id}";
    }
}
