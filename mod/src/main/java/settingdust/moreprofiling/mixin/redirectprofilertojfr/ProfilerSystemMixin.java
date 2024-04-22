package settingdust.moreprofiling.mixin.redirectprofilertojfr;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.profiler.ProfileResult;
import net.minecraft.util.profiler.ProfilerSystem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.moreprofiling.ProfilerEvent;

@Mixin(ProfilerSystem.class)
public class ProfilerSystemMixin {
    @Shadow
    private String fullPath;

    @Unique
    private final Map<String, ProfilerEvent> moreprofiling$currentEvents = Maps.newHashMap();

    @Inject(
            method = "push(Ljava/lang/String;)V",
            at = @At(value = "INVOKE", remap = false, target = "Lit/unimi/dsi/fastutil/longs/LongList;add(J)Z"))
    private void moreprofiling$startJFR(final String location, final CallbackInfo ci) {
        var profilerEvent = new ProfilerEvent();
        moreprofiling$currentEvents.put(fullPath, profilerEvent);
        profilerEvent.begin();
    }

    @Inject(
            method = "pop",
            at =
                    @At(
                            value = "FIELD",
                            opcode = Opcodes.PUTFIELD,
                            target = "Lnet/minecraft/util/profiler/ProfilerSystem;fullPath:Ljava/lang/String;"))
    private void moreprofiling$stopJFR(final CallbackInfo ci) {
        var profilerEvent = moreprofiling$currentEvents.get(fullPath);
        profilerEvent.path = fullPath.replace(ProfileResult.SPLITTER_CHAR, '/');
        profilerEvent.commit();
        moreprofiling$currentEvents.remove(fullPath);
    }
}
