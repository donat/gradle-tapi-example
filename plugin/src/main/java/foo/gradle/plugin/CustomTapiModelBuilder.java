package foo.gradle.plugin;

import foo.gradle.plugin.internal.DefaultCustomTapiModel;
import org.gradle.api.Project;
import org.gradle.tooling.provider.model.ParameterizedToolingModelBuilder;
import org.gradle.tooling.provider.model.ToolingModelBuilder;

import java.util.stream.Collectors;

public class CustomTapiModelBuilder implements ToolingModelBuilder {

    @Override
    public boolean canBuild(String modelName) {
        return CustomTapiModel.class.getName().equals(modelName);
    }

    @Override
    public Object buildAll(String modelName, Project project) {
        return new DefaultCustomTapiModel(build(project));
    }

    private String build(Project project) {
        try {
            return project.getConfigurations()
                    .getByName("compileClasspath")
                    .getIncoming()
                    .artifactView(viewConfiguration -> viewConfiguration.lenient(true))
                    .getArtifacts()
                    .getArtifacts().stream()
                    .map(it -> it.getId().getComponentIdentifier())
                    .collect(Collectors.toList())
                    .toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
