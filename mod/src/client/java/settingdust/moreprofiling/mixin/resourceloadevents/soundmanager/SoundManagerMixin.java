package settingdust.moreprofiling.mixin.resourceloadevents.soundmanager;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.moreprofiling.SoundManagerRegisterEvent;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @ModifyExpressionValue(
            method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)"
                    + "Lnet/minecraft/client/sound/SoundManager$SoundList;",
            at = @At(value = "NEW", ordinal = 1, target = "net/minecraft/util/Identifier"))
    private Identifier moreprofiling$startEvent(
            final Identifier original, @Share("event") LocalRef<SoundManagerRegisterEvent> eventRef) {
        var event = new SoundManagerRegisterEvent(original.toString());
        eventRef.set(event);
        event.begin();
        return original;
    }

    @Inject(
            method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)"
                    + "Lnet/minecraft/client/sound/SoundManager$SoundList;",
            at =
                    @At(
                            value = "INVOKE",
                            shift = At.Shift.AFTER,
                            target =
                                    "Lnet/minecraft/client/sound/SoundManager$SoundList;register(Lnet/minecraft/util/Identifier;"
                                            + "Lnet/minecraft/client/sound/SoundEntry;)V"))
    private void moreprofiling$stopEvent(
            final ResourceManager resourceManager,
            final Profiler profiler,
            final CallbackInfoReturnable<SoundManager.SoundList> cir,
            @Share("event") LocalRef<SoundManagerRegisterEvent> eventRef) {
        eventRef.get().commit();
    }
}
