package fish.payara.test.extpc;

import fish.payara.test.extpc.entity.Parent;
import fish.payara.test.extpc.entity.boundary.ParentEditService;
import fish.payara.test.extpc.entity.boundary.ParentService;
import java.io.File;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.glassfish.embeddable.CommandRunner;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishRuntime;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Patrik Dudits
 */
public class ExtendedPersistenceContextTest {

    private static GlassFishRuntime runtime;
    private static GlassFish server;
    private static CommandRunner asadmin;
    private static Deployer deployer;

    private static ParentService parentService;
    private static ParentEditService editService;

    @BeforeClass
    public static void deploy() throws GlassFishException, NamingException {
        runtime = GlassFishRuntime.bootstrap();
        server = runtime.newGlassFish();
        server.start();
        asadmin = server.getCommandRunner();
        deployer = server.getDeployer();

        deployer.deploy(new File("target/classes"), "--name", "extpc");

        parentService = InitialContext.doLookup("java:global/extpc/ParentService");
        editService = InitialContext.doLookup("java:global/extpc/ParentEditService");
    }

    @Test
    public void extendedPcTest() {
        Parent p = parentService.create();
        editService.setParent(p.getId());
    }

}
