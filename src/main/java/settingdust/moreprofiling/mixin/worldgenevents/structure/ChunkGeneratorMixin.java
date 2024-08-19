package settingdust.moreprofiling.mixin.worldgenevents.structure;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.moreprofiling.StructurePlaceEvent;
import settingdust.moreprofiling.TryPlaceStructureEvent;

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

    @Inject(method = "trySetStructureStart", at = @At("HEAD"))
    private void moreprofiling$trySetStructureStart$startEvent(
        final StructureSet.WeightedEntry weightedEntry,
        final StructureAccessor structureAccessor,
        final DynamicRegistryManager dynamicRegistryManager,
        final NoiseConfig noiseConfig,
        final StructureTemplateManager structureManager,
        final long seed,
        final Chunk chunk,
        final ChunkPos pos,
        final ChunkSectionPos sectionPos,
        final CallbackInfoReturnable<Boolean> cir,
        @Share("event") LocalRef<TryPlaceStructureEvent> eventRef
    ) {
        var structure = weightedEntry.structure();
        var event = new TryPlaceStructureEvent(weightedEntry.structure()
                                                            .getKey()
                                                            .map(it -> it.getValue().toString())
                                                            .orElseGet(() -> structure.value().getClass().getName()));
        eventRef.set(event);
        event.begin();
    }

    @Inject(
        method = "trySetStructureStart",
        at = @At(
            value = "RETURN"
        )
    )
    private void moreprofiling$trySetStructureStart$stopEvent(
        final StructureSet.WeightedEntry weightedEntry,
        final StructureAccessor structureAccessor,
        final DynamicRegistryManager dynamicRegistryManager,
        final NoiseConfig noiseConfig,
        final StructureTemplateManager structureManager,
        final long seed,
        final Chunk chunk,
        final ChunkPos pos,
        final ChunkSectionPos sectionPos,
        final CallbackInfoReturnable<Boolean> cir,
        @Share("event") LocalRef<TryPlaceStructureEvent> eventRef
    ) {
        eventRef.get().commit();
    }
}

