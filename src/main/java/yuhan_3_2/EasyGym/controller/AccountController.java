package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.UserRepository;
import yuhan_3_2.EasyGym.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(){

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
            userService.save(user);

        return "/menu/message";
    }
}
