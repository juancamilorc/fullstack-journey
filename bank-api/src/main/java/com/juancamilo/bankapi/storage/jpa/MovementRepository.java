package com.juancamilo.bankapi.storage.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovementRepository extends JpaRepository<MovementEntity, Long> {

    List<MovementEntity> findByAccount_NumberOrderByIdDesc(String number);
}