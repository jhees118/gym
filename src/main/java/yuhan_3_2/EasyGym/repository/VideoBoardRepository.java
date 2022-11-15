package yuhan_3_2.EasyGym.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.entity.VideoBoard;

import java.util.List;


@Repository
public interface VideoBoardRepository extends JpaRepository<VideoBoard,Long> {
    List<VideoBoard> findByUser(User user);
}