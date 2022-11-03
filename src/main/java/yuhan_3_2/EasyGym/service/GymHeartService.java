package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.*;
import yuhan_3_2.EasyGym.repository.*;

import java.util.List;

@Service
public class GymHeartService {

    @Autowired
    private HeartRepository heartRepository;
    @Autowired
    private FreeBoardRepository freeboardRepository; //레파지토리 기능들사용위함
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GymHeartRepository gymHeartRepository;
    @Autowired
    private GymPositionRepository gymPositionRepository;

    public GymHeart gymHeartClick(GymHeart gymHeart, Long id , String username){     //입력저장 메소드
        User user = userRepository.findByUsername(username);
        GymPosition gymPosition = gymPositionRepository.findById(id).orElse(null);
        gymHeart.setUser(user);
        gymHeart.setGymPosition(gymPosition);


        return gymHeartRepository.save(gymHeart); //하트레포지토리에 유저이름 보드 id 입력
    } //쓰기위한 메소드

    public GymHeart heartView(User user , GymPosition gymPosition){

        return gymHeartRepository.findByUserAndGymPosition(user,gymPosition);
    }

    public List<GymHeart> gymHeartCount(GymPosition gymPosition){
        return gymHeartRepository.findByGymPosition(gymPosition);
    }

    public void gymHeartDelete(Long id){
        gymHeartRepository.deleteById(id);
    }


}
