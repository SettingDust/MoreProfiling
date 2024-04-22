package settingdust.moreprofiling

import jdk.jfr.Category
import jdk.jfr.Description
import jdk.jfr.Event
import jdk.jfr.Label
import jdk.jfr.Name

@Name("settingdust.moreprofiling.ResourceLoadingEvent")
@Label("Resource Loading")
@Category("Minecraft")
@Description("Info for resource loading in minecraft")
data class ResourceLoadingEvent(
    @Label("Identifier") @JvmField var id: String? = null,
    @Label("Owner") @JvmField var owner: String? = null
) : Event()
