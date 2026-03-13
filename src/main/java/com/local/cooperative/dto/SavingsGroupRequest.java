package com.local.cooperative.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavingsGroupRequest {
    private String name;
    private String description;
}
