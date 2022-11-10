package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.*;
import yuhan_3_2.EasyGym.repository.*;

import java.util.List;

@Service
public class VideoCommentService {
    @Autowired
    private VideoCommentRepository videoCommentRepository;
    @Autowired
    private VideoBoardRepository videoBoardRepository;
    @Autowired
    private UserRepository userRepository;

    public VideoComment videoWrite(VideoComment videoComment, Long id , String username){     //입력저장 메소드
        User user = userRepository.findByUsername(username);
        VideoBoard videoBoard = videoBoardRepository.findById(id).orElse(null);
        videoComment.setUser(user);
        videoComment.setVideoBoard(videoBoard);


        return videoCommentRepository.save(videoComment); //하트레포지토리에 유저이름 보드 id 입력
    } //쓰기위한 메소드
    public List<VideoComment> videoCommentList(VideoBoard videoBoard){
        return videoCommentRepository.findByVideoBoard(videoBoard);
    }   //리스트를보여주기위한메소드



}