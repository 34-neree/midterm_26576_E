package com.local.cooperative.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {
    private String transactionType;
    private BigDecimal amount;
    private String description;
    private UUID accountId;
}
