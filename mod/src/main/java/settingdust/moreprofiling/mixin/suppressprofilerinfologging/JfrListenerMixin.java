package settingdust.moreprofiling.mixin.suppressprofilerinfologging;

import java.util.function.Supplier;
import net.minecraft.util.profiling.jfr.JfrListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(JfrListener.class)
public class JfrListenerMixin {
    @Redirect(
            method = "stop",
            at =
                    @At(
                            value = "INVOKE",
                            ordinal = 1,
                            target =
                                    "Lnet/minecraft/util/profiling/jfr/JfrListener;log(Ljava/util/function/Supplier;)V"))
    private void moreprofiling$avoidSpamInfo(final Supplier<String> logSupplier) {}
}
