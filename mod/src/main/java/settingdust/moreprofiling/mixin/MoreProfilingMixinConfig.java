package settingdust.moreprofiling.mixin;

import me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin;
import settingdust.moreprofiling.MoreProfilingConfig;

import java.util.List;
import java.util.Set;

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
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadevents")) {
            var resourceLoadConfig = commonConfig.getResourceLoadEvents();
            if (!resourceLoadConfig.getEnable()) return false;
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadevents.texturemanager"))
                return resourceLoadConfig.getTextureManager();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadevents.languagemanager"))
                return resourceLoadConfig.getLanguageManager();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadevents.soundmanager"))
                return resourceLoadConfig.getSoundManager();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadevents.fontmanager"))
                return resourceLoadConfig.getFontManager();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadevents.citresewn"))
                return resourceLoadConfig.getCitResewn();

        }
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadprofiling"))
            return commonConfig.getResourceLoadProfiling();
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
