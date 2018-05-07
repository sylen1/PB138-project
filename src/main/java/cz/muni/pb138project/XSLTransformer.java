package cz.muni.pb138project;

import org.springframework.stereotype.Component;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class XSLTransformer {

    public String transform(String xml, String xsltFile) throws TransformerException {
        StringReader reader = new StringReader(xml);
        StringWriter writer = new StringWriter();
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer(new StreamSource(
                XSLTransformer.class.getResourceAsStream("/xslt/" + xsltFile)
        ));

        transformer.transform(new StreamSource(reader), new StreamResult(writer));

        return writer.toString();
    }
}
