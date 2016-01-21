/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.test.extpc;

import fish.payara.test.extpc.entity.Child;
import fish.payara.test.extpc.entity.Parent;
import fish.payara.test.extpc.entity.boundary.ParentEditService;
import fish.payara.test.extpc.entity.boundary.ParentService;
import java.io.File;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.glassfish.embeddable.CommandResult;
import static org.junit.Assert.assertEquals;
import org.glassfish.embeddable.CommandRunner;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishRuntime;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author UI187816
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
//        if (configURL == null) {
        server = runtime.newGlassFish();
//        } else {
//            GlassFishProperties glassFishProperties = new GlassFishProperties();
//            try {
//                glassFishProperties.setConfigFileURI(configURL.toURI().toString());
//            } catch (URISyntaxException e) {
//                logger.error("Problem with loading domain configuration", e);
//            }
//            server = runtime.newGlassFish(glassFishProperties);
//        }
        server.start();
        asadmin = server.getCommandRunner();
        deployer = server.getDeployer();

        asadmin("create-jdbc-connection-pool", "--datasourceclassname", "org.h2.jdbcx.JdbcDataSource", "--restype", "javax.sql.XADataSource",
                "--property", "password=sa:url=jdbc\\:h2\\:target/test-db;MODE\\=Oracle;DB_CLOSE_ON_EXIT\\=FALSE;TRACE_LEVEL_SYSTEM_OUT\\=2;MVCC\\=TRUE:user=sa", "h2pool");
        asadmin("create-jdbc-resource", "--connectionpoolid", "h2pool", "jdbc/h2");

        deployer.deploy(new File("target/classes"), "--name", "extpc");

        parentService = InitialContext.doLookup("java:global/extpc/ParentService");
        editService = InitialContext.doLookup("java:global/extpc/ParentEditService");
    }

    public static void asadmin(String command, String... params) {
        CommandResult result = asadmin.run(command, params);
        System.out.println(result.getOutput());
        if (result.getExitStatus() != CommandResult.ExitStatus.SUCCESS) {
            if (result.getFailureCause() != null) {
                result.getFailureCause().printStackTrace();
            }
            throw new RuntimeException(command + " failed");
        }
    }

    @Test
    public void extendedPcTest() {
        Parent p = parentService.create();
        editService.setParent(p.getId());
        assertEquals(0, editService.childrenCount());
        Child c1 = editService.addChild();
        Child c2 = editService.addChild();
        editService.save();
        assertEquals(2, editService.childrenCount());
        assertEquals(2, parentService.childrenCount(p));
        parentService.deleteParent(p);
    }

}
