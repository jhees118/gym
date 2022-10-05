package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.Heart;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.FreeBoardRepository;
import yuhan_3_2.EasyGym.repository.HeartRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;

@Service
public class HeartService {

    @Autowired
    private HeartRepository heartRepository;
    @Autowired
    private FreeBoardRepository freeboardRepository; //레파지토리 기능들사용위함
    @Autowired
    private UserRepository userRepository;

    public Heart HeartClick(String username ,Long id ,Heart heart){     //입력저장 메소드
        User user = userRepository.findByUsername(username);
        FreeBoard freeBoard = freeboardRepository.findByFreeBoardId(id);
        heart.setUser(user);
        heart.setFreeBoard(freeBoard);


        return heartRepository.save(heart); //하트레포지토리에 유저이름 보드 id 입력
    } //쓰기위한 메소드
}
