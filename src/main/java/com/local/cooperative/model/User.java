package com.local.cooperative.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    // Many Users belong to one Village (Many-to-One)
    // When saving a User, only village_id is needed.
    // The system resolves: Village → Cell → Sector → District → Province
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id", nullable = false)
    @JsonBackReference
    private Village village;

    // One User has One Account (One-to-One) - mapped by Account entity
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;

    // Many Users belong to Many SavingsGroups (Many-to-Many)
    // This creates a join table "user_savings_group"
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_savings_group",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "savings_group_id")
    )
    private Set<SavingsGroup> savingsGroups = new HashSet<>();

    // Constructors
    public User() {}

    public User(String firstName, String lastName, String email, String phone, Village village) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.village = village;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Village getVillage() { return village; }
    public void setVillage(Village village) { this.village = village; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public Set<SavingsGroup> getSavingsGroups() { return savingsGroups; }
    public void setSavingsGroups(Set<SavingsGroup> savingsGroups) { this.savingsGroups = savingsGroups; }
}
