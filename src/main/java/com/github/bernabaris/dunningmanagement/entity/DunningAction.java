package com.github.bernabaris.dunningmanagement.entity;

import com.github.bernabaris.dunningmanagement.enums.ActionType;
import com.github.bernabaris.dunningmanagement.enums.DunningLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DunningAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Enumerated(EnumType.STRING)
    private DunningLevel dunningLevel;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
