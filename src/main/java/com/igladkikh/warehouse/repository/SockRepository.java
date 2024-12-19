package com.igladkikh.warehouse.repository;

import com.igladkikh.warehouse.model.Sock;
import com.igladkikh.warehouse.model.SockColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SockRepository extends JpaRepository<Sock, Long> {

    Optional<Sock> findByColorAndCottonPercentPart(SockColor color, int cotton);
}
