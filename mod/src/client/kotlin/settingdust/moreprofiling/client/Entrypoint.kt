package settingdust.moreprofiling.client

import com.terraformersmc.modmenu.api.ModMenuApi
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import settingdust.moreprofiling.finishGameLaunching

fun init() {
    ClientTickEvents.START_CLIENT_TICK.register {
        if (it.overlay == null) {
            finishGameLaunching()
        }
    }
}

object ModMenuEntrypoint : ModMenuApi {}
