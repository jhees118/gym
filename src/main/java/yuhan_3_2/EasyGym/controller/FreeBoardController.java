package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.repository.FreeBoardRepository;
import yuhan_3_2.EasyGym.service.FreeBoardService;

import javax.validation.Valid;

@Controller
@RequestMapping("/menu/board")
public class FreeBoardController {

    @Autowired
    private FreeBoardService freeBoardService;
    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @GetMapping("/free-list")
    public String freeList(Model model, @PageableDefault(page = 0,size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){

        Page<FreeBoard> freeList = freeBoardService.freeList(pageable);
        int  nowPage = freeList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4,1);
        int endPage = Math.min(nowPage +5,freeList.getTotalPages());

        model.addAttribute("nowPage",nowPage);
        model.addAttribute("freelist",freeList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);


        return  "/menu/board/free-list";
    }
    @GetMapping("/free-write")
    public String freeWrite(Model model, @RequestParam(required = false) Long id){

        if(id == null) {
            model.addAttribute("freeBoard", new FreeBoard());
        } else {
            FreeBoard freeBoard = freeBoardRepository.findById(id).orElse(null);
            model.addAttribute("freeBoard",freeBoard);
        }
        return "/menu/board/free-write";
    }
    @PostMapping("/free-write")  //getMapping에서 post로변환
    public String greetingSubmit(@Valid FreeBoard freeBoard, BindingResult bindingResult, Model model, @RequestParam(required = false) Long id){
        if(bindingResult.hasErrors()){
            return "/menu/board/free-write";
        }
        freeBoardService.write(freeBoard);

        if(id == null){
            model.addAttribute("message","글작성이 완료되었습니다");
        }else{
            model.addAttribute("message","글작성이 수정되었습니다");
        }

        model.addAttribute("searchUrl","/menu/board/free-list");
        return "/menu/board/message";
    }

}