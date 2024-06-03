apply(
    from = "https://github.com/SettingDust/FabricKotlinTemplate/raw/main/common.settings.gradle.kts"
)

val minecraft = settings.extra["minecraft"]
val kotlin = settings.extra["kotlin"]

dependencyResolutionManagement.versionCatalogs.named("catalog") {
    library("kotlin-jdk8", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8").version("$kotlin")
    val kotlinxSerialization = "1.6.3"
    library("kotlinx-serialization-core", "org.jetbrains.kotlinx", "kotlinx-serialization-core")
        .version(kotlinxSerialization)
    library("kotlinx-serialization-json", "org.jetbrains.kotlinx", "kotlinx-serialization-json")
        .version(kotlinxSerialization)
    library("kotlinx-coroutines", "org.jetbrains.kotlinx", "kotlinx-coroutines-core")
        .version("1.8.0")
    library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").version("$kotlin")

    // https://modrinth.com/mod/preloading-tricks/versions
    library("preloading-tricks", "maven.modrinth", "preloading-tricks").version("1.0.6")

    // https://modrinth.com/mod/modernfix
    library("modernfix", "maven.modrinth", "modernfix").version("5.16.1+mc$minecraft")

    // https://modrinth.com/mod/worldgen-profiling
    library("worldgen-profiling", "maven.modrinth", "worldgen-profiling").version("1.3.1")

    // https://modrinth.com/mod/cit-resewn
    library("cit-resewn", "maven.modrinth", "cit-resewn").version("1.1.5+$minecraft")

    // https://modrinth.com/mod/cit-resewn
    library("polytone", "maven.modrinth", "polytone").version("1.20-1.17.8")

    // https://github.com/Fallen-Breath/conditional-mixin
    library("mixin-conditional", "me.fallenbreath", "conditional-mixin-fabric").version("0.6.2")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
    // https://github.com/DanySK/gradle-pre-commit-git-hooks
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.0.7"
}

gitHooks {
    preCommit {
        from {
            // git diff --cached --name-only --diff-filter=ACMR | while read -r a; do
            // echo ${'$'}(readlink -f ${"$"}a); ./gradlew spotlessApply -q
            // -PspotlessIdeHook="${'$'}(readlink -f ${"$"}a)" </dev/null; done
            """
            export JAVA_HOME="${System.getProperty("java.home")}"
            ./gradlew spotlessApply spotlessCheck
            """
                .trimIndent()
        }
    }
    commitMsg { conventionalCommits { defaultTypes() } }
    hook("post-commit") {
        from {
            """
            files="${'$'}(git show --pretty= --name-only | tr '\n' ' ')"
            git add ${'$'}files
            git -c core.hooksPath= commit --amend -C HEAD
            """
                .trimIndent()
        }
    }
    createHooks(true)
}

val name: String by settings

rootProject.name = name

include("mod")

include("quilt")

include("forge")
