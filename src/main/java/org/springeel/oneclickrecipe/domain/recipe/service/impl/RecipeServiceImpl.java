package org.springeel.oneclickrecipe.domain.recipe.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springeel.oneclickrecipe.domain.recipe.dto.service.RecipeCreateServiceRequestDto;
import org.springeel.oneclickrecipe.domain.recipe.dto.service.RecipeUpdateServiceRequestDto;
import org.springeel.oneclickrecipe.domain.recipe.entity.Recipe;
import org.springeel.oneclickrecipe.domain.recipe.exception.NotFoundRecipeException;
import org.springeel.oneclickrecipe.domain.recipe.exception.RecipeErrorCode;
import org.springeel.oneclickrecipe.domain.recipe.mapper.entity.RecipeEntityMapper;
import org.springeel.oneclickrecipe.domain.recipe.repository.RecipeRepository;
import org.springeel.oneclickrecipe.domain.recipe.service.RecipeService;
import org.springeel.oneclickrecipe.domain.user.entity.User;
import org.springeel.oneclickrecipe.global.util.S3Provider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeEntityMapper recipeEntityMapper;
    private final S3Provider s3Provider;

    public void createRecipe(final RecipeCreateServiceRequestDto requestDto, User user) {
        String folderName = requestDto.title() + UUID.randomUUID();
        Recipe recipe = recipeEntityMapper.toRecipe(requestDto, user, folderName);
        recipeRepository.save(recipe);
        s3Provider.createFolder(folderName);
    }

    public void deleteRecipe(Long recipeId, User user) {
        Recipe recipe = recipeRepository.findByIdAndUser(recipeId, user)
            .orElseThrow(() -> new NotFoundRecipeException(RecipeErrorCode.NOT_FOUND_RECIPE));
        recipeRepository.delete(recipe);
    }

    @Transactional
    public void updateRecipe(final RecipeUpdateServiceRequestDto requestDto, User user,
        Long recipeId) {
        Recipe recipe = recipeRepository.findByIdAndUser(recipeId, user)
            .orElseThrow(() -> new NotFoundRecipeException(RecipeErrorCode.NOT_FOUND_RECIPE));
        recipe.updateRecipe(
            requestDto.title(),
            requestDto.intro(),
            requestDto.serving(),
            requestDto.videoPath()
        );
    }
}
