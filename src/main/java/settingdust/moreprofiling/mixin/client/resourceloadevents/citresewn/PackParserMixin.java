package settingdust.moreprofiling.mixin.client.resourceloadevents.citresewn;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import settingdust.moreprofiling.CitLoadEvent;
import shcm.shsupercm.fabric.citresewn.cit.CIT;
import shcm.shsupercm.fabric.citresewn.pack.PackParser;
import shcm.shsupercm.fabric.citresewn.pack.format.PropertyGroup;

@Mixin(PackParser.class)
public class PackParserMixin {

    @WrapMethod(method = "parseCIT")
    private static CIT<?> moreprofiling$recordEvent(
        final PropertyGroup properties,
        final ResourceManager resourceManager,
        final Operation<CIT<?>> original
    ) {
        var event = new CitLoadEvent(properties.identifier.toString(), properties.packName);
        event.begin();
        var result = original.call(properties, resourceManager);
        event.commit();
        return result;
    }
}
