package settingdust.moreprofiling

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import jdk.jfr.Event
import kotlin.io.path.createDirectories
import kotlin.io.path.div
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resource.ProfiledResourceReload.Summary
import net.minecraft.util.profiler.RecordDumper
import net.minecraft.util.profiling.jfr.FlightProfiler
import net.minecraft.util.profiling.jfr.InstanceType
import settingdust.moreprofiling.mixin.dumpreloaderdebugresult.ProfiledResourceReloadSummaryAccessor

fun MutableList<Class<out Event>>.registerEvents() {
    registerProfilerEvent()
    registerResourceLoadingEvents()
}

var launchProfiling = false

fun finishLaunchProfiling() {
    if (MoreProfilingConfig.common.launchProfiling && launchProfiling) {
        val path = FlightProfiler.INSTANCE.stop()
        MoreProfiling.LOGGER.info("Launch profiling finished. Exported to $path")
        launchProfiling = false
    }
}

val RESOURCE_PROFILING_DIRECTORY =
    (RecordDumper.DEBUG_PROFILING_DIRECTORY / "resources").also { it.createDirectories() }

fun dumpResourceProfiling(summaries: List<Summary>) {
    if (!MoreProfilingConfig.common.dumpDebugReloaderResult) return
    val datatime =
        DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss.SSS", Locale.UK)
            .withZone(ZoneId.systemDefault())
            .format(Instant.now())
    val path = RESOURCE_PROFILING_DIRECTORY / datatime
    for (summary in summaries) {
        summary as ProfiledResourceReloadSummaryAccessor
        summary.applyProfile.save(path / "${summary.name}_apply.txt")
        summary.prepareProfile.save(path / "${summary.name}_prepare.txt")
    }
}

var worldLoadProfiling = false

fun startWorldLoading() {
    if (MoreProfilingConfig.common.worldLoadProfiling) {
        worldLoadProfiling = true
        FlightProfiler.INSTANCE.start(
            when (FabricLoader.getInstance().environmentType!!) {
                EnvType.CLIENT -> InstanceType.CLIENT
                EnvType.SERVER -> InstanceType.SERVER
            }
        )
    }
}

fun finishWorldLoading() {
    if (MoreProfilingConfig.common.worldLoadProfiling && worldLoadProfiling) {
        val path = FlightProfiler.INSTANCE.stop()
        MoreProfiling.LOGGER.info("World loading profiling finished. Exported to $path")
        worldLoadProfiling = false
    }
}
