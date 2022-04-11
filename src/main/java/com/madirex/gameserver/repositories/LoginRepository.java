package com.madirex.gameserver.repositories;
import com.madirex.gameserver.model.Login;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, String> {
    Page<Login> findByUserId(Pageable pageable, String user);
}