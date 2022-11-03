package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.service.FreeBoardService;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private FreeBoardService freeBoardService;
    @GetMapping
    public String index(Model model,@PageableDefault(page = 0,size = 5,sort = "heartCount",direction = Sort.Direction.DESC) Pageable pageable){
        Page<FreeBoard> freeHeartList= freeBoardService.freeList(pageable);
         model.addAttribute("freeHeartList",freeHeartList);
        return  "index";
    }


}
