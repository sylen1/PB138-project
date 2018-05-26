package cz.muni.pb138project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xmldb.api.base.XMLDBException;

import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class HomeController {

    @Autowired
    private XSLTransformer transformer;

    @Autowired
    private XMLDatabaseManager databaseManager;

    @RequestMapping("")
    public String index(@RequestParam(required = false) String category,
                        Model model,
                        HttpSession session) throws TransformerException, XMLDBException {

        addMenu(model);

        if (category != null) {
            session.setAttribute("category", category);
        } else {
            category = (String) session.getAttribute("category");
            if (category == null) {
                category = databaseManager.getFirstCategory();
                session.setAttribute("category", category);
            }
        }

        String xmlEntries = databaseManager.searchMediaByCategory(category);
        String htmlEntries = transformer.transform(xmlEntries, "XSLTTemplateMedium.xsl");
        model.addAttribute("mediaEntries", htmlEntries);
//        model.addAttribute("category", category);
        return "index";
    }

    @RequestMapping(value = "/search", method = POST)
    public String search(@RequestBody MultiValueMap<String,String> formData, Model model, HttpSession session) throws TransformerException, XMLDBException {
        addMenu(model);

        String label = formData.getFirst("label");
        String genres = formData.getFirst("tags");
        String[] genresArray = genres.isEmpty() ? null : genres.split(" ");

        String category = null;
        if (formData.containsKey("only-selected-category")) {
            category = (String) session.getAttribute("category");
        }
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
                category);

        String htmlMedia = transformer.transform(xmlMedia, "XSLTTemplateMedium.xsl");
        model.addAttribute("mediaEntries", htmlMedia);

        return "index";
    }

    @RequestMapping(value = "/add_record", method = GET)
    public String addRecord(Model model) throws TransformerException, XMLDBException {
        addMenu(model);
        String xmlEntries = databaseManager.findAllCategories();
        String htmlEntries = transformer.transform(xmlEntries, "XSLTTemplateCategorySelect.xsl");
        model.addAttribute("categoryEntries", htmlEntries);
        return "add_record";
    }

    @RequestMapping(value = "/add_record", method = POST)
    public String addRecordPost(@RequestBody MultiValueMap<String, String> form) throws XMLDBException {
        String recordXml = newRecordToXml(form);
        databaseManager.addMediumToCollection(recordXml, form.getFirst("category"));
        return "redirect:/";
    }

    @RequestMapping(value = "/delete_medium", method = GET)
    public String deleteMedium(String id) throws XMLDBException {
        if (id != null) {
            databaseManager.deleteMediumFromCollection(id);
        }
        return "redirect:/";
    }

    private void addMenu(Model model) throws TransformerException, XMLDBException {
        String xmlCategories = databaseManager.findAllCategories();
        String htmlCategories = transformer.transform(xmlCategories, "XSLTTemplateCategory.xsl");
        model.addAttribute("categoryMenu", htmlCategories);
    }

    private String newRecordToXml(MultiValueMap<String, String> form) {
        StringBuilder sb = new StringBuilder();

        sb.append("<medium>")
                .append("<id>").append(UUID.randomUUID()).append("</id>")
                .append("<label>").append(form.getFirst("label")).append("</label>")
                .append("<type>").append(form.getFirst("type")).append("</type>");

        String[] genres = form.getFirst("tags").split(" ");
        if (genres.length > 0) {
            sb.append("<genres>");
            for (String genre : genres) {
                sb.append("<genre>")
                        .append(genre)
                        .append("</genre>");
            }
            sb.append("</genres>");
        }


        List<String> entries = form.get("entries");
        if (!entries.isEmpty()) {
            sb.append("<content>");
            for (String entry : entries) {
                sb.append("<entry>").append(entry).append("</entry>");
            }
            sb.append("</content>");
        }

        List<String> keys = form.get("prop-keys");
        List<String> values = form.get("prop-values");

        sb.append("<properties>");
        for (int i = 0; i < keys.size(); i++) {
            if (!keys.get(i).isEmpty() && !values.get(i).isEmpty()) {
                sb.append("<").append(keys.get(i).toLowerCase()).append(">")
                        .append(values.get(i))
                        .append("</").append(keys.get(i).toLowerCase()).append(">");
            }
        }
        sb.append("</properties>");

        sb.append("</medium>");

        return sb.toString();
    }

}
