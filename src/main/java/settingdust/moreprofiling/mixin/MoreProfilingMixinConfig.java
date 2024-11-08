package settingdust.moreprofiling.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import settingdust.moreprofiling.MoreProfilingConfig;

import java.util.List;
import java.util.Set;

public class MoreProfilingMixinConfig implements IMixinConfigPlugin {
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
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.client.resourceloadevents.texturemanager"))
                return resourceLoadConfig.getTextureManager();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.client.resourceloadevents.languagemanager"))
                return resourceLoadConfig.getLanguageManager();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.client.resourceloadevents.soundmanager"))
                return resourceLoadConfig.getSoundManager();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.client.resourceloadevents.fontmanager"))
                return resourceLoadConfig.getFontManager();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.client.resourceloadevents.citresewn"))
                return resourceLoadConfig.getCitResewn();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadevents.findresources"))
                return resourceLoadConfig.getFindResources();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadevents.readresource"))
                return resourceLoadConfig.getReadResource();
        }
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.worldgenevents")) {
            var worldGenConfig = commonConfig.getWorldGenEvents();
            if (!worldGenConfig.getEnable()) return false;
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.worldgenevents.structure"))
                return worldGenConfig.getStructure();
            if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.worldgenevents.densityfunction"))
                return worldGenConfig.getDensityFunction();
        }
        if (mixinClassName.startsWith("settingdust.moreprofiling.mixin.resourceloadprofiling"))
            return commonConfig.getResourceLoadProfiling();
        return true;
    }

    @Override
    public void onLoad(final String mixinPackage) {

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

    @Override
    public void preApply(
        final String targetClassName,
        final ClassNode targetClass,
        final String mixinClassName,
        final IMixinInfo mixinInfo
    ) {

    }

    @Override
    public void postApply(
        final String targetClassName,
        final ClassNode targetClass,
        final String mixinClassName,
        final IMixinInfo mixinInfo
    ) {

    }
}
