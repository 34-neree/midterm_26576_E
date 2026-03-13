package com.local.cooperative.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberPatchRequest {
    private String firstName;
    private String lastName;
    private String phone;
}
