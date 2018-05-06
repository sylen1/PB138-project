package cz.muni.pb138project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class HomeController {

    @RequestMapping("")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = "/search", method = POST)
    public String search(@RequestBody MultiValueMap<String,String> formData) {
        System.out.println(formData);
        return "redirect:/";
    }

}
