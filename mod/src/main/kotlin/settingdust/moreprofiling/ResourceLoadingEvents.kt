package settingdust.moreprofiling

import jdk.jfr.Category
import jdk.jfr.Event
import jdk.jfr.Label
import jdk.jfr.Name

fun MutableList<Class<out Event>>.registerResourceLoadingEvents() {
    add(TextureManagerLoadTextureEvent::class.java)
    add(LanguageManagerLoadLanguagesEvent::class.java)
    add(SoundManagerRegisterEvent::class.java)
    add(FontManagerLoadEvent::class.java)
}

@Name("settingdust.moreprofiling.TextureManagerLoadTextureEvent")
@Label("Load Texture")
@Category("Minecraft", "Resources", "Texture Manager")
data class TextureManagerLoadTextureEvent(@JvmField @Label("Identifier") var id: String) : Event()

@Name("settingdust.moreprofiling.LanguageManagerLoadLanguagesEvent")
@Label("Load Languages")
@Category("Minecraft", "Resources", "Language Manager")
data class LanguageManagerLoadLanguagesEvent(@JvmField @Label("Pack") var pack: String) : Event()

@Name("settingdust.moreprofiling.SoundManagerRegisterEvent")
@Label("Register")
@Category("Minecraft", "Resources", "Sound Manager")
data class SoundManagerRegisterEvent(@JvmField @Label("Identifier") var id: String) : Event()

@Name("settingdust.moreprofiling.FontManagerLoadEvent")
@Label("Load")
@Category("Minecraft", "Resources", "Font Manager")
data class FontManagerLoadEvent(@JvmField @Label("Identifier") var id: String) : Event()
