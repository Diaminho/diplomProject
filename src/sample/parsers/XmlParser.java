package sample.parsers;

import javafx.scene.image.Image;
import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sample.resources.Material;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlParser{

        public XmlParser() throws ParserConfigurationException, IOException, SAXException {

        }

        public static List<Material> readXMLFile(String fileName) throws ParserConfigurationException, IOException {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = null;
            try {
                document = builder.parse(new File(fileName));
            } catch (org.xml.sax.SAXException e) {
                e.printStackTrace();
            }
            Element element = document.getDocumentElement();

            List<Material> res=new ArrayList<>();
            printElement(element.getChildNodes(), res);
            return res;
        }

        public static void printElement(NodeList nodeList, List<Material> res) {
            String key,value;
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i) instanceof Element) {
                    //System.out.println((Element) nodeList.item(i));
                    if(((Element) nodeList.item(i)).hasAttribute("name") && ((Element) nodeList.item(i)).getTagName()=="material") {
                        System.out.println(((Element) nodeList.item(i)).getAttribute("material"));
                        res.add(new Material(((Element) nodeList.item(i)).getAttribute("name")));
                        res.get(res.size()-1).setMaterialImage(new Image(((Element) nodeList.item(i)).getAttribute("image")));
                    }
                    else {
                        if (((Element) nodeList.item(i)).getTagName()=="property") {
                            //res.add(nodeList.item(i).getTextContent());
                            key=((Element) nodeList.item(i)).getAttribute("name");
                            value=((Element) nodeList.item(i)).getTextContent();
                            System.out.println(((Element) nodeList.item(i)).getTagName() + ": " + key+"  value: "+value);
                            res.get(res.size()-1).addProperty(key,value);
                        }
                    }
                    if (nodeList.item(i).hasChildNodes()) {
                        printElement(nodeList.item(i).getChildNodes(),res);
                    }
                }
            }

        }

        /*
        public void writeXMLFile(String fileName, List materialsList){

            try {
                // Строим объектную модель исходного XML файла
                final File xmlFile = new File(fileName);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = db.parse(xmlFile);
                doc.normalize();

                // Получаем корневой элемент
                Node settings = doc.getFirstChild();

                // файла. Однако, лучше использовать более надежный метод
                // getElementsByTagName().
                Node setting=doc.getElementsByTagName("material").item(0);

                // Для этого - пробежимся по всем дочерним элементам.
                NodeList nodeList=setting.getChildNodes();


                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node nextNode = nodeList.item(i);

                    if (nextNode.getNodeName().equals("port")) {
                        nextNode.setTextContent(values[1]);
                    } else if (nextNode.getNodeName().equals("server")) {
                        nextNode.setTextContent(values[0]);
                    } else if (nextNode.getNodeName().equals("log")) {
                        nextNode.setTextContent(values[2]);
                    }
                }
                // Записываем изменения в XML файл
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(fileName));
                transformer.transform(source, result);
                System.out.println("Изменения сохранены");

            } catch (IOException | ParserConfigurationException
                    | TransformerConfigurationException ex) {
                Logger.getLogger(XmlParser.class.getName())
                        .log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(XmlParser.class.getName())
                        .log(Level.SEVERE, null, ex);
            } catch (org.xml.sax.SAXException e) {
                e.printStackTrace();
            }
        }
        */
}
