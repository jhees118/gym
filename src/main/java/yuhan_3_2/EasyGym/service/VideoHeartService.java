package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.*;
import yuhan_3_2.EasyGym.repository.*;

import java.util.List;

@Service
public class VideoHeartService {

    @Autowired
    private VideoHeartRepository videoHeartRepository;
    @Autowired
    private VideoBoardRepository videoBoardRepository; //레파지토리 기능들사용위함
    @Autowired
    private UserRepository userRepository;

    public VideoHeart videoHeartClick(VideoHeart videoHeart, Long id , String username){     //입력저장 메소드
        User user = userRepository.findByUsername(username);
        VideoBoard videoBoard = videoBoardRepository.findById(id).orElse(null);
        videoHeart.setUser(user);
        videoHeart.setVideoBoard(videoBoard);


        return videoHeartRepository.save(videoHeart); //하트레포지토리에 유저이름 보드 id 입력
    } //쓰기위한 메소드

    public VideoHeart videoHeartView(User user , VideoBoard videoBoard){

        return videoHeartRepository.findByUserAndVideoBoard(user,videoBoard);
    }

    public List<VideoHeart> videoHeartCount(VideoBoard videoBoard){
        return videoHeartRepository.findByVideoBoard(videoBoard);
    }

    public void videoHeartDelete(Long id){
        videoHeartRepository.deleteById(id);
    }

    public List<VideoHeart> userVideoHeartList(User user){
        return videoHeartRepository.findByUser(user);
    }


}
