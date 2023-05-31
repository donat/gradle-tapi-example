package foo.gradle.plugin;

import org.gradle.internal.service.ServiceRegistration;
import org.gradle.internal.service.scopes.AbstractPluginServiceRegistry;
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry;
import org.gradle.tooling.provider.model.internal.BuildScopeToolingModelBuilderRegistryAction;

public class MyToolingModelServices extends AbstractPluginServiceRegistry {

    @Override
    public void registerBuildServices(ServiceRegistration registration) {
        registration.add(MyToolingModelRegistration.class);
    }

    public static class MyToolingModelRegistration implements BuildScopeToolingModelBuilderRegistryAction {
        @Override
        public void execute(ToolingModelBuilderRegistry registry) {
            registry.register(new CustomTapiModelBuilder());
        }
    }
}
