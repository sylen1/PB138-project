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
                        Model model) throws TransformerException, XQException {

        addMenu(model);
        category = (category != null) ? category : databaseManager.getFirstCategory();

        String xmlEntries = databaseManager.searchMediaByCategory(category);
        String htmlEntries = transformer.transform(xmlEntries, "XSLTTemplateCategory.xsl");
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

        String htmlMedia = transformer.transform(xmlMedia, "XSLTTemplateMedium.xsl");
        model.addAttribute("mediaEntries", htmlMedia);

        return "index";
    }

    @RequestMapping(value = "/add_record", method = GET)
    public String addRecord(Model model) throws TransformerException, XQException {
        addMenu(model);
        return "add_record";
    }

    @RequestMapping(value = "/add_record", method = POST)
    public String addRecordPost(@RequestBody MultiValueMap<String, String> form) throws XQException {
        String recordXml = newRecordToXml(form);
        databaseManager.addMediumToCollection(recordXml, form.getFirst("category"));
        return "redirect:/";
    }

    private void addMenu(Model model) throws TransformerException, XQException {
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
