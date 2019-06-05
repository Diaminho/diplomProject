package sample.task;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import sample.Experiment;
import sample.resource.Material;

import javax.xml.bind.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MarshallConverter {
    public static void marshalingToXML(Experiment experiment) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Experiment.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(experiment, new File("experiment.xml"));
    }

    public static Experiment marshalingToExperiment() {
        File xmlFile = new File("experiment.xml");

        JAXBContext jaxbContext;
        Experiment experiment = new Experiment();
        try
        {
            jaxbContext = JAXBContext.newInstance(Experiment.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            experiment = (Experiment) jaxbUnmarshaller.unmarshal(xmlFile);

            for (Material m: experiment.getMaterialMap().keySet()) {
                m.setMaterialImage(new Image(m.getMaterialPath()));
            }
            experiment.fillNeededMaterials();
            List<Integer> brigadesScenarioList = new ArrayList<>();
            for (Double d: experiment.getBrigades()) {
                brigadesScenarioList.add(-1);
            }

            experiment.setScenarioBrigadesList(brigadesScenarioList);
            //System.out.println(experiment);
        }

        catch (UnmarshalException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка загрузки настроек");
            alert.setHeaderText("Ошибка");
            if (e.getCause().toString().contains("java.io.FileNotFoundException:")) {
                alert.setContentText("Файл настроек не был найден");
            }
            else if(e.getCause().toString().contains("org.xml.sax.SAXParseException;")) {
                alert.setContentText("Некорректный файл настроек");
            }
            alert.showAndWait();
            return null;
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешная загрузка");
        alert.setHeaderText("Успех");
        alert.setContentText("Загрузка файла настроек прошла успешно.");
        alert.show();
        return experiment;
    }
}
