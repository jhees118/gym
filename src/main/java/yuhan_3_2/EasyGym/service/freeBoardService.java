package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.freeBoard;
import yuhan_3_2.EasyGym.repository.freeBoardRepository;

import java.util.List;

@Service
public class freeBoardService {

    @Autowired
    private freeBoardRepository freeboardRepository;

    public void write(freeBoard freeboard){
        freeboardRepository.save(freeboard);
       
    }

    public List<freeBoard> freeList(){
        return freeboardRepository.findAll();
    }

}
