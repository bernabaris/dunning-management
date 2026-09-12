package com.github.bernabaris.dunningmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DunningLevelResponse {
    private Long invoiceId;
    private String dunningLevel;
}
