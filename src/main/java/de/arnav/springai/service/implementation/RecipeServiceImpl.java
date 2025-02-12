package de.arnav.springai.service.implementation;

import de.arnav.springai.service.RecipeService;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Override
    public String createRecipe(String ingredients, String cuisine, String dietaryRestrictions) {
        return "Recipe created with " + ingredients + " in " + cuisine + " cuisine with " + dietaryRestrictions + " restrictions.";
    }
}
