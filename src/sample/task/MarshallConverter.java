package sample.task;

import sample.Experiment;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class MarshallConverter {
    public static void marshalingExample(Experiment experiment) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Experiment.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(experiment, new File("experiment.xml"));
    }
}
