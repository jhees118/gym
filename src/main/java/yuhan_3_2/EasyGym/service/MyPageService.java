package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.*;
import yuhan_3_2.EasyGym.repository.MyPageRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;

import java.util.List;

@Service
public class MyPageService {
    @Autowired
    private MyPageRepository myPageRepository;

    @Autowired
    private UserRepository userRepository;

    public MyPage myPageWrite(MyPage myPage, String username){     //입력저장 메소드
        User user = userRepository.findByUsername(username);
        myPage.setUser(user);



        return myPageRepository.save(myPage);
    } //쓰기위한 메소드
    public List<MyPage> myPageList(User user){
        return myPageRepository.findByUser(user);
    }

}
