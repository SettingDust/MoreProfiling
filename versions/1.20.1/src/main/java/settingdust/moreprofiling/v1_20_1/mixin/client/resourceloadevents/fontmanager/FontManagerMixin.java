package settingdust.moreprofiling.v1_20_1.mixin.client.resourceloadevents.fontmanager;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.font.FontManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import settingdust.moreprofiling.FontManagerLoadEvent;

import java.util.List;

@Mixin(FontManager.class)
public class FontManagerMixin {
    @WrapMethod(method = "method_51607")
    private void moreprofiling$startEvent(
        final Identifier fontId,
        final List providers,
        final Operation<Void> original
    ) {
        var event = new FontManagerLoadEvent(fontId.toString());
        event.begin();
        original.call(fontId, providers);
        event.commit();
    }
}
