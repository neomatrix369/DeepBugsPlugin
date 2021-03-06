import org.jetbrains.intellij.tasks.*
import tanvd.kosogor.defaults.configureIdea

group = rootProject.group
version = rootProject.version

intellij {
    pluginName = "DeepBugs for Python"
    version = rootProject.intellij.version
    type = "IC"
    downloadSources = true
    setPlugins("PythonCore:201.6668.13", "java")
}

configureIdea {
    exclude += file("src/test/testData")
}

tasks.withType<PrepareSandboxTask> {
    from("${projectDir}/src/main/models") {
        into("${pluginName}/models")
    }
}

tasks.withType<RunIdeTask> {
    jvmArgs("-Xmx1g", "-Didea.is.internal=true")
}

tasks.withType<Test> {
    useJUnit()

    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<PatchPluginXmlTask> {
    sinceBuild("201")
    untilBuild("")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":keras-runner"))
}
