package yuhan_3_2.EasyGym.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yuhan_3_2.EasyGym.entity.Comment;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.User;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{
   Page<Comment> findByFreeBoard(FreeBoard freeBoard, Pageable pageable);
}
