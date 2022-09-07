package yuhan_3_2.EasyGym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yuhan_3_2.EasyGym.entity.FreeBoard;

@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard,Long> {
}
