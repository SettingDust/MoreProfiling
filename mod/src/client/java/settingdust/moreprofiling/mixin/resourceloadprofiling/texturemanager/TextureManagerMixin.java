package settingdust.moreprofiling.mixin.resourceloadprofiling.texturemanager;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.moreprofiling.ResourceLoadingEvent;

@Mixin(TextureManager.class)
public class TextureManagerMixin {
    @Unique
    private static final String MOREPROFILING_OWNER = "TextureManager(" + TextureManager.class.getName() + ")";

    @Inject(method = "loadTexture", at = @At("HEAD"))
    private void moreprofiling$startEvent(
            final Identifier id,
            final AbstractTexture texture,
            final CallbackInfoReturnable<AbstractTexture> cir,
            @Share("event") LocalRef<ResourceLoadingEvent> eventRef) {
        var event = new ResourceLoadingEvent(id.toString(), MOREPROFILING_OWNER);
        eventRef.set(event);
        event.begin();
    }

    @Inject(method = "loadTexture", at = @At("RETURN"))
    private void moreprofiling$stopEvent(
            final Identifier id,
            final AbstractTexture texture,
            final CallbackInfoReturnable<AbstractTexture> cir,
            @Share("event") LocalRef<ResourceLoadingEvent> eventRef) {
        eventRef.get().commit();
    }
}