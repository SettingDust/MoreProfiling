package settingdust.moreprofiling.mixin.client.resourceloadevents.languagemanager;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import settingdust.moreprofiling.LanguageManagerLoadLanguagesEvent;

import java.util.Map;

@Mixin(LanguageManager.class)
public class LanguageManagerMixin {
    @WrapMethod(method = "method_29392")
    private static void moreprofiling$recordEvent(
        final Map map,
        final ResourcePack pack,
        final Operation<Void> original
    ) {
        var event = new LanguageManagerLoadLanguagesEvent(pack.getName());
        event.begin();
        original.call(map, pack);
        event.commit();
    }
}
