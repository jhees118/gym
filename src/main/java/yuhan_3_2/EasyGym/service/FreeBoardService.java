
package yuhan_3_2.EasyGym.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.FreeBoardRepository;
import yuhan_3_2.EasyGym.repository.UserRepository;

@Service
public class FreeBoardService {

    @Autowired
    private FreeBoardRepository freeboardRepository; //레파지토리 기능들사용위함
    @Autowired
    private UserRepository userRepository;
    public void write(String username,FreeBoard freeboard){     //입력저장 메소드
         User user = userRepository.findByUsername(username);
         freeboard.setUser(user);
        freeboardRepository.save(freeboard);
    } //쓰기위한 메소드

    public Page<FreeBoard> freeList(Pageable pageable){
        return freeboardRepository.findAll(pageable);
    }   //리스트를보여주기위한메소드

}
