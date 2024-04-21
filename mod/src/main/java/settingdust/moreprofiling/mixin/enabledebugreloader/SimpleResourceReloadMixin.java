package settingdust.moreprofiling.mixin.enabledebugreloader;

import net.minecraft.resource.SimpleResourceReload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SimpleResourceReload.class)
public class SimpleResourceReloadMixin {
    @ModifyVariable(method = "start", at = @At("HEAD"), argsOnly = true)
    private static boolean moreprofiling$profiled(boolean profiled) {
        return true;
    }
}
