package yuhan_3_2.EasyGym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yuhan_3_2.EasyGym.entity.FreeBoard;


import java.util.List;


@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard,Long> {



}
