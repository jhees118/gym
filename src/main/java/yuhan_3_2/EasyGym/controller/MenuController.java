package yuhan_3_2.EasyGym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @GetMapping("/calorie")
    public String calorie() { return "/menu/calorie"; }
    @GetMapping("/gym-position/shoulder")
    public String shoulder() {
        return "/menu/gym-position/shoulder";
    }
    @GetMapping("/gym-position/chest")
    public String chest() {
        return "/menu/gym-position/chest";
    }
    @GetMapping("/gym-position/arm")
    public String arm() {
        return "/menu/gym-position/arm";
    }
    @GetMapping("/gym-position/back")
    public String back() {
        return "/menu/gym-position/back";
    }
    @GetMapping("/gym-position/leg")
    public String leg() {
        return "/menu/gym-position/leg";
    }
}