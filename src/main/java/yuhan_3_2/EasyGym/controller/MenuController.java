package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.Heart;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.HeartRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;
import yuhan_3_2.EasyGym.service.HeartService;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private HeartService heartService;
    @Autowired
    private HeartRepository heartRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/calorie")
    public String calorie() { return "/menu/calorie"; }
    @GetMapping("/myPage")
    public String myPage(Model model, Authentication authentication, User user) {
     String username = authentication.getName();
       model.addAttribute("username",username); //사용자이름

        Long userId = userRepository.findByUsername(username).getId();//로그인중인 유저 아이디를 찾음
        List<Heart> userHeartFreeList = heartRepository.findByUser(userRepository.getReferenceById(userId));//유저아이디가 들어가있는 데이터를 리스트에 넣음




        model.addAttribute("userHeartFreeList",userHeartFreeList); //좋아요한 자유게시판

        return "/menu/myPage";
    }

}