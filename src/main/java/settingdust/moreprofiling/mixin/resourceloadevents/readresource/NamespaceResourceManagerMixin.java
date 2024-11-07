package settingdust.moreprofiling.mixin.resourceloadevents.readresource;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import settingdust.moreprofiling.ResourceManagerReadResourceEvent;

import java.io.InputStream;

@Mixin(NamespaceResourceManager.class)
public class NamespaceResourceManagerMixin {
    @ModifyReturnValue(method = "wrapForDebug", at = @At("RETURN"))
    private static InputSupplier<InputStream> moreprofiling$wrapStreamForProfiling(
        final InputSupplier<InputStream> original,
        Identifier id,
        ResourcePack pack
    ) {
        return ResourceManagerReadResourceEvent.ProfilingStream.Companion.wrap(original, id, pack.getName());
    }
}
