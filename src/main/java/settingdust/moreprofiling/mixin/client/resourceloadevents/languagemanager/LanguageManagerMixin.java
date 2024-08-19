package settingdust.moreprofiling.mixin.client.resourceloadevents.languagemanager;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import java.util.Map;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.moreprofiling.LanguageManagerLoadLanguagesEvent;

@Mixin(LanguageManager.class)
public class LanguageManagerMixin {
    @Inject(method = "method_29392", at = @At("HEAD"))
    private static void moreprofiling$startEvent(
            final Map map,
            final ResourcePack pack,
            final CallbackInfo ci,
            @Share("event") LocalRef<LanguageManagerLoadLanguagesEvent> eventRef) {
        var event = new LanguageManagerLoadLanguagesEvent(pack.getName());
        eventRef.set(event);
        event.begin();
    }

    @Inject(method = "method_29392", at = @At("RETURN"))
    private static void moreprofiling$stopEvent(
            final Map map,
            final ResourcePack pack,
            final CallbackInfo ci,
            @Share("event") LocalRef<LanguageManagerLoadLanguagesEvent> eventRef) {
        eventRef.get().commit();
    }
}
