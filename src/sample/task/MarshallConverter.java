package sample.task;

import javafx.scene.image.Image;
import sample.Experiment;
import sample.resource.Material;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
        return experiment;
    }
}
