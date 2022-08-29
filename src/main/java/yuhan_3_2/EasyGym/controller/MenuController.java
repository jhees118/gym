package yuhan_3_2.EasyGym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController {
        @GetMapping("/calorie")
        public String calorie() { return "/menu/calorie"; }
        @GetMapping("/gym_position/shoulder")
        public String shoulder() {
            return "/menu/gym_position/shoulder";
    }
        @GetMapping("/gym_position/chest")
        public String chest() {
        return "/menu/gym_position/chest";
    }
        @GetMapping("/gym_position/arm")
        public String arm() {
            return "/menu/gym_position/arm";
    }
        @GetMapping("/gym_position/back")
        public String back() {
            return "/menu/gym_position/back";
    }
        @GetMapping("/gym_position/leg")
        public String leg() {
            return "/menu/gym_position/leg";
    }
}
