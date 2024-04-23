package settingdust.moreprofiling.mixin.worldgenevents.structure;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.structure.StructureStart;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.moreprofiling.StructurePlaceEvent;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @ModifyReceiver(
        method = "generateFeatures",
        at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V")
    )
    private List<StructureStart> moreprofiling$startEvent(
        final List<StructureStart> instance,
        final Consumer consumer,
        @Local Supplier<String> name,
        @Share("event") LocalRef<StructurePlaceEvent> eventRef
    ) {
        var event = new StructurePlaceEvent(name.get());
        eventRef.set(event);
        event.begin();

        return instance;
    }

    @Inject(
        method = "generateFeatures",
        at = @At(
            value = "INVOKE",
            shift = At.Shift.BY,
            by = 2,
            target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"
        )
    )
    private void moreprofiling$findAllResources$stopEvent(
        final CallbackInfo ci,
        @Share("event") LocalRef<StructurePlaceEvent> eventRef
    ) {
        eventRef.get().commit();
    }
}
