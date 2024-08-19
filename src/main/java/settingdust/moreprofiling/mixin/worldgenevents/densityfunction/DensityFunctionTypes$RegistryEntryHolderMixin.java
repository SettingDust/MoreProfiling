package settingdust.moreprofiling.mixin.worldgenevents.densityfunction;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.moreprofiling.DensityFunctionEvent;

@Mixin(DensityFunctionTypes.RegistryEntryHolder.class)
public class DensityFunctionTypes$RegistryEntryHolderMixin {
    @Shadow
    @Final
    private RegistryEntry<DensityFunction> function;

    @Inject(method = "apply", at = @At("HEAD"))
    private void moreprofiling$startEvent(
        final DensityFunction.DensityFunctionVisitor visitor,
        final CallbackInfoReturnable<DensityFunction> cir,
        @Share("event") LocalRef<DensityFunctionEvent> eventRef
    ) {
        if (function.getKey().isEmpty()) return;
        var event = new DensityFunctionEvent(function.getKey().get().toString());
        eventRef.set(event);
        event.begin();
    }

    @Inject(method = "apply", at = @At("RETURN"))
    private void moreprofiling$stopEvent(
        final DensityFunction.DensityFunctionVisitor visitor,
        final CallbackInfoReturnable<DensityFunction> cir,
        @Share("event") LocalRef<DensityFunctionEvent> eventRef
    ) {
        if (eventRef.get() != null) eventRef.get().commit();
    }
}
