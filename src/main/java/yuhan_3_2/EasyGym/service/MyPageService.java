package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.GymComment;
import yuhan_3_2.EasyGym.entity.GymPosition;
import yuhan_3_2.EasyGym.entity.MyPage;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.MyPageRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;

@Service
public class MyPageService {
    @Autowired
    private MyPageRepository myPageRepository;

    @Autowired
    private UserRepository userRepository;

    public MyPage myPageWrite(MyPage myPage, String username){     //입력저장 메소드
        User user = userRepository.findByUsername(username);
        myPage.setUser(user);
        myPage.setYear(myPage.getDay());
        myPage.setMonth(myPage.getMonth());
        myPage.setDay(myPage.getDay());
        myPage.setContent(myPage.getContent());


        return myPageRepository.save(myPage); //하트레포지토리에 유저이름 보드 id 입력
    } //쓰기위한 메소드
}
