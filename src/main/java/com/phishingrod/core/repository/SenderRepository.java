package com.phishingrod.core.repository;

import com.phishingrod.core.domain.Sender;
import com.phishingrod.core.domain.SenderServer;
import com.phishingrod.core.repository.base.NameKeyEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SenderRepository extends NameKeyEntityRepository<Sender>
{
    List<Sender> findAllByServer(SenderServer server);
}
