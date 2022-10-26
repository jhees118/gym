package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.Comment;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.CommentRepository;
import yuhan_3_2.EasyGym.repository.FreeBoardRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FreeBoardRepository freeboardRepository; //레파지토리 기능들사용위함
    @Autowired
    private UserRepository userRepository;

    public Comment write(String username,Long id,Comment comment){     //입력저장 메소드
        User user = userRepository.findByUsername(username);
        FreeBoard freeBoard = freeboardRepository.findById(id).get();
        comment.setUser(user);
        comment.setFreeBoard(freeBoard);


        return commentRepository.save(comment);
    } //쓰기위한 메소드

    public Page<Comment> commentList(Pageable pageable,FreeBoard freeBoard){
        return commentRepository.findByFreeBoard(freeBoard,pageable);
    }   //리스트를보여주기위한메소드

}
