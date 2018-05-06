package cz.muni.pb138project;

import cz.muni.pb138project.forms.CreateCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CategoriesController {

    @RequestMapping("/manage_categories")
    public String manageCategories(Model model) {
        model.addAttribute("createCategoryForm", new CreateCategory());
        return "manage_categories";
    }

    @RequestMapping(value = "/create_category", method = POST)
    public String createCategory(@Valid @ModelAttribute("createCategoryForm") CreateCategory createCategory,
                                 BindingResult bindingResult,
                                 Model model) {
        System.out.println("bindingResult.hasErrors() = " + bindingResult.hasErrors());
        System.out.println(createCategory);
        if (bindingResult.hasErrors()) {
            model.addAttribute("createCategoryForm", createCategory);
            return "manage_categories";
        }
        return "redirect:/manage_categories";
    }
}
