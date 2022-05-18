package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping({"/{id}/show"})
    public String getById(Model model, @PathVariable Long id) {
        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/single-recipe";
    }

    @RequestMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @PostMapping(name = "recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }


    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return  "recipe/recipeform";
    }
}
