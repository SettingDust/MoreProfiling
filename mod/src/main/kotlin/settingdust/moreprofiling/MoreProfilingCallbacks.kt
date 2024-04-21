package settingdust.moreprofiling

import net.minecraft.util.profiling.jfr.FlightProfiler

var launchProfiling = false

fun finishGameLaunching() {
    if (MoreProfilingConfig.common.launch && launchProfiling)
        FlightProfiler.INSTANCE.stop().let {
            MoreProfiling.LOGGER.info("Launch profiling finished. Exported to $it")
            launchProfiling = false
        }
}
