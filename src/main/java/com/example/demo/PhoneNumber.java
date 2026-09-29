package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String operator;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
        this.operator = determineOperator(number);
    }

    public String getOperator() {
        return operator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String determineOperator(String number) {
        if (number == null) {
            return "Неизвестно";
        }

        String digits = number.replaceAll("\\D", "");

        if (digits.startsWith("998") && digits.length() == 12) {
            digits = digits.substring(3);
        }

        if (digits.length() != 9) {
            return "Неизвестно";
        }

        String prefix = digits.substring(0, 2);

        return switch (prefix) {
            case "90", "91" -> "Beeline";
            case "93", "94", "50" -> "Ucell";
            case "95", "99" -> "Uzmobile";
            case "97", "88" -> "Mobiuz";
            case "33" -> "Humans";
            default -> "Неизвестно";
        };
    }
}