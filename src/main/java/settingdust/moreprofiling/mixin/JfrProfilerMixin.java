package settingdust.moreprofiling.mixin;

import java.util.ArrayList;
import java.util.List;
import jdk.jfr.Event;
import net.minecraft.util.profiling.jfr.JfrProfiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.moreprofiling.MoreProfilingCallbacksKt;

@Mixin(value = JfrProfiler.class, priority = 1001)
public class JfrProfilerMixin {
    @Mutable
    @Shadow
    @Final
    private static List<Class<? extends Event>> EVENTS;

    @Inject(
            method = "<clinit>",
            at =
                    @At(
                            value = "FIELD",
                            shift = At.Shift.BY,
                            by = 2,
                            target = "Lnet/minecraft/util/profiling/jfr/JfrProfiler;EVENTS:Ljava/util/List;"))
    private static void moreprofiling$registerEvents(final CallbackInfo ci) {
        EVENTS = new ArrayList<>(EVENTS);
        MoreProfilingCallbacksKt.registerEvents(EVENTS);
    }

    @Inject(method = "<clinit>", at = @At(value = "NEW", target = "net/minecraft/util/profiling/jfr/JfrProfiler"))
    private static void moreprofiling$freezeEvents(final CallbackInfo ci) {
        EVENTS = List.copyOf(EVENTS);
    }
}
