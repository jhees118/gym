package yuhan_3_2.EasyGym.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.entity.VideoBoard;
import yuhan_3_2.EasyGym.entity.VideoComment;

import java.util.List;

@Repository
public interface VideoCommentRepository extends JpaRepository<VideoComment,Long>{
    List<VideoComment> findByVideoBoard(VideoBoard videoBoard);


}