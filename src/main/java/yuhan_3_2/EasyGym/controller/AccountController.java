package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.UserRepository;
import yuhan_3_2.EasyGym.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login( HttpServletRequest request,
                         Model model){
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        return "account/login";
    }



    @GetMapping("/register")
    public String register(Model model){

        model.addAttribute("user", new User());

        return "account/register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult , Model model ){

        if(bindingResult.hasErrors()){
            return "account/register";
        }
            model.addAttribute("message","회원가입이 완료되었습니다.");
             model.addAttribute("searchUrl","/account/login");
            userService.save(user);

        return "/menu/message";
    }
    @RequestMapping(value = "/user-check", method = { RequestMethod.POST })
    @ResponseBody
    public int userCheck(String username) {
       boolean a= userService.checkUsernameDuplicate(username); //username 이 있으면 true 없으면 false
           int b;
       if(a==true){ //중복이름이있으면 0
           b=0;
       }else{b=1;} //없으면 1
       return b;
    }

}
