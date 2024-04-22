package settingdust.moreprofiling.mixin.resourceloadprofiling.modelloader;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.moreprofiling.ModelLoadEvent;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin {
    @Inject(method = "loadModel", at = @At("HEAD"))
    private void moreprofiling$startEvent(
        final Identifier id,
        final CallbackInfo ci,
        @Share("event") LocalRef<ModelLoadEvent> eventRef
    ) {
        var event = new ModelLoadEvent(id.toString());
        eventRef.set(event);
        event.begin();
    }

    @Inject(method = "loadModel", at = @At("TAIL"))
    private void moreprofiling$stopEvent(
        final Identifier id,
        final CallbackInfo ci,
        @Share("event") LocalRef<ModelLoadEvent> eventRef
    ) {
        eventRef.get().commit();
    }
}
