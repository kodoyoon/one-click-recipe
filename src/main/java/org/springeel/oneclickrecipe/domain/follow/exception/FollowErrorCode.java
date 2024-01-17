package org.springeel.oneclickrecipe.domain.follow.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springeel.oneclickrecipe.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FollowErrorCode implements ErrorCode {

    // 400
    ALREADY_EXIST_FOLLOW(HttpStatus.BAD_REQUEST, "이미 구독자가 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_FOLLOW(HttpStatus.FORBIDDEN, "구독자에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_FOLLOW(HttpStatus.NOT_FOUND, "구독자를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
