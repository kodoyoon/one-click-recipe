package org.springeel.oneclickrecipe.domain.food.controller;

import lombok.RequiredArgsConstructor;
import org.springeel.oneclickrecipe.domain.food.dto.controller.FoodCreateControllerRequestDto;
import org.springeel.oneclickrecipe.domain.food.dto.service.FoodCreateServiceRequestDto;
import org.springeel.oneclickrecipe.domain.food.mapper.dto.FoodDtoMapper;
import org.springeel.oneclickrecipe.domain.food.service.FoodService;
import org.springeel.oneclickrecipe.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@RestController
public class FoodController {

    private final FoodService foodService;
    private final FoodDtoMapper dtoMapper;

    @PostMapping("/foods/create-food")
    public ResponseEntity<?> createFood(
        @RequestBody FoodCreateControllerRequestDto controllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        FoodCreateServiceRequestDto createServiceRequestDto =
            dtoMapper.toFoodCreateServiceDto(controllerRequestDto);
        foodService.createFood(createServiceRequestDto, userDetails.user());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/foods/{foodId}")
    public ResponseEntity<?> deleteFood(
        @PathVariable Long foodId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        foodService.deleteFood(userDetails.user(), foodId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
