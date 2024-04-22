package settingdust.moreprofiling.mixin.resourceloadprofiling;

import net.minecraft.resource.SimpleResourceReload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.moreprofiling.MoreProfilingCallbacksKt;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(SimpleResourceReload.class)
public class SimpleResourceReloadMixin {
    @Shadow protected CompletableFuture<List<?>> applyStageFuture;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
    private void moreprofiling$startProfiling(final CallbackInfo ci) {
        MoreProfilingCallbacksKt.startResourceLoading();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void moreprofiling$stopProfiling(final CallbackInfo ci) {
        applyStageFuture.thenAccept(it -> MoreProfilingCallbacksKt.finishResourceLoading());
    }
}
