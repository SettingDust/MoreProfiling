package settingdust.moreprofiling.mixin.client.resourceloadevents.modelloader;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import settingdust.moreprofiling.ModelLoadEvent;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin {
    @WrapMethod(method = "loadModelFromJson")
    private JsonUnbakedModel moreprofiling$recordEvent(
        final Identifier id, final Operation<JsonUnbakedModel> original
    ) {
        var event = new ModelLoadEvent(id.toString());
        event.begin();
        var result = original.call(id);
        event.commit();
        return result;
    }
}
