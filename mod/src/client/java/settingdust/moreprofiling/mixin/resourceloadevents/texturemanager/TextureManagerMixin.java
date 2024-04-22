package settingdust.moreprofiling.mixin.resourceloadevents.texturemanager;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.moreprofiling.TextureManagerLoadTextureEvent;

@Mixin(TextureManager.class)
public class TextureManagerMixin {

    @Inject(method = "loadTexture", at = @At("HEAD"))
    private void moreprofiling$startEvent(
            final Identifier id,
            final AbstractTexture texture,
            final CallbackInfoReturnable<AbstractTexture> cir,
            @Share("event") LocalRef<TextureManagerLoadTextureEvent> eventRef) {
        var event = new TextureManagerLoadTextureEvent(id.toString());
        eventRef.set(event);
        event.begin();
    }

    @Inject(method = "loadTexture", at = @At("RETURN"))
    private void moreprofiling$stopEvent(
            final Identifier id,
            final AbstractTexture texture,
            final CallbackInfoReturnable<AbstractTexture> cir,
            @Share("event") LocalRef<TextureManagerLoadTextureEvent> eventRef) {
        eventRef.get().commit();
    }
}
