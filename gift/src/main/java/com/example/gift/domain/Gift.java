package com.example.gift.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gift")
public class Gift extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private Long price;

    private Long senderId;

    private Long receiverId;

    public Gift(String name, Long price, Long senderId, int stock) {
        this.name = name;
        this.price = price;
        this.senderId = senderId;
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    private int stock;

    public Gift(String name, Long price, Long senderId) {
        this.name = name;
        this.price = price;
        this.senderId = senderId;
    }

    public Gift() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public void minusStock() {
        this.stock--;
    }
}
