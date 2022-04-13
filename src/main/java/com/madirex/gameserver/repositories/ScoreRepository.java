package com.madirex.gameserver.repositories;

import com.madirex.gameserver.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, String> {
}
