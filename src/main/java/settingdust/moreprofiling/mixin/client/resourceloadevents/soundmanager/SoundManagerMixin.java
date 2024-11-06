package settingdust.moreprofiling.mixin.client.resourceloadevents.soundmanager;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import settingdust.moreprofiling.SoundManagerRegisterEvent;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @WrapOperation(
        method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)Lnet/minecraft/client/sound/SoundManager$SoundList;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/sound/SoundManager$SoundList;register(Lnet/minecraft/util/Identifier;Lnet/minecraft/client/sound/SoundEntry;)V"
        )
    )
    private void moreprofiling$recordEvent(
        final SoundManager.SoundList instance,
        final Identifier id,
        final SoundEntry entry,
        final Operation<Void> original,
        @Local String prefix
    ) {
        var event = new SoundManagerRegisterEvent(prefix + ":" + id);
        event.begin();
        original.call(instance, id, entry);
        event.commit();
    }
}
