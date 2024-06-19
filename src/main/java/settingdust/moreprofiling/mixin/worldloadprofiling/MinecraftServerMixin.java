package settingdust.moreprofiling.mixin.worldloadprofiling;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.moreprofiling.MoreProfilingCallbacksKt;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void moreprofiling$startProfiling(CallbackInfo ci) {
        MoreProfilingCallbacksKt.startWorldLoading();
    }

    @Inject(method = "loadWorld", at = @At("TAIL"))
    private void moreprofiling$dumpProfiling(final CallbackInfo ci) {
        MoreProfilingCallbacksKt.finishWorldLoading();
    }
}
