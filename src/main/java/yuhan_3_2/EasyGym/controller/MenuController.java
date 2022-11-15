package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.Heart;
import yuhan_3_2.EasyGym.entity.MyPage;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.HeartRepository;
import yuhan_3_2.EasyGym.repository.MyPageRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;
import yuhan_3_2.EasyGym.service.HeartService;
import yuhan_3_2.EasyGym.service.MyPageService;

import javax.validation.Valid;
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
    @Autowired
    private MyPageService myPageService;
    @Autowired
    private MyPageRepository myPageRepository;
    @GetMapping("/calorie")
    public String calorie() { return "/menu/calorie"; }
    @GetMapping("/myPage")
    public String myPage(Model model, Authentication authentication, User user,@RequestParam(required = false) Long id) {
        String username = authentication.getName();
       model.addAttribute("username",username); //사용자이름
        List<MyPage> myPageList = myPageService.myPageList(user);
        Long userId = userRepository.findByUsername(username).getId();//로그인중인 유저 아이디를 찾음
        List<Heart> userHeartFreeList = heartRepository.findByUser(userRepository.getReferenceById(userId));//유저아이디가 들어가있는 데이터를 리스트에 넣음
        if(id == null) {
            model.addAttribute("myPage", new MyPage());
        } else {
            MyPage myPage = myPageRepository.findById(id).orElse(null);
            model.addAttribute("myPage",myPage);
        }


        model.addAttribute("myPageList",myPageList);
        model.addAttribute("userHeartFreeList",userHeartFreeList); //좋아요한 자유게시판

        return "/menu/myPage";
    }
    @PostMapping("/myPage")
    public String myPageWrite(@Valid MyPage myPage, BindingResult bindingResult, Model model, Authentication authentication, @RequestParam(required = false) Long id){
        if(bindingResult.hasErrors()){
            return "/menu/myPage";
        }

        String username = authentication.getName();


        if(id == null){
            myPageService.myPageWrite(myPage,username);
            model.addAttribute("message","글작성이 완료되었습니다");
        }else{
            myPageService.myPageWrite(myPage,username);
            model.addAttribute("message","글작성이 수정되었습니다");
        }
        model.addAttribute("searchUrl","/menu/myPage");
        return "/menu/message";
    }

}