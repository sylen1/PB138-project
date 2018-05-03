package cz.muni.pb138project;

import net.xqj.exist.ExistXQDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.namespace.QName;
import javax.xml.xquery.*;

@Controller
public class HomeController {

    @RequestMapping("")
    public String index(Model model) {
        String msg = checkDb();
        model.addAttribute("msg", msg);
        return "index";
    }

    private String checkDb() {
        try {
            XQDataSource xqs = new ExistXQDataSource();
            xqs.setProperty("serverName", "localhost");
            xqs.setProperty("port", "8080");

            XQConnection conn = xqs.getConnection();

            XQPreparedExpression xqpe =
                    conn.prepareExpression("declare variable $x as xs:string external; $x");

            xqpe.bindString(new QName("x"), "Hello World!", null);

            XQResultSequence rs = xqpe.executeQuery();

            while (rs.next()) {
                rs.getItemAsString(null);
            }

            conn.close();

            return "Successfully connected to eXist at localhost:8080";
        } catch (XQException e) {
            e.printStackTrace();
            return "Couldn't connect to eXist because: " + e.getMessage();
        }
    }
}
