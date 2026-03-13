package com.local.cooperative.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequest {
    private String code;
    private String name;
    private String type;
    private UUID parentId;
}
