package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
        private FreeBoardService freeboardService;
        @Autowired
        private FreeBoardRepository freeboardRepository;

        @GetMapping("/freelist")
        public String freeList(Model model,Pageable pageable){
            model.addAttribute("freelist",freeboardService.freeList(pageable));

            return  "/menu/board/freelist";
        }
        @GetMapping("/freewrite")
        public String freewrite(Model model, @RequestParam(required = false) Integer id){

            if(id == null) {
                model.addAttribute("freeBoard", new FreeBoard());
            } else {
                FreeBoard freeboard = freeboardRepository.findById(id).orElse(null);
                model.addAttribute("freeBoard",freeboard);
            }
            return "/menu/board/freewrite";
        }
        @PostMapping("/freewrite")  //getMapping에서 post로변환
        public String greetingSubmit(@Valid FreeBoard freeBoard, BindingResult bindingResult, Model model, @RequestParam(required = false) Integer id){
            if(bindingResult.hasErrors()){
                return "/menu/board/freewrite";
            }
            freeboardService.write(freeBoard);

            if(id == null){
                model.addAttribute("message","글작성이 완료되었습니다");
            }else{
                model.addAttribute("message","글작성이 수정되었습니다");
            }

            model.addAttribute("searchUrl","/menu/board/freelist");
            return "/menu/board/message";
        }

}
