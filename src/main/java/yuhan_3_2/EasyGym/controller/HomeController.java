package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.GymPosition;
import yuhan_3_2.EasyGym.entity.VideoBoard;
import yuhan_3_2.EasyGym.entity.VideoHeart;
import yuhan_3_2.EasyGym.repository.UserRepository;
import yuhan_3_2.EasyGym.repository.VideoHeartRepository;
import yuhan_3_2.EasyGym.service.FreeBoardService;
import yuhan_3_2.EasyGym.service.GymPositionService;
import yuhan_3_2.EasyGym.service.VideoBoardService;
import yuhan_3_2.EasyGym.service.VideoHeartService;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private FreeBoardService freeBoardService;
    @Autowired
    private GymPositionService gymPositionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VideoHeartRepository videoHeartRepository;
    @Autowired
    private VideoBoardService videoBoardService;
    @GetMapping
    public String index(Model model, @PageableDefault(page = 0,size = 4,sort = "heartCount",direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication){
        Page<FreeBoard> freeHeartList= freeBoardService.freeList(pageable);
        model.addAttribute("freeHeartList",freeHeartList);



        List<VideoBoard> videoHeartList = videoBoardService.videoHeartList(); //비디오 하트순
        model.addAttribute("videoHeartList",videoHeartList);
        List<GymPosition> gymHeartList = gymPositionService.gymPositionHeartList();//헬스자세 하트순
        model.addAttribute("gymHeartList",gymHeartList);

        return  "index";
    }



}