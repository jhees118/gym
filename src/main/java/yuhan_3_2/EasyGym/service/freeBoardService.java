package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.freeBoard;
import yuhan_3_2.EasyGym.repository.freeBoardRepository;

import java.util.List;

@Service
public class freeBoardService {

    @Autowired
    private freeBoardRepository freeboardRepository; //레파지토리 기능들사용위함

    public void write(freeBoard freeboard){     //입력저장 메소드
        freeboardRepository.save(freeboard);
    } //쓰기위한 메소드

    public List<freeBoard> freeList(){
        return freeboardRepository.findAll();
    }   //리스트를보여주기위한메소드

}
