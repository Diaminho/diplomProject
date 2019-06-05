package test;


import org.junit.Before;
import org.junit.Test;
import sample.Experiment;

public class FixWindowsTest {

    Experiment experiment = new Experiment();
    //ExperimentManager experimentManager = new ExperimentManager(null, experiment);

    @Before
    public void setUp() throws Exception {
        //experiment.setDefaultMaterialsQuality();


        //Method annotated with `@BeforeClass` will execute once before any of the test methods in this class.

        //This method could be used to set up any test fixtures that are computationally expensive and shared by several test methods. e.g. establishing database connections

        //Sometimes several tests need to share computationally expensive setup (like logging into a database). While this can compromise the independence of tests, sometimes it is a necessary optimization. From http://junit.sourceforge.net/javadoc/org/junit/BeforeClass.html

    }




    @Test
    public void testFixWindowLogic() {

    }

}
