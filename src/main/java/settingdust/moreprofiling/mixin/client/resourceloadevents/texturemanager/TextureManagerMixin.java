package settingdust.moreprofiling.mixin.client.resourceloadevents.texturemanager;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import settingdust.moreprofiling.TextureManagerLoadTextureEvent;

@Mixin(TextureManager.class)
public class TextureManagerMixin {

    @WrapMethod(method = "loadTexture")
    private AbstractTexture moreprofiling$recordEvent(
        final Identifier id, final AbstractTexture texture, final Operation<AbstractTexture> original
    ) {
        var event = new TextureManagerLoadTextureEvent(id.toString());
        event.begin();
        var result = original.call(id, texture);
        event.commit();
        return result;
    }
}
