package yuhan_3_2.EasyGym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yuhan_3_2.EasyGym.entity.MyPage;
import yuhan_3_2.EasyGym.entity.User;

import java.util.List;


@Repository
public interface MyPageRepository extends JpaRepository<MyPage,Long> {
    List<MyPage> findByUser(User user);
}
