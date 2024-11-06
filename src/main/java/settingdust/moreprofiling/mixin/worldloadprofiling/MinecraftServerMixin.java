package settingdust.moreprofiling.mixin.worldloadprofiling;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.moreprofiling.MoreProfilingCallbacksKt;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @WrapMethod(method = "loadWorld")
    private void moreprofiling$startProfiling(final Operation<Void> original) {
        MoreProfilingCallbacksKt.startWorldLoading();
        original.call();
        MoreProfilingCallbacksKt.finishWorldLoading();
    }
}
