package settingdust.moreprofiling.mixin.dumpreloaderdebugresult;

import java.util.List;
import net.minecraft.resource.ProfiledResourceReload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.moreprofiling.MoreProfilingCallbacksKt;

@Mixin(ProfiledResourceReload.class)
public class ProfiledResourceReloadMixin {
    @Inject(method = "finish", at = @At(value = "HEAD"))
    private void moreprofiling$dumpResult(
            final List<ProfiledResourceReload.Summary> summaries,
            final CallbackInfoReturnable<List<ProfiledResourceReload.Summary>> cir) {
        MoreProfilingCallbacksKt.dumpResourceProfiling(summaries);
    }
}
