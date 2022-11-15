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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.GymPosition;
import yuhan_3_2.EasyGym.entity.MyPage;
import yuhan_3_2.EasyGym.repository.MyPageRepository;
import yuhan_3_2.EasyGym.service.FreeBoardService;
import yuhan_3_2.EasyGym.service.GymPositionService;
import yuhan_3_2.EasyGym.service.MyPageService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private FreeBoardService freeBoardService;
    @Autowired
    private GymPositionService gymPositionService;
    @Autowired
    private MyPageService myPageService;
    @Autowired
    private MyPageRepository myPageRepository;
    @GetMapping
    public String index(Model model,@PageableDefault(page = 0,size = 4,sort = "heartCount",direction = Sort.Direction.DESC) Pageable pageable){
        Page<FreeBoard> freeHeartList= freeBoardService.freeList(pageable);
         model.addAttribute("freeHeartList",freeHeartList);

        List<GymPosition> gymHeartList = gymPositionService.gymPositionHeartList();
        model.addAttribute("gymHeartList",gymHeartList);

        return  "index";
    }



}
