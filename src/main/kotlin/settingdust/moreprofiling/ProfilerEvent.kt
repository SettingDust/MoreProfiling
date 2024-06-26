package settingdust.moreprofiling

import jdk.jfr.Category
import jdk.jfr.Description
import jdk.jfr.Event
import jdk.jfr.Label
import jdk.jfr.Name

fun MutableList<Class<out Event>>.registerProfilerEvent() {
    add(ProfilerEvent::class.java)
}

@Name("settingdust.moreprofiling.ProfilerEvent")
@Label("Profiler")
@Category("Minecraft")
@Description("Redirect Minecraft built in profiler")
data class ProfilerEvent(@Label("Path") @JvmField var path: String? = null) : Event()
