package com.local.cooperative.service;

import com.local.cooperative.dto.*;
import com.local.cooperative.exception.BadRequestException;
import com.local.cooperative.exception.ResourceNotFoundException;
import com.local.cooperative.model.Location;
import com.local.cooperative.model.Member;
import com.local.cooperative.repository.LocationRepository;
import com.local.cooperative.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;

    public Member create(MemberRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        Member member = Member.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();

        // Assign location (village) if provided
        if (request.getLocationId() != null) {
            Location location = locationRepository.findById(request.getLocationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Location not found"));
            member.setLocation(location);
        }

        return memberRepository.save(member);
    }

    public Page<Member> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return memberRepository.findAll(pageable);
    }

    public Member getById(UUID id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
    }

    public Member update(UUID id, MemberRequest request) {
        Member member = getById(id);

        if (!member.getEmail().equalsIgnoreCase(request.getEmail())
                && memberRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());

        if (request.getLocationId() != null) {
            Location location = locationRepository.findById(request.getLocationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Location not found"));
            member.setLocation(location);
        }

        return memberRepository.save(member);
    }

    public Member patch(UUID id, MemberPatchRequest request) {
        Member member = getById(id);

        if (request.getFirstName() != null)
            member.setFirstName(request.getFirstName());
        if (request.getLastName() != null)
            member.setLastName(request.getLastName());
        if (request.getPhone() != null)
            member.setPhone(request.getPhone());

        return memberRepository.save(member);
    }

    public void delete(UUID id) {
        Member member = getById(id);
        memberRepository.delete(member);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public Page<Member> findByProvince(String provinceIdentifier, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return memberRepository.findAllByProvinceCodeOrName(provinceIdentifier, provinceIdentifier, pageable);
    }

    public Member updateLocation(Member member) {
        return memberRepository.save(member);
    }

    public MemberDetailsResponse toDetails(Member member) {
        return MemberDetailsResponse.builder()
                .id(member.getId())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .location(buildLocation(member))
                .build();
    }

    private LocationDetailsResponse buildLocation(Member member) {
        if (member.getLocation() == null) {
            return null;
        }

        Location village = member.getLocation();
        Location cell = village.getParent();
        Location sector = cell != null ? cell.getParent() : null;
        Location district = sector != null ? sector.getParent() : null;
        Location province = district != null ? district.getParent() : null;

        return LocationDetailsResponse.builder()
                .userId(member.getId())
                .villageId(village.getId())
                .villageCode(village.getCode())
                .villageName(village.getName())
                .cellId(cell != null ? cell.getId() : null)
                .cellCode(cell != null ? cell.getCode() : null)
                .cellName(cell != null ? cell.getName() : null)
                .sectorId(sector != null ? sector.getId() : null)
                .sectorCode(sector != null ? sector.getCode() : null)
                .sectorName(sector != null ? sector.getName() : null)
                .districtId(district != null ? district.getId() : null)
                .districtCode(district != null ? district.getCode() : null)
                .districtName(district != null ? district.getName() : null)
                .provinceId(province != null ? province.getId() : null)
                .provinceCode(province != null ? province.getCode() : null)
                .provinceName(province != null ? province.getName() : null)
                .build();
    }
}
