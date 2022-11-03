package za.co.nedj.app.nedj.leaderboardapi;

import org.shipstone.swagger.integration.annotation.SwaggerUIConfiguration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 *
 * @author airhacks.com
 */
@ApplicationPath("api")
@SwaggerUIConfiguration
public class App extends Application {

}
