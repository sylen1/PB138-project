package cz.muni.pb138project;

import cz.muni.pb138project.forms.CreateCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xmldb.api.base.XMLDBException;

import javax.validation.Valid;
import javax.xml.transform.TransformerException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CategoriesController {

    @Autowired
    private XSLTransformer transformer;

    @Autowired
    private XMLDatabaseManager databaseManager;

    @RequestMapping(value = "/manage_categories", method = GET)
    public String manageCategories(Model model) throws TransformerException, XMLDBException {
        model.addAttribute("createCategoryForm", new CreateCategory());
        addMenu(model);

        String xmlCategories = databaseManager.findAllCategoriesWithCounts();
        String htmlCategories = transformer.transform(xmlCategories, "XSLTTemplateCategoryTable.xsl");
        model.addAttribute("categories", htmlCategories);
        return "manage_categories";
    }

    @RequestMapping(value = "/create_category", method = POST)
    public String createCategory(@Valid @ModelAttribute("createCategoryForm") CreateCategory createCategory,
                                 BindingResult bindingResult,
                                 Model model) throws TransformerException, XMLDBException {

        addMenu(model);

        if (bindingResult.hasErrors()) {
            model.addAttribute("createCategoryForm", createCategory);
            return "manage_categories";
        }
        // TODO: test unique
        databaseManager.createCategory(createCategory.getName());
        return "redirect:/manage_categories";
    }

    @RequestMapping(value = "/delete_category", method = GET)
    public String deleteCategory(String id) throws XMLDBException {
        if (id != null) {
            databaseManager.deleteCategory(id);
        }
        return "redirect:/manage_categories";
    }

    private void addMenu(Model model) throws TransformerException, XMLDBException {
        String xmlCategories = databaseManager.findAllCategories();
        String htmlCategories = transformer.transform(xmlCategories, "XSLTTemplateCategory.xsl");
        model.addAttribute("categoryMenu", htmlCategories);
    }

}
