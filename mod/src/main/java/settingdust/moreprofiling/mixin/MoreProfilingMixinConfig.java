package settingdust.moreprofiling.mixin;

import java.util.List;
import java.util.Set;
import me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin;
import settingdust.moreprofiling.MoreProfilingConfig;

public class MoreProfilingMixinConfig extends RestrictiveMixinConfigPlugin {
    @Override
    public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
        var commonConfig = MoreProfilingConfig.INSTANCE.getCommon();
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.enabledebugreloader"))
            return commonConfig.getEnableDebugReloader();
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.dumpreloaderdebugresult"))
            return commonConfig.getDumpDebugReloaderResult();
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.worldloadprofiling"))
            return commonConfig.getWorldLoadProfiling();
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.suppressprofilerinfologging"))
            return commonConfig.getSuppressProfilerInfoLogging();
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.redirectprofilertojfr"))
            return commonConfig.getRedirectProfilerToJFR();
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadprofiling")) {
            var resourceLoadConfig = commonConfig.getResourceLoadProfiling();
            if (!resourceLoadConfig.getEnable()) return false;
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadprofiling.texturemanager"))
                return resourceLoadConfig.getTextureManager();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadprofiling.languagemanager"))
                return resourceLoadConfig.getLanguageManager();
        }
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
