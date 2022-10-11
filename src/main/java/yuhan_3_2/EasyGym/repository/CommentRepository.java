package yuhan_3_2.EasyGym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yuhan_3_2.EasyGym.entity.Comment;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{
    Comment findByUserAndFreeBoard(User user, FreeBoard freeBoard);
}
