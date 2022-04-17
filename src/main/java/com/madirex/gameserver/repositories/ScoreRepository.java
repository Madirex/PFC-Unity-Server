package com.madirex.gameserver.repositories;

import com.madirex.gameserver.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, String> {
    List<Score> findAllByLevelOrderByAmountDesc(Integer level);



    @Query( value = "SELECT * FROM score s, users u WHERE s.level = ?1 AND u.username LIKE ?2 ORDER BY s.level ASC", nativeQuery = true)
    List<Score> findAllByLevelAndUserOrderByLevel(String level, String user);

    @Query( value = "SELECT * FROM score s, users u WHERE u.username = ?1 ORDER BY s.level ASC", nativeQuery = true)
    List<Score> findAllByUserOrderByLevel(String user);
}
