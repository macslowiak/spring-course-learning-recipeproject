package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }


    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository
                .findAll()
                .iterator()
                .forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long l) {
        return recipeToRecipeCommand.convert(findById(l));
    }
    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe recipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(recipe);
        log.debug("Saved recipeID" + savedRecipe.getId());
        return  recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteReceipeCommand(RecipeCommand command) {
        Recipe recipe = recipeCommandToRecipe.convert(command);

        if(recipe!=null) {
            log.debug("Deleted recipeID: " + recipe.getId());
            recipeRepository.delete(recipe);
        }
    }

}
