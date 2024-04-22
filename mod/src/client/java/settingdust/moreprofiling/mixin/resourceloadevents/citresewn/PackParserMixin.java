package settingdust.moreprofiling.mixin.resourceloadevents.citresewn;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.moreprofiling.CitLoadEvent;
import shcm.shsupercm.fabric.citresewn.cit.CIT;
import shcm.shsupercm.fabric.citresewn.pack.PackParser;
import shcm.shsupercm.fabric.citresewn.pack.format.PropertyGroup;

@Mixin(PackParser.class)
public class PackParserMixin {

    @Inject(method = "parseCIT", at = @At("HEAD"))
    private static void moreprofiling$startEvent(
        final PropertyGroup properties,
        final ResourceManager resourceManager,
        final CallbackInfoReturnable<CIT<?>> cir,
        @Share("event") LocalRef<CitLoadEvent> eventRef
    ) {
        var event = new CitLoadEvent(properties.identifier.toString(), properties.packName);
        eventRef.set(event);
        event.begin();
    }

    @Inject(method = "parseCIT", at = @At("RETURN"))
    private static void moreprofiling$stopEvent(
        final PropertyGroup properties,
        final ResourceManager resourceManager,
        final CallbackInfoReturnable<CIT<?>> cir,
        @Share("event") LocalRef<CitLoadEvent> eventRef
    ) {
        eventRef.get().commit();
    }
}
