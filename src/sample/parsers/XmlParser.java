package sample.parsers;

import javafx.scene.image.Image;
import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.*;
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
        private static Document doc;

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
                        if (((Element)nodeList.item(i)).hasAttribute("image") && !((Element) nodeList.item(i)).getAttribute("image").equals("")) {
                            res.get(res.size() - 1).setMaterialImage(new Image(((Element) nodeList.item(i)).getAttribute("image")));
                        }
                    }
                    else {
                        if (((Element) nodeList.item(i)).getTagName().equals("property")) {
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


        public static void writeXMLFile(String fileName, List materialsList){

            try {
                // Строим объектную модель исходного XML файла
                //final File xmlFile = new File(fileName);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                doc = db.newDocument();
                doc.normalize();

                //CREATING ROOT ELEMENT
                Element root = doc.createElement("materials");
                doc.appendChild(root);
                //


                // Получаем корневой элемент
                Node materials = doc.getFirstChild();


                for (Object mat:materialsList) {
                    Element materialElem=doc.createElement("material");
                    materials.appendChild(materialElem);
                    // set Material Name
                    setAttributeName(materialElem,(Material)mat);
                    //
                    // set Image
                    setAttributeImage(materialElem,(Material)mat);
                    //

                    //set properties for current material
                    for (Object name:((Material)mat).getProperties().keySet()) {
                        setProperty(materialElem, (String)name, ((Material)mat).getProperties().get(name));
                    }
                }

                // Записываем изменения в XML файл
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(fileName));
                transformer.transform(source, result);
                System.out.println("Изменения сохранены");

            } catch (ParserConfigurationException
                    | TransformerConfigurationException ex) {
                Logger.getLogger(XmlParser.class.getName())
                        .log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(XmlParser.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }


        private static void setAttributeName(Element elem, Material mat){
            Attr attr = doc.createAttribute("name");
            attr.setValue(mat.getName());
            elem.setAttributeNode(attr);
        }

        private static void setAttributeImage(Element elem, Material mat){

            Attr attr = doc.createAttribute("image");
            if (mat.getMaterialImage()!=null) {
                attr.setValue(mat.getMaterialImage().impl_getUrl());
            }
            elem.setAttributeNode(attr);
}

        private static void setProperty(Element elem, String name, String value){
            // property element
            Element property=doc.createElement("proprerty");
            property.setAttribute("name",name);
            property.appendChild(doc.createTextNode(value));
            elem.appendChild(property);
        }

}
