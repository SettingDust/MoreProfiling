package settingdust.moreprofiling.mixin.resourceloadevents.resourcemanager;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import settingdust.moreprofiling.FindAllResourcesEvent;
import settingdust.moreprofiling.FindResourcesEvent;

@Mixin(NamespaceResourceManager.class)
public class NamespaceResourceManagerMixin {

    @WrapOperation(
        method = "findResources",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resource/ResourcePack;findResources(Lnet/minecraft/resource/ResourceType;Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/resource/ResourcePack$ResultConsumer;)V"
        )
    )
    private void moreprofiling$findResources$recordEvent(
        final ResourcePack instance,
        final ResourceType type,
        final String namespace,
        final String startingPath,
        final ResourcePack.ResultConsumer resultConsumer,
        final Operation<Void> original
    ) {
        var event = new FindResourcesEvent(type.name(), instance.getName(), namespace, startingPath);
        event.begin();
        original.call(instance, type, namespace, namespace, resultConsumer);
        event.commit();
    }

    @WrapOperation(
        method = "findAndAdd",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resource/ResourcePack;findResources(Lnet/minecraft/resource/ResourceType;" +
                     "Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/resource/ResourcePack$ResultConsumer;)V"
        )
    )
    private void moreprofiling$findAllResources$recordEvent(
        final ResourcePack instance,
        final ResourceType type,
        final String namespace,
        final String startingPath,
        final ResourcePack.ResultConsumer resultConsumer,
        final Operation<Void> original
    ) {
        var event = new FindAllResourcesEvent(type.name(), instance.getName(), namespace, startingPath);
        event.begin();
        original.call(instance, type, namespace, namespace, resultConsumer);
        event.commit();
    }
}
