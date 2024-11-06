package settingdust.moreprofiling.mixin.worldgenevents.densityfunction;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import settingdust.moreprofiling.NamedDensityFunctionApplyEvent;

@Mixin(DensityFunctionTypes.RegistryEntryHolder.class)
public class DensityFunctionTypes$RegistryEntryHolderMixin {
    @Shadow
    @Final
    private RegistryEntry<DensityFunction> function;

    @WrapMethod(method = "apply")
    private DensityFunction moreprofiling$startEvent(
        final DensityFunction.DensityFunctionVisitor visitor,
        final Operation<DensityFunction> original
    ) {
        if (function.getKey().isEmpty()) return original.call(visitor);
        var event = new NamedDensityFunctionApplyEvent(function.getKey().get().getValue().toString());
        event.begin();
        var result = original.call(visitor);
        event.commit();
        return result;
    }
}
