package com.igladkikh.warehouse.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "socks")
public class Sock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private SockColor color;
    @Column(name = "cotton")
    private int cottonPercentPart;
    private int quantity;
}
