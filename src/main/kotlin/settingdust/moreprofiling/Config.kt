package settingdust.moreprofiling

import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.div
import kotlin.io.path.inputStream
import kotlin.io.path.writeText
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import net.fabricmc.loader.api.FabricLoader

object MoreProfilingConfig {
    @Serializable
    data class CommonConfig(
        val launchProfiling: Boolean = false,
        val worldLoadProfiling: Boolean = false,
        val resourceLoadProfiling: Boolean = false,
        val enableDebugReloader: Boolean = false,
        val dumpDebugReloaderResult: Boolean = false,
        val suppressProfilerInfoLogging: Boolean = false,
        val redirectProfilerToJFR: Boolean = true,
        val resourceLoadEvents: ResourceLoadConfig = ResourceLoadConfig(),
        val worldGenEvents: WorldGenConfig = WorldGenConfig(),
    )

    @Serializable
    data class ResourceLoadConfig(
        val enable: Boolean = false,
        val textureManager: Boolean = true,
        val languageManager: Boolean = true,
        val soundManager: Boolean = true,
        val fontManager: Boolean = true,
        val citResewn: Boolean = true,
        val findResources: Boolean = true
    )

    @Serializable
    data class WorldGenConfig(
        val enable: Boolean = false,
        val structure: Boolean = true,
        val densityFunction: Boolean = true
    )

    var common = CommonConfig()
        private set

    private val configDir = FabricLoader.getInstance().configDir
    private val commonConfigPath = configDir / "${MoreProfiling.ID}.json"

    private val json = Json {
        encodeDefaults = true
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    init {
        try {
            configDir.createDirectories()
        } catch (_: Exception) {}

        try {
            commonConfigPath.createFile()
        } catch (_: Exception) {}

        try {
            common = json.decodeFromStream(commonConfigPath.inputStream())
        } catch (_: Exception) {}

        commonConfigPath.writeText(json.encodeToString(common))
    }
}
