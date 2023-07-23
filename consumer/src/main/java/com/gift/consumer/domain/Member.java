package com.gift.consumer.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade= CascadeType.ALL)
    private List<Gift> giftList = new ArrayList<>();

    public Member() {

    }

    public Boolean isDuplicateGift(Gift gift) {
        if (giftList.contains(gift)) {
            return true;
        }
        return false;
    }

    public void receiveGift(Gift gift) {
        giftList.add(gift);
        gift.setReceiverId(this.id);
    }

    public void sendGift() {

    }
}

