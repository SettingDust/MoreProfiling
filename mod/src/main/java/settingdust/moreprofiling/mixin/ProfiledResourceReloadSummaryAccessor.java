package settingdust.moreprofiling.mixin;

import net.minecraft.resource.ProfiledResourceReload;
import net.minecraft.util.profiler.ProfileResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ProfiledResourceReload.Summary.class)
public interface ProfiledResourceReloadSummaryAccessor {
    @Accessor
    String getName();

    @Accessor
    ProfileResult getPrepareProfile();

    @Accessor
    ProfileResult getApplyProfile();
}
