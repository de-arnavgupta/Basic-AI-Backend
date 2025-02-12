package de.arnav.springai.controller.impl;

import de.arnav.springai.controller.RecipeController;
import de.arnav.springai.service.RecipeService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeControllerImpl implements RecipeController {

    private final RecipeService recipeService;

    public RecipeControllerImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public String createRecipe(@RequestParam String ingredients,
                               @RequestParam(defaultValue = "any") String cuisine,
                               @RequestParam(defaultValue = "none") String dietaryRestrictions) {
        return recipeService.createRecipe(ingredients, cuisine, dietaryRestrictions);
    }
}
