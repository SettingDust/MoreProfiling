package settingdust.moreprofiling

import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.div
import kotlin.io.path.inputStream
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import net.fabricmc.loader.api.FabricLoader

object MoreProfilingConfig {
    @Serializable data class CommonConfig(val launch: Boolean = false)

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
    }
}
