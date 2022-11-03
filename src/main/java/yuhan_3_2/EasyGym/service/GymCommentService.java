package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.*;
import yuhan_3_2.EasyGym.repository.GymCommentRepository;
import yuhan_3_2.EasyGym.repository.GymPositionRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;

@Service
public class GymCommentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GymPositionRepository gymPositionRepository;
    @Autowired
    private GymCommentRepository gymCommentRepository;

    public GymComment gymWrite(GymComment gymComment, Long id , String username){     //입력저장 메소드
        User user = userRepository.findByUsername(username);
        GymPosition gymPosition = gymPositionRepository.findById(id).orElse(null);
        gymComment.setUser(user);
        gymComment.setGymPosition(gymPosition);


        return gymCommentRepository.save(gymComment); //하트레포지토리에 유저이름 보드 id 입력
    } //쓰기위한 메소드
    public Page<GymComment> gymCommentList(Pageable pageable,GymPosition gymPosition){
        return gymCommentRepository.findByGymPosition(gymPosition,pageable);
    }   //리스트를보여주기위한메소드
}
