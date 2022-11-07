package yuhan_3_2.EasyGym.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yuhan_3_2.EasyGym.entity.GymComment;
import yuhan_3_2.EasyGym.entity.GymPosition;

import java.util.List;

public interface GymCommentRepository extends JpaRepository<GymComment,Long> {
    List<GymComment> findByGymPosition(GymPosition gymPosition);
}
