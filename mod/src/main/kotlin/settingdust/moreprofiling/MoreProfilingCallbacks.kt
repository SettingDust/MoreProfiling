package settingdust.moreprofiling

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.io.path.createDirectories
import kotlin.io.path.div
import net.minecraft.resource.ProfiledResourceReload.Summary
import net.minecraft.util.profiler.RecordDumper
import net.minecraft.util.profiling.jfr.FlightProfiler
import settingdust.moreprofiling.mixin.dumpreloaderdebugresult.ProfiledResourceReloadSummaryAccessor

var launchProfiling = false

fun finishGameLaunching() {
    if (MoreProfilingConfig.common.launchProfiling && launchProfiling)
        FlightProfiler.INSTANCE.stop().let {
            MoreProfiling.LOGGER.info("Launch profiling finished. Exported to $it")
            launchProfiling = false
        }
}

val RESOURCE_PROFILING_DIRECTORY =
    (RecordDumper.DEBUG_PROFILING_DIRECTORY / "resources").also { it.createDirectories() }

fun dumpResourceProfiling(summaries: List<Summary>) {
    if (!MoreProfilingConfig.common.dumpReloaderDebugResult) return
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
