package settingdust.moreprofiling.mixin.client.resourceloadevents.soundmanager;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.moreprofiling.SoundManagerRegisterEvent;

import java.util.Map;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @Inject(
        method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)Lnet/minecraft/client/sound/SoundManager$SoundList;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/sound/SoundManager$SoundList;register(Lnet/minecraft/util/Identifier;Lnet/minecraft/client/sound/SoundEntry;)V"
        )
    )
    private void moreprofiling$startEvent(
        final ResourceManager resourceManager,
        final Profiler profiler,
        final CallbackInfoReturnable<SoundManager.SoundList> cir,
        @Share("event") LocalRef<SoundManagerRegisterEvent> eventRef,
        @Local String prefix,
        @Local Map.Entry<String, SoundEntry> entry
    ) {
        var event = new SoundManagerRegisterEvent(prefix + ":" + entry.getKey());
        eventRef.set(event);
        event.begin();
    }

    @Inject(
        method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)Lnet/minecraft/client/sound/SoundManager$SoundList;",
        at =
        @At(
            value = "INVOKE",
            shift = At.Shift.AFTER,
            target =
                "Lnet/minecraft/client/sound/SoundManager$SoundList;register(Lnet/minecraft/util/Identifier;Lnet/minecraft/client/sound/SoundEntry;)V"
        )
    )
    private void moreprofiling$stopEvent(
        final ResourceManager resourceManager,
        final Profiler profiler,
        final CallbackInfoReturnable<SoundManager.SoundList> cir,
        @Share("event") LocalRef<SoundManagerRegisterEvent> eventRef
    ) {
        eventRef.get().commit();
    }
}
