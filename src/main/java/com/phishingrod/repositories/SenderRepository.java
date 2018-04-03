package com.phishingrod.repositories;

import com.phishingrod.domain.Sender;
import org.springframework.data.repository.CrudRepository;

public interface SenderRepository extends CrudRepository<Sender, Long>
{
    void findByLoginNameAndServerName(String loginName, String serverName);
}
