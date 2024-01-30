package org.springeel.oneclickrecipe.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springeel.oneclickrecipe.domain.follow.service.FollowService;
import org.springeel.oneclickrecipe.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")

public class FollowController {

    private final FollowService followService;

    @PostMapping("/follows/{userId}") //사용자 구독 생성
    public ResponseEntity<?> create(
        @PathVariable(name = "userId") Long followingId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        followService.create(userDetails.user(), followingId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/follows/{userId}") // 사용자 구독 취소
    public ResponseEntity<?> delete(
        @PathVariable(name = "userId") Long followingId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        followService.delete(userDetails.user(), followingId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/follows/{userId}/count") //특정 구독 조회
    public ResponseEntity<Long> getLikesCount(
        @PathVariable(name = "userId") Long followingId
    ) {
        long likesCount = followService.getLikesCount(followingId);
        return new ResponseEntity<>(likesCount, HttpStatus.OK);
    }

    @GetMapping("/follows/{userId}/status") // 사용자가 특정사용자를 구독 했는지 안했는지
    public ResponseEntity<Boolean> getUserFollowStatus(
        @PathVariable(name = "userId") Long followingId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boolean likeStatus = followService.getUserFollowingStatus(userDetails.user(), followingId);
        return new ResponseEntity<>(likeStatus, HttpStatus.OK);
    }
}
