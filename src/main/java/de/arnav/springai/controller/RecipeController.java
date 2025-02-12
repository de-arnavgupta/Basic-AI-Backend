package de.arnav.springai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface RecipeController {

    @GetMapping("/createRecipe")
    String createRecipe(@RequestParam String ingredients,
                        @RequestParam(defaultValue = "any") String cuisine,
                        @RequestParam(defaultValue = "none") String dietaryRestrictions);
}
