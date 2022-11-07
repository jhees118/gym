package yuhan_3_2.EasyGym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.Heart;
import yuhan_3_2.EasyGym.entity.User;

import java.util.List;

@Repository
public interface HeartRepository extends JpaRepository<Heart,Long> {
    Heart findByUserAndFreeBoard(User user,FreeBoard freeBoard);
    List<Heart> findByFreeBoard(FreeBoard freeBoard);
    List<Heart> findByUser(User user);

}
