package my.example.app;

import foo.gradle.plugin.CustomTapiModel;
import org.gradle.tooling.BuildAction;
import org.gradle.tooling.BuildController;
import org.gradle.tooling.model.eclipse.EclipseProject;
import org.gradle.tooling.model.gradle.BasicGradleProject;
import org.gradle.tooling.model.gradle.GradleBuild;

import java.util.Arrays;
import java.util.Collection;

public class CustomTapiModelQueryAction implements BuildAction<String> {

    @Override
    public String execute(BuildController buildController) {

        StringBuilder result = new StringBuilder();
        traverseBuilds(buildController, Arrays.asList(buildController.getBuildModel()), result, 0);
        return result.toString();
    }

    private static void traverseBuilds(BuildController buildController, Collection<? extends GradleBuild> builds, StringBuilder acc, int depth) {
        for (GradleBuild build : builds) {
            try {
                traverseBuild(buildController,  build.getRootProject());
                traverseBuilds(buildController, build.getEditableBuilds(), acc, depth + 1);
            } catch (Exception e) {
                System.out.println("Failed tooling request for " + build.getRootProject().getProjectDirectory().getAbsolutePath());
                e.printStackTrace();
            }


        }
    }

    private static void traverseBuild(BuildController buildController, BasicGradleProject project) {
        System.out.println(project.getProjectIdentifier() + " -> " + buildController.getModel(project, CustomTapiModel.class).getResult());
        for (BasicGradleProject subproject : project.getChildren()) {
            traverseBuild(buildController, subproject);
        }
    }

    private static void printInfo(StringBuilder acc, int depth, String info) {
        acc.append(depth);
        acc.append(' ');
        acc.append(info);
        acc.append("\n");
    }
}
