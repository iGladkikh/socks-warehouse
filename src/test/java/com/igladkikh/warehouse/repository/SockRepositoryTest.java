package com.igladkikh.warehouse.repository;

import com.igladkikh.warehouse.model.Sock;
import com.igladkikh.warehouse.model.SockColor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class SockRepositoryTest {
    @Autowired
    private SockRepository sockRepository;

    @Test
    void testFindUserById() {
        SockColor colorValue = SockColor.BLACK;
        int cottonValue = 20;
        int quantityValue = 50;

        Sock sock = new Sock();
        sock.setColor(colorValue);
        sock.setCotton(cottonValue);
        sock.setQuantity(quantityValue);
        sockRepository.save(sock);

        Optional<Sock> userOptional = sockRepository.findByColorAndCotton(sock.getColor(), sock.getCotton());

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user -> {
                            assertThat(user).hasFieldOrProperty("id");
                            assertThat(user).hasFieldOrPropertyWithValue("color", colorValue);
                            assertThat(user).hasFieldOrPropertyWithValue("cotton", cottonValue);
                            assertThat(user).hasFieldOrPropertyWithValue("quantity", quantityValue);
                        }
                );
    }
}
