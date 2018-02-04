package com.eb.server.repositories;

import com.eb.server.domain.MatchmakingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchmakingRequestRepository extends JpaRepository<MatchmakingRequest, Long> {
}
