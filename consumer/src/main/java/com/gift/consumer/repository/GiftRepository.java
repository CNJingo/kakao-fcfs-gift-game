package com.gift.consumer.repository;

import com.gift.consumer.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
}
