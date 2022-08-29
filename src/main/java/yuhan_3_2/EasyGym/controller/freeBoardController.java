package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yuhan_3_2.EasyGym.entity.freeBoard;
import yuhan_3_2.EasyGym.service.freeBoardService;

@Controller
@RequestMapping("/menu")
public class freeBoardController {

        @Autowired
        private freeBoardService freeboardService;

        @GetMapping("/board/free")
        public String free(freeBoard freeboard) {

            freeboardService.write(freeboard);

            return "";
        }
        @GetMapping("/board/freelist")
        public String freeList(Model model){
            model.addAttribute("freelist",freeboardService.freeList());
            return  "/menu/board/freelist";
        }
        @GetMapping("/board/freewrite")
    public String freeWrite(){
            return "/menu/board/freewrite";
        }
}
