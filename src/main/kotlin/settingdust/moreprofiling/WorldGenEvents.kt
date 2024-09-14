package settingdust.moreprofiling

import jdk.jfr.Category
import jdk.jfr.Event
import jdk.jfr.Label
import jdk.jfr.Name

fun MutableList<Class<out Event>>.registerWorldGenEvents() {
    add(StructurePlaceEvent::class.java)
}

@Name("settingdust.moreprofiling.StructurePlaceEvent")
@Label("Place")
@Category("Minecraft", "World Generation", "Structure")
data class StructurePlaceEvent(@JvmField @Label("Identifier") val id: String) : Event()

@Name("settingdust.moreprofiling.TryPlaceStructureEvent")
@Label("Try Place")
@Category("Minecraft", "World Generation", "Structure")
data class TryPlaceStructureEvent(@JvmField @Label("Identifier") val id: String) : Event()

@Name("settingdust.moreprofiling.DensityFunctionApplyEvent")
@Label("Apply")
@Category("Minecraft", "World Generation", "Density Function")
data class NamedDensityFunctionApplyEvent(@JvmField @Label("Identifier") var id: String) : Event()
