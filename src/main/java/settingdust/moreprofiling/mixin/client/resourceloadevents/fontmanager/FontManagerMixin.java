package settingdust.moreprofiling.mixin.client.resourceloadevents.fontmanager;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.font.FontManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.moreprofiling.FontManagerLoadEvent;

@Mixin(FontManager.class)
public class FontManagerMixin {
    @Inject(method = "method_51607", at = @At("HEAD"))
    private void moreprofiling$startEvent(
        final CallbackInfo ci,
        @Share("event") LocalRef<FontManagerLoadEvent> eventRef,
        @Local(argsOnly = true) Identifier id
    ) {
        var event = new FontManagerLoadEvent(id.toString());
        eventRef.set(event);
        event.begin();
    }

    @Inject(method = "method_51607", at = @At("TAIL"))
    private void moreprofiling$stopEvent(
        final CallbackInfo ci, @Share("event") LocalRef<FontManagerLoadEvent> eventRef
    ) {
        eventRef.get().commit();
    }
}
