package de.arnav.springai.service.implementation;

import de.arnav.springai.service.RecipeService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {


    private final ChatModel chatModel;
    public RecipeServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients, String cuisine, String dietaryRestrictions) {
        var template = """
                    I want to create a recipe using the following ingredients: {ingredients}.
                    The cuisine should be {cuisine}.
                    Please consider these dietary restrictions {dietaryRestrictions}.
                    Please provide me detail recipe it should contain - title of the recipe, list of ingredients and steps to prepare it.
                """;
        PromptTemplate promptTemplate = new PromptTemplate(template);
        Map<String, Object> params = Map.of(
                "ingredients", ingredients,
                "cuisine", cuisine,
                "dietaryRestrictions", dietaryRestrictions
        );
        Prompt prompt = promptTemplate.create(params);
        return chatModel.call(prompt).getResult().getOutput().getContent();
    }
}
