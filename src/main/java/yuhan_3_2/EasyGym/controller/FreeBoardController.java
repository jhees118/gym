package yuhan_3_2.EasyGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yuhan_3_2.EasyGym.entity.Comment;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.Heart;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.CommentRepository;
import yuhan_3_2.EasyGym.repository.FreeBoardRepository;
import yuhan_3_2.EasyGym.repository.HeartRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;
import yuhan_3_2.EasyGym.service.CommentService;
import yuhan_3_2.EasyGym.service.FreeBoardService;
import yuhan_3_2.EasyGym.service.HeartService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
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
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/free-list")
    public String freeList(Model model,FreeBoard freeBoard,@PageableDefault(page = 0,size = 8,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                          Authentication authentication){

        if(authentication == null){
            model.addAttribute("currentUser",1);
        }

        Page<FreeBoard> freeList = freeBoardService.freeList(pageable);

        int  nowPage = freeList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4,1);
        int endPage = Math.min(nowPage +5,freeList.getTotalPages());

        model.addAttribute("nowPage",nowPage);
        model.addAttribute("freelist",freeList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        List<FreeBoard> freeHeartList =freeBoardService.freeHeartList();

        model.addAttribute("freeHeartList",freeHeartList);

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
    @PostMapping("/free-write")  //getMapping?????? post?????????
    public String greetingSubmit(@Valid FreeBoard freeBoard, BindingResult bindingResult, Model model, @RequestParam(required = false) Long id,
                                 Authentication authentication){
        if(bindingResult.hasErrors()){
            return "/menu/board/free-write";
        }

        String username = authentication.getName();


        if(id == null){
            freeBoardService.freeBoardWrite(username,freeBoard);
            model.addAttribute("message","???????????? ?????????????????????");
        }else{
            freeBoardService.freeBoardWrite(username,freeBoard);
            model.addAttribute("message","???????????? ?????????????????????");
        }

        model.addAttribute("searchUrl","/menu/board/free-list");
        return "/menu/message";
    }

    @GetMapping("/free-view")
    @Transactional
    public String getFreeView(Authentication authentication, @Valid Comment comment, BindingResult bindingResult, Model model, @RequestParam(required = false) Long id,
                              User user, Pageable pageable, FreeBoard freeBoard,HttpServletRequest request,HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "/menu/board/free-view";
        }


        freeBoard = freeBoardRepository.findById(id).get(); //???????????????????????? ??????????????? ????????????


       Cookie[] cookies = request.getCookies();
       int visitor = 0;
       if(cookies != null) {
           for (Cookie cookie : cookies) {
               if (cookie.getName().equals("visit")) {
                   visitor = 1;
                   if (cookie.getValue().contains(request.getParameter("id"))) {

                   } else {
                       cookie.setValue(cookie.getValue() + "_" + request.getParameter("id"));
                       response.addCookie(cookie);
                       freeBoard.setViewCount(freeBoard.getViewCount() + 1); //????????? ??????
                   }
               }
           }
           if (visitor == 0) {
               Cookie cookie1 = new Cookie("visit", request.getParameter("id"));
               response.addCookie(cookie1);
               freeBoard.setViewCount(freeBoard.getViewCount() + 1); //???????????????
           }
       }

        if(authentication == null){ //???????????? ????????????????????????
            model.addAttribute("currentUser", null);  //html ????????? ??????????????? ???????????? ???????????????

        }else {
            model.addAttribute("currentUser", authentication.getName());
            String username = authentication.getName();
            Long userId = userRepository.findByUsername(username).getId();
            Heart heartId = heartRepository.findByUserAndFreeBoard(userRepository.getReferenceById(userId), freeBoardRepository.getReferenceById(id));

            if(heartId == null){
                model.addAttribute("heartCheck", 0);

            }else{
                model.addAttribute("heartCheck", 1);

            }
        }
        List<Heart> heartCount = heartService.heartCount(freeBoard);

        List<Comment> commentList = commentService.commentList(freeBoard);
        model.addAttribute("freeBoard", freeBoardService.view(id));
        model.addAttribute("commentList",commentList);
        model.addAttribute("heartCount",heartCount);
        if(comment.getContent()==null){}else {
            String username = authentication.getName();
            commentService.write(comment, id, username);
            if (request.getHeader("Referer")!=null) {               //?????????????????? ????????????????????? if???
                return  "redirect:" + request.getHeader("Referer");
            }else{
                return "redirect:/";
            }
        }


        return "/menu/board/free-view";
    }



    @GetMapping("/free-delete")
    public String freeDelete(Authentication authentication,Long id)
    {
        String username = authentication.getName();
        FreeBoard board = freeBoardService.view(id); //id?????? ?????? ????????? ??? ????????????

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
        if (username.equals(board.getUser().getUsername())){ //id???????????? ????????? ???????????? ??????????????? ????????????.
            return "/menu/board/free-modify";
        }

        return "redirect:/menu/board/free-list";
    }



    @GetMapping("/free-heart/{id}")
    @Transactional
    public String heartCheck(Model model,Heart heart,@PathVariable("id") Long id,Authentication authentication)
    {

        String username = authentication.getName();
        Long userId = userRepository.findByUsername(username).getId();  //?????? ?????????????????? ?????????????????? ??????
        Heart heartId = heartRepository.findByUserAndFreeBoard(userRepository.getReferenceById(userId), freeBoardRepository.getReferenceById(id)); // ??????Id ??????Id ????????? ?????? Id??????
        FreeBoard freeBoard = freeBoardRepository.findById(id).get();

        if (heartId == null) { //??????Id??? null ????????? ????????? ????????? ??????
            heartService.HeartClick(heart, id, username);
            freeBoard.setHeartCount(freeBoard.getHeartCount()+1);
        } else { //null??? ????????? ????????? ??????
            freeBoard.setHeartCount(freeBoard.getHeartCount()-1);
            heartService.heartDelete(heartId.getId());

        }

        model.addAttribute("freeBoard", freeBoardService.view(id));
        return "redirect:/menu/board/free-view?id={id}";
    }
    @GetMapping("/comment-delete")
    public String commentDelete(Authentication authentication,Long id,HttpServletRequest request)
    {
        String username = authentication.getName();


        if(commentRepository.findById(id).get().getUser().getUsername().equals(username)){
            commentRepository.deleteById(id);
        }

        if (request.getHeader("Referer")!=null) {               //?????????????????? ????????????????????? if???
            return  "redirect:" + request.getHeader("Referer");
        }else{
            return "redirect:/";
        }

    }
    @GetMapping("/comment-modify/{id}")
    public String commentUpdate(@PathVariable("id") Long id,Model model,Authentication authentication,Comment comment,HttpServletRequest request){
        String username = authentication.getName();
        if(commentRepository.findById(id).get().getUser().getUsername().equals(username)){
            model.addAttribute("comment",commentService.view(id));
        }
        if (username.equals(comment.getUser().getUsername())){ //id???????????? ????????? ???????????? ??????????????? ????????????.
            return "/menu/board/free-view/{id}";
        }

        if (request.getHeader("Referer")!=null) {               //?????????????????? ????????????????????? if???
            return  "redirect:" + request.getHeader("Referer");
        }else{
            return "redirect:/";
        }
    }


}


