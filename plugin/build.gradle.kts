plugins {
    `java-gradle-plugin`
    `maven-publish`
}

repositories {
    mavenCentral()
}

gradlePlugin {
    // Define the plugin
    plugins {
        val greeting by plugins.creating  {
            id = "custom.tapi.model"
            implementationClass = "org.gradle.plugin.CustomTapiModelPlugin"
        }
    }
}
group = "org.gradlex"
version = "0.0.1"
publishing {
    repositories {
        maven {
            name = "Mylocal"
            // change to point to your repo, e.g. http://my.org/repo
            url = layout.buildDirectory.dir("repo").get().asFile.toURI()
        }
    }
}