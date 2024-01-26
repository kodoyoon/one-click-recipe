package org.springeel.oneclickrecipe.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springeel.oneclickrecipe.domain.user.dto.controller.UserLoginControllerRequestDto;
import org.springeel.oneclickrecipe.domain.user.dto.controller.UserSignUpControllerRequestDto;
import org.springeel.oneclickrecipe.domain.user.dto.service.UserLoginServiceRequestDto;
import org.springeel.oneclickrecipe.domain.user.dto.service.UserSignUpServiceRequestDto;
import org.springeel.oneclickrecipe.domain.user.mapper.dto.UserDtoMapper;
import org.springeel.oneclickrecipe.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserDtoMapper userDtoMapper;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> singUp(
        @Valid @RequestBody UserSignUpControllerRequestDto controllerRequestDto
    ) {
        UserSignUpServiceRequestDto serviceRequestDto = userDtoMapper.toUserSignUpServiceRequestDto(
            controllerRequestDto);
        userService.signUp(serviceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
        @Valid @RequestBody UserLoginControllerRequestDto controllerRequestDto,
        HttpServletResponse httpServletResponse
    ) {
        UserLoginServiceRequestDto serviceRequestDto = userDtoMapper.toUserLoginServiceRequestDto(
            controllerRequestDto);
        userService.login(serviceRequestDto, httpServletResponse);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        HttpServletResponse httpServletResponse
    ) {
        userService.logout(httpServletResponse);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshAccessToken(
        @CookieValue("Refresh-Token") String refreshToken,
        HttpServletResponse httpServletResponse
    ) {
        log.info("refresh Token: {}", URLDecoder.decode(refreshToken, StandardCharsets.UTF_8));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
