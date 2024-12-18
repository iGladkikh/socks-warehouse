package com.igladkikh.warehouse.repository;

import com.igladkikh.warehouse.model.Sock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SockRepository extends JpaRepository<Sock, Long> {

    Sock findByColorInAndCottonPercentPart(String[] color, int cotton);
}
