package com.local.cooperative.controller;

import com.local.cooperative.dto.MemberDetailsResponse;
import com.local.cooperative.dto.MemberPatchRequest;
import com.local.cooperative.dto.MemberRequest;
import com.local.cooperative.model.Location;
import com.local.cooperative.model.Member;
import com.local.cooperative.service.LocationService;
import com.local.cooperative.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Member> create(@RequestBody MemberRequest request) {
        return new ResponseEntity<>(memberService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Member>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(memberService.getAll(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDetailsResponse> getById(@PathVariable UUID id) {
        Member member = memberService.getById(id);
        return ResponseEntity.ok(memberService.toDetails(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> update(@PathVariable UUID id, @RequestBody MemberRequest request) {
        return ResponseEntity.ok(memberService.update(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Member> patch(@PathVariable UUID id, @RequestBody MemberPatchRequest request) {
        return ResponseEntity.ok(memberService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(memberService.existsByEmail(email));
    }

    @GetMapping("/by-province")
    public ResponseEntity<Page<MemberDetailsResponse>> findByProvince(
            @RequestParam String province,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Page<Member> memberPage = memberService.findByProvince(province, page, size, sortBy, direction);
        Page<MemberDetailsResponse> responsePage = memberPage.map(memberService::toDetails);
        return ResponseEntity.ok(responsePage);
    }

    @PostMapping("/{memberId}/location/{locationId}")
    public ResponseEntity<MemberDetailsResponse> assignLocation(
            @PathVariable UUID memberId, @PathVariable UUID locationId) {
        Member member = memberService.getById(memberId);
        Location location = locationService.getById(locationId);
        member.setLocation(location);
        Member saved = memberService.updateLocation(member);
        return ResponseEntity.ok(memberService.toDetails(saved));
    }
}
