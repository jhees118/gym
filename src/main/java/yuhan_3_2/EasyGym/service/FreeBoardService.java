
package yuhan_3_2.EasyGym.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.FreeBoardRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class FreeBoardService {

    @Autowired
    private FreeBoardRepository freeboardRepository; //레파지토리 기능들사용위함
    @Autowired
    private UserRepository userRepository;
    public FreeBoard freeBoardWrite(String username, FreeBoard freeBoard){     //입력저장 메소드
         User user = userRepository.findByUsername(username);
         freeBoard.setUser(user);
        freeBoard.setViewCount(0);
        freeBoard.setHeartCount(0);

       return freeboardRepository.save(freeBoard);
    } //쓰기위한 메소드

    public Page<FreeBoard> freeList(Pageable pageable){
        return freeboardRepository.findAll(pageable);
    }   //리스트를보여주기위한메소드

    //상세 페이지
    public FreeBoard view(Long id){

        return freeboardRepository.findById(id).get();
    }

    //게시글 삭제
    public void freeDelete(Long id){
        freeboardRepository.deleteById(id);
    }



    }



