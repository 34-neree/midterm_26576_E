package com.local.cooperative.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberProfileRequest {
    private String nationalId;
    private String occupation;
    private String notes;
    private UUID memberId;
}
