package settingdust.moreprofiling.mixin;

import java.util.List;
import java.util.Set;
import me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin;
import settingdust.moreprofiling.MoreProfilingConfig;

public class MoreProfilingMixinConfig extends RestrictiveMixinConfigPlugin {
    @Override
    public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.enabledebugreloader"))
            return MoreProfilingConfig.INSTANCE.getCommon().getEnableDebugReloader();
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.dumpreloaderdebugresult"))
            return MoreProfilingConfig.INSTANCE.getCommon().getDumpDebugReloaderResult();
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.worldloadprofiling"))
            return MoreProfilingConfig.INSTANCE.getCommon().getWorldLoadProfiling();
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.suppressprofilerinfologging"))
            return MoreProfilingConfig.INSTANCE.getCommon().getSuppressProfilerInfoLogging();
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.redirectprofilertojfr"))
            return MoreProfilingConfig.INSTANCE.getCommon().getRedirectProfilerToJFR();
        return super.shouldApplyMixin(targetClassName, mixinClassName);
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return List.of();
    }
}
