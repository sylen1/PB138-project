package cz.muni.pb138project;

import cz.muni.pb138project.forms.CreateCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.xml.transform.TransformerException;
import javax.xml.xquery.XQException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CategoriesController {

    @Autowired
    private XSLTransformer transformer;

    @Autowired
    private XMLDatabaseManager databaseManager;

    @RequestMapping("/manage_categories")
    public String manageCategories(Model model) throws TransformerException, XQException {
        model.addAttribute("createCategoryForm", new CreateCategory());
        addMenu(model);
        return "manage_categories";
    }

    @RequestMapping(value = "/create_category", method = POST)
    public String createCategory(@Valid @ModelAttribute("createCategoryForm") CreateCategory createCategory,
                                 BindingResult bindingResult,
                                 Model model) throws TransformerException, XQException {

        addMenu(model);

        if (bindingResult.hasErrors()) {
            model.addAttribute("createCategoryForm", createCategory);
            return "manage_categories";
        }
        // TODO: test unique
//        databaseManager.createCategory(createCategory.getName());
        return "redirect:/manage_categories";
    }

    private void addMenu(Model model) throws TransformerException, XQException {
        String xmlCategories = databaseManager.findAllCategories();
        String htmlCategories = transformer.transform(xmlCategories, "XSLTTemplateCategory.xsl");
        model.addAttribute("categoryMenu", htmlCategories);
    }

}
