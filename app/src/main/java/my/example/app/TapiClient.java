package my.example.app;

import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;

public class TapiClient {

    public static void main(String[] args) {
        ProjectConnection connection = GradleConnector.newConnector()
                .useGradleVersion("8.0")
                .forProjectDirectory(new File("/tmp/foo"))
                .connect();
        try {
            String model = connection.action(new CustomTapiModelQueryAction())
                    .setStandardOutput(System.out)
                    .setStandardError(System.err)
                    .withArguments("--init-script", "/Users/donat/Development/gradle-tapi-with-client/gradle-tapi-example/app/src/main/resources/init/init.gradle")
                    .run();

            System.out.println("---\n" + model + "---");
        } finally {
            connection.close();
        }
    }
}
