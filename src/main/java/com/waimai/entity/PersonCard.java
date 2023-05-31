package com.waimai.entity;

public class PersonCard {
    private String phone;

    private String cardId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    @Override
    public String toString() {
        return "PersonCard{" +
                "phone='" + phone + '\'' +
                ", cardId='" + cardId + '\'' +
                '}';
    }
}