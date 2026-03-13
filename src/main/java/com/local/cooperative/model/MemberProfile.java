package com.local.cooperative.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "member_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    @Size(max = 20)
    @Column(name = "national_id", length = 20)
    private String nationalId;

    @Size(max = 100)
    @Column(name = "occupation", length = 100)
    private String occupation;

    @Size(max = 500)
    @Column(name = "notes", length = 500)
    private String notes;
}
