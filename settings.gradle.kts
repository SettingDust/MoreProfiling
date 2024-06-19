val minecraft = "1.21"
extra["minecraft"] = minecraft

apply("https://github.com/SettingDust/MinecraftGradleScripts/raw/main/common.gradle.kts")

apply("https://github.com/SettingDust/MinecraftGradleScripts/raw/main/kotlin.gradle.kts")

apply("https://github.com/SettingDust/MinecraftGradleScripts/raw/main/fabric.gradle.kts")

apply("https://github.com/SettingDust/MinecraftGradleScripts/raw/main/modmenu.gradle.kts")

dependencyResolutionManagement.versionCatalogs.named("catalog") {

    // https://modrinth.com/mod/preloading-tricks/versions
    library("preloading-tricks", "maven.modrinth", "preloading-tricks").version("1.1.0")

    // https://modrinth.com/mod/modernfix
    library("modernfix", "maven.modrinth", "modernfix").version("5.18.3+mc$minecraft")

    // https://modrinth.com/mod/worldgen-devtools
    library("worldgen-devtools", "maven.modrinth", "worldgen-devtools").version("1.1.0+$minecraft")

    // https://modrinth.com/mod/cit-resewn
    library("cit-resewn", "maven.modrinth", "cit-resewn").version("1.1.5+1.20.4")

    // https://modrinth.com/mod/polytone
    library("polytone", "maven.modrinth", "polytone").version("1.20-1.17.8")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

val name: String by settings

rootProject.name = name
