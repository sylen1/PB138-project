package cz.muni.pb138project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.transform.TransformerException;
import javax.xml.xquery.XQException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class HomeController {

    @Autowired
    private XSLTransformer transformer;

    @Autowired
    private XMLDatabaseManager databaseManager;

    @RequestMapping("")
    public String index(@RequestParam(required = false) String category,
                        Model model) throws TransformerException, XQException {

        addMenu(model);
        category = (category != null) ? category : databaseManager.getFirstCategory();

        String xmlEntries = databaseManager.searchMediaByCategory(category);
        String htmlEntries = transformer.transform(xmlEntries, "media-entries.xsl");
        model.addAttribute("mediaEntries", htmlEntries);
//        model.addAttribute("category", category);
        return "index";
    }

    @RequestMapping(value = "/search", method = POST)
    public String search(@RequestBody MultiValueMap<String,String> formData, Model model) throws TransformerException, XQException {
        addMenu(model);

        String label = formData.getFirst("label");
        String genres = formData.getFirst("tags");
        String[] genresArray = genres.isEmpty() ? null : genres.split(" ");

//        String category =
        Map<String, String> properties = new HashMap<>();
        List<String> keys = formData.get("prop-keys");
        List<String> values = formData.get("prop-values");
        for (int i = 0; i < keys.size(); i++) {
            if (!keys.get(i).isEmpty() && !values.get(i).isEmpty()) {
                properties.put(keys.get(i), values.get(i));
            }
        }

        String xmlMedia = databaseManager.searchMedia(label.isEmpty() ? null : label,
                genresArray,
                properties.isEmpty() ? null : properties,
                null);

        String htmlMedia = transformer.transform(xmlMedia, "media-entries.xsl");
        model.addAttribute("mediaEntries", htmlMedia);

        return "index";
    }

    private void addMenu(Model model) throws TransformerException, XQException {
        String xmlCategories = databaseManager.findAllCategories();
        String htmlCategories = transformer.transform(xmlCategories, "categories.xsl");
        model.addAttribute("categoryMenu", htmlCategories);
    }

}
