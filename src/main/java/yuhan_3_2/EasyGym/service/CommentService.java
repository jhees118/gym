package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import yuhan_3_2.EasyGym.entity.Comment;
import yuhan_3_2.EasyGym.repository.CommentRepository;
import yuhan_3_2.EasyGym.repository.FreeBoardRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;

public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FreeBoardRepository freeboardRepository; //레파지토리 기능들사용위함
    @Autowired
    private UserRepository userRepository;


}
