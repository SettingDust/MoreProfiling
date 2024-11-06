package settingdust.moreprofiling.mixin.worldgenevents.structure;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import settingdust.moreprofiling.StructurePlaceEvent;
import settingdust.moreprofiling.TryPlaceStructureEvent;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @WrapOperation(
        method = "generateFeatures",
        at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V")
    )
    private void moreprofiling$startEvent(
        final List instance, final Consumer consumer, final Operation<Void> original, @Local Supplier<String> name
    ) {
        var event = new StructurePlaceEvent(name.get());
        event.begin();
        original.call(instance, consumer);
        event.commit();
    }

    @WrapMethod(method = "trySetStructureStart")
    private boolean moreprofiling$trySetStructureStart$startEvent(
        final StructureSet.WeightedEntry weightedEntry,
        final StructureAccessor structureAccessor,
        final DynamicRegistryManager dynamicRegistryManager,
        final NoiseConfig noiseConfig,
        final StructureTemplateManager structureManager,
        final long seed,
        final Chunk chunk,
        final ChunkPos pos,
        final ChunkSectionPos sectionPos,
        final Operation<Boolean> original
    ) {
        var structure = weightedEntry.structure();
        var event = new TryPlaceStructureEvent(weightedEntry.structure()
                                                            .getKey()
                                                            .map(it -> it.getValue().toString())
                                                            .orElseGet(() -> structure.value().getClass().getName()));
        event.begin();
        var result = original.call(
            weightedEntry,
            structureAccessor,
            dynamicRegistryManager,
            noiseConfig,
            structureManager,
            seed,
            chunk,
            pos,
            sectionPos
        );
        event.commit();
        return result;
    }
}

