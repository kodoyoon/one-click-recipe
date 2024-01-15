package org.springeel.oneclickrecipe.domain.recipeprocess.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springeel.oneclickrecipe.domain.recipe.entity.Recipe;
import org.springeel.oneclickrecipe.global.entity.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_RECIPE_PROCESS")
@Entity
public class RecipeProcess extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sequence;

    @Column
    private String description;

    @Column
    private Short time;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "recipe_Id", nullable = false)
    private Recipe recipe;

    @Builder
    public RecipeProcess(
        Recipe recipe,
        String sequence,
        String description,
        Short time,
        String imageUrl
    ) {
        this.recipe = recipe;
        this.sequence = sequence;
        this.description = description;
        this.time = time;
        this.imageUrl = imageUrl;
    }

    public void updateRecipe(String sequence, String description, short time, String imageUrl) {
        this.sequence = sequence;
        this.description = description;
        this.time = time;
        this.imageUrl = imageUrl;
    }
}
