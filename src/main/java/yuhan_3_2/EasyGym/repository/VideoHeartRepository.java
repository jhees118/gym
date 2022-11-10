package yuhan_3_2.EasyGym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.entity.VideoBoard;
import yuhan_3_2.EasyGym.entity.VideoHeart;

import java.util.List;

@Repository
public interface VideoHeartRepository extends JpaRepository<VideoHeart,Long> {
    VideoHeart findByUserAndVideoBoard(User user, VideoBoard videoBoard);
    List<VideoHeart> findByVideoBoard(VideoBoard videoBoard);
    List<VideoHeart> findByUser(User user);

}