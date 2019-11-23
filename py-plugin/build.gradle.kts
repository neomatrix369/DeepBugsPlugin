import org.jetbrains.intellij.tasks.*

group = rootProject.group
version = rootProject.version

intellij {
    pluginName = "DeepBugsPython"
    version = "2019.2"
    type = "IC"
    downloadSources = true
    setPlugins("PythonCore:2019.2.192.5728.98")
}

tasks.withType<PrepareSandboxTask> {
    from("${projectDir}/models") {
        exclude("tokenToVector.json.zip")
        into("${pluginName}/models")
    }
    from(zipTree("${projectDir}/models/tokenToVector.json.zip")) {
        into("${pluginName}/models")
    }
}

tasks.withType<RunIdeTask> {
    jvmArgs("-Xmx1g", "-Didea.is.internal=true")
}

tasks.withType<PatchPluginXmlTask> {
    sinceBuild("192.5728")
    untilBuild("")
    changeNotes("Minor improvements. Usage statistics.")
}

dependencies {
    implementation(project(":common"))
}