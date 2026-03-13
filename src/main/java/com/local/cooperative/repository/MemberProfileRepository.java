package com.local.cooperative.repository;

import com.local.cooperative.model.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, UUID> {
}
