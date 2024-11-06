package settingdust.moreprofiling.v1_21.mixin.client.resourceloadevents.fontmanager;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.font.FontManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import settingdust.moreprofiling.FontManagerLoadEvent;

import java.util.List;
import java.util.Set;

@Mixin(FontManager.class)
public class FontManagerMixin {
    @WrapMethod(method = "method_51607")
    private void moreprofiling$startEvent(
        final Set set,
        final Identifier id,
        final List fonts,
        final Operation<Void> original
    ) {
        var event = new FontManagerLoadEvent(id.toString());
        event.begin();
        original.call(set, id, fonts);
        event.commit();
    }
}
