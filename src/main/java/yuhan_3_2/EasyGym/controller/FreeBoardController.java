package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.Heart;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.FreeBoardRepository;
import yuhan_3_2.EasyGym.repository.HeartRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;
import yuhan_3_2.EasyGym.service.FreeBoardService;
import yuhan_3_2.EasyGym.service.HeartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/menu/board")
public class FreeBoardController {

    @Autowired
    private FreeBoardService freeBoardService;
    @Autowired
    private FreeBoardRepository freeBoardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HeartService heartService;
    @Autowired
    private HeartRepository heartRepository;


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
    public String freeWrite(Model model, @RequestParam(required = false) Long id,Authentication authentication){


        if(id == null) {
            model.addAttribute("freeBoard", new FreeBoard());
        } else {
            FreeBoard freeBoard = freeBoardRepository.findById(id).orElse(null);
            model.addAttribute("freeBoard",freeBoard);
        }



        return "/menu/board/free-write";
    }
    @PostMapping("/free-write")  //getMapping에서 post로변환
    public String greetingSubmit(@Valid FreeBoard freeBoard, BindingResult bindingResult, Model model, @RequestParam(required = false) Long id,
                                 Authentication authentication){
        if(bindingResult.hasErrors()){
            return "/menu/board/free-write";
        }

        String username = authentication.getName();


        if(id == null){
            freeBoardService.write(username,freeBoard);
            model.addAttribute("message","글작성이 완료되었습니다");
        }else{
            freeBoardService.write(username,freeBoard);
            model.addAttribute("message","글작성이 수정되었습니다");
        }

        model.addAttribute("searchUrl","/menu/board/free-list");
        return "/menu/message";
    }


    @GetMapping("/free-view")
    public String freeView(Authentication authentication, Model model,Long id) {

        if(authentication == null){ //사용자가 로그인중이아니면
            model.addAttribute("currentUser", null);           //html 사용자 권한에따른 구성으로 설정값입력
        }else {
            model.addAttribute("currentUser", authentication.getName());
        }


        model.addAttribute("freeBoard", freeBoardService.view(id));
        return "/menu/board/free-view";
    }

    @GetMapping("/free-delete")
    public String freeDelete(Authentication authentication,Long id)
    {
        String username = authentication.getName();
        FreeBoard board = freeBoardService.view(id); //id값의 보드 리스트 를 가져온후

        if (!username.equals(board.getUser().getUsername())){
            return "redirect:/menu/board/free-list";
        }

        freeBoardService.freeDelete(id);

        return "redirect:/menu/board/free-list";
    }

    @GetMapping("/free-modify/{id}")
    public String freeUpdate(@PathVariable("id") Long id,Model model,Authentication authentication){
        String username = authentication.getName();
        FreeBoard board = freeBoardService.view(id);
        model.addAttribute("freeBoard",freeBoardService.view(id));
        if (username.equals(board.getUser().getUsername())){ //id값에맞는 보드에 유저안에 유저이름을 가져온다.
            return "/menu/board/free-modify";
        }

        return "redirect:/menu/board/free-list";
    }

    @GetMapping("/free-heart/{id}")
    public String heartCheck(Model model,Heart heart,@PathVariable("id") Long id,Authentication authentication)
    {
        String username = authentication.getName();
        System.out.println(heart);
        System.out.println(id);
        System.out.println(username);
        Long heart1 = heartService.heartView(9l).getUser().getId();
        System.out.println(heart1);

            heartService.HeartClick(heart, id, username);

        model.addAttribute("freeBoard", freeBoardService.view(id));
        return "redirect:/menu/board/free-view?id={id}";
    }

    @GetMapping("/asdf/{id}")
    public String getJPATest(@PathVariable("id") Long id){

     Long heart1 = heartService.heartView(id).getFreeBoard().getId();
      Long heart =  heartService.heartView(id).getUser().getId();
       System.out.println(heart1);
        System.out.println(heart);
        return "redirect:/menu/board/asdf/{id}";
    }

}


