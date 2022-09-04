package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.processor.SpringTextareaFieldTagProcessor;
import yuhan_3_2.EasyGym.entity.freeBoard;
import yuhan_3_2.EasyGym.repository.freeBoardRepository;
import yuhan_3_2.EasyGym.service.freeBoardService;

import javax.validation.Valid;

@Controller
@RequestMapping("/menu/board")
public class freeBoardController {

        @Autowired
        private freeBoardService freeboardService;
        @Autowired
        private freeBoardRepository freeboardRepository;

        @GetMapping("/freelist")
        public String freeList(Model model,Pageable pageable){
            model.addAttribute("freelist",freeboardService.freeList(pageable));

            return  "/menu/board/freelist";
        }
        @GetMapping("/freewrite")
        public String freewrite(Model model, @RequestParam(required = false) Integer id){

            if(id == null) {
                model.addAttribute("freeBoard", new freeBoard());
            } else {
                freeBoard freeboard = freeboardRepository.findById(id).orElse(null);
                model.addAttribute("freeBoard",freeboard);
            }
            return "/menu/board/freewrite";
        }
        @PostMapping("/freewrite")  //getMapping에서 post로변환
        public String greetingSubmit(@Valid freeBoard freeBoard, BindingResult bindingResult,Model model,@RequestParam(required = false) Integer id){
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
