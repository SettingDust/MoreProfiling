package settingdust.moreprofiling

import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.SharedConstants
import net.minecraft.server.dedicated.DedicatedServer
import net.minecraft.util.profiling.jfr.FlightProfiler
import net.minecraft.util.profiling.jfr.InstanceType
import org.apache.logging.log4j.LogManager

object MoreProfiling {
    val ID = "more-profiling"

    val LOGGER = LogManager.getLogger()!!
}

fun preLaunch() {
    SharedConstants.createGameVersion()
    if (MoreProfilingConfig.common.launchProfiling) {
        launchProfiling = true
        FlightProfiler.INSTANCE.start(
            when (FabricLoader.getInstance().environmentType!!) {
                EnvType.CLIENT -> InstanceType.CLIENT
                EnvType.SERVER -> InstanceType.SERVER
            })
    }
}

fun init() {
    ServerLifecycleEvents.SERVER_STARTED.register { finishLaunchProfiling() }

    ClientTickEvents.START_CLIENT_TICK.register {
        if (it.overlay == null) {
            finishLaunchProfiling()
        }
    }

    ServerLifecycleEvents.SERVER_STOPPED.register {
        if (it is DedicatedServer && FlightProfiler.INSTANCE.isProfiling)
            FlightProfiler.INSTANCE.stop()
    }

    ClientLifecycleEvents.CLIENT_STOPPING.register {
        if (FlightProfiler.INSTANCE.isProfiling) FlightProfiler.INSTANCE.stop()
    }
}
