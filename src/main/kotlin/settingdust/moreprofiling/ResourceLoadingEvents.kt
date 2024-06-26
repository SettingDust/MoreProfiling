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
    add(ModelLoadEvent::class.java)
    add(CitLoadEvent::class.java)
    add(FindResourcesEvent::class.java)
    add(FindAllResourcesEvent::class.java)
}

@Name("settingdust.moreprofiling.TextureManagerLoadTextureEvent")
@Label("Load Texture")
@Category("Minecraft", "Resources", "Texture Manager")
data class TextureManagerLoadTextureEvent(@JvmField @Label("Identifier") val id: String) : Event()

@Name("settingdust.moreprofiling.LanguageManagerLoadLanguagesEvent")
@Label("Load Languages")
@Category("Minecraft", "Resources", "Language Manager")
data class LanguageManagerLoadLanguagesEvent(@JvmField @Label("Pack") val pack: String) : Event()

@Name("settingdust.moreprofiling.SoundManagerRegisterEvent")
@Label("Register")
@Category("Minecraft", "Resources", "Sound Manager")
data class SoundManagerRegisterEvent(@JvmField @Label("Identifier") val id: String) : Event()

@Name("settingdust.moreprofiling.FontManagerLoadEvent")
@Label("Load")
@Category("Minecraft", "Resources", "Font Manager")
data class FontManagerLoadEvent(@JvmField @Label("Identifier") val id: String) : Event()

@Name("settingdust.moreprofiling.ModelLoadEvent")
@Label("Load")
@Category("Minecraft", "Resources", "Model")
data class ModelLoadEvent(@JvmField @Label("Identifier") val id: String) : Event()

@Name("settingdust.moreprofiling.CitLoadEvent")
@Label("Load CIT")
@Category("Minecraft", "Resources", "Model")
data class CitLoadEvent(
    @JvmField @Label("Identifier") val id: String,
    @JvmField @Label("Pack") val pack: String
) : Event()

@Name("settingdust.moreprofiling.FindResourcesEvent")
@Label("Find Resources")
@Category("Minecraft", "Resources")
data class FindResourcesEvent(
    @JvmField @Label("Type") val type: String,
    @JvmField @Label("Pack") val pack: String,
    @JvmField @Label("Namespace") val namespace: String,
    @JvmField @Label("Prefix") val prefix: String,
) : Event()

@Name("settingdust.moreprofiling.FindAllResourcesEvent")
@Label("Find All Resources")
@Category("Minecraft", "Resources")
data class FindAllResourcesEvent(
    @JvmField @Label("Type") val type: String,
    @JvmField @Label("Pack") val pack: String,
    @JvmField @Label("Namespace") val namespace: String,
    @JvmField @Label("Prefix") val prefix: String,
) : Event()
