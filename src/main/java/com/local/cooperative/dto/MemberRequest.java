package com.local.cooperative.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UUID locationId;
}
