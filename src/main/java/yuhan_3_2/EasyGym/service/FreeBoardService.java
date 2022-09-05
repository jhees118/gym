package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.repository.FreeBoardRepository;

@Service
public class FreeBoardService {

    @Autowired
    private FreeBoardRepository freeboardRepository; //레파지토리 기능들사용위함

    public void write(FreeBoard freeboard){     //입력저장 메소드
        freeboardRepository.save(freeboard);
    } //쓰기위한 메소드

    public Page<FreeBoard> freelist(Pageable pageable){
        return freeboardRepository.findAll(pageable);
    }   //리스트를보여주기위한메소드

}
