package settingdust.moreprofiling

import jdk.jfr.Category
import jdk.jfr.Event
import jdk.jfr.Label
import jdk.jfr.Name

fun MutableList<Class<out Event>>.registerWorldGenEvents() {
    add(StructurePlaceEvent::class.java)
}

@Name("settingdust.moreprofiling.StructurePlaceEvent")
@Label("Place Structure")
@Category("Minecraft", "World Generation")
data class StructurePlaceEvent(@JvmField @Label("Identifier") val id: String) : Event()

@Name("settingdust.moreprofiling.DensityFunctionApplyEvent")
@Label("Apply")
@Category("Minecraft", "World Generation", "Density Function")
data class DensityFunctionApplyEvent(@JvmField @Label("Identifier") var id: String) : Event()
