package org.springeel.oneclickrecipe.domain.recipe.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springeel.oneclickrecipe.domain.recipe.dto.service.RecipeCreateServiceRequestDto;
import org.springeel.oneclickrecipe.domain.recipe.entity.Recipe;
import org.springeel.oneclickrecipe.domain.recipe.mapper.RecipeMapper;
import org.springeel.oneclickrecipe.domain.recipe.repository.RecipeRepository;
import org.springeel.oneclickrecipe.domain.recipe.service.RecipeService;
import org.springeel.oneclickrecipe.domain.user.entity.User;
import org.springeel.oneclickrecipe.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public void createRecipe(final RecipeCreateServiceRequestDto requestDto, Long userId) {
        User user = userRepository.findUserById(userId);
        Recipe recipe = new Recipe(
            requestDto.title(),
            requestDto.intro(),
            requestDto.serving(),
            requestDto.videoPath(),
            user
        );
        recipeRepository.save(recipe);
    }
}
