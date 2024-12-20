package com.igladkikh.warehouse.repository;

import com.igladkikh.warehouse.model.Sock;
import com.igladkikh.warehouse.model.SockColor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface SockRepository extends JpaRepository<Sock, Long>, JpaSpecificationExecutor<Sock> {

    Optional<Sock> findByColorAndCotton(SockColor color, int cotton);

    default Specification<Sock> hasColor(List<SockColor> colors) {
        return (sock, query, builder) ->
                colors == null ? null : sock.get("color").in(colors);
    }

    default Specification<Sock> cottonEquals(Integer cotton) {
        return (sock, query, builder) ->
                cotton == null ? null : builder.equal(sock.get("cotton"), cotton);
    }

    default Specification<Sock> cottonLessThan(Integer cotton) {
        return (sock, query, builder) ->
                cotton == null ? null : builder.lessThanOrEqualTo(sock.get("cotton"), cotton);
    }

    default Specification<Sock> cottonGreaterThan(Integer cotton) {
        return (sock, query, builder) ->
                cotton == null ? null : builder.greaterThanOrEqualTo(sock.get("cotton"), cotton);
    }
}
