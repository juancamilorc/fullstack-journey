package com.juancamilo.bankapi.storage.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    List<AccountEntity> findByClient_Id(String clientId);
}