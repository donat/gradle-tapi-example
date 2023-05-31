package my.example.app;

import org.gradle.tooling.BuildAction;
import org.gradle.tooling.BuildController;
import org.gradle.tooling.model.gradle.BasicGradleProject;
import org.gradle.tooling.model.gradle.GradleBuild;

import java.util.*;

public class PerProjectModelQueryAction<T> implements BuildAction<Map<String, T>> {

    private final Class<T> customTapiModel;

    public PerProjectModelQueryAction(Class<T> customTapiModel) {
        this.customTapiModel = customTapiModel;
    }

    @Override
    public Map<String, T> execute(BuildController buildController) {
        Map<String, T> result = new LinkedHashMap<>();
        traverseBuilds(buildController, Arrays.asList(buildController.getBuildModel()), result);
        return result;
    }

    private void traverseBuilds(BuildController buildController, Collection<? extends GradleBuild> builds, Map<String, T> acc) {
        for (GradleBuild build : builds) {
            traverseProjects(buildController, Collections.singletonList(build.getRootProject()), acc);
            traverseBuilds(buildController, build.getEditableBuilds(), acc);
        }
    }

    private void traverseProjects(BuildController buildController, Collection<? extends BasicGradleProject> projects, Map<String, T> acc) {
        for (BasicGradleProject project : projects) {
            T model;
            try {
                model = buildController.getModel(project, customTapiModel);
            } catch (Exception e) {
                e.printStackTrace();
                model = null;
            }
            acc.put(project.getPath(), model);
            traverseProjects(buildController, project.getChildren(), acc);
        }
    }
}
