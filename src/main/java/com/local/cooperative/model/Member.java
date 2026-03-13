package com.local.cooperative.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "members", uniqueConstraints = {
        @UniqueConstraint(name = "uk_member_email", columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 150)
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @NotBlank
    @Size(max = 20)
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    // Many-to-One: many members belong to one village (location)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    // One-to-One: each member has one profile
    @JsonIgnore
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private MemberProfile profile;

    // One-to-Many: one member can have many accounts
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    // Many-to-Many: members can belong to many savings groups
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "member_savings_groups", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "savings_group_id"))
    private List<SavingsGroup> savingsGroups = new ArrayList<>();
}
