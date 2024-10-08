package settingdust.moreprofiling.mixin.resourceloadevents.resourcemanager;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.moreprofiling.FindAllResourcesEvent;
import settingdust.moreprofiling.FindResourcesEvent;

import java.util.Map;
import java.util.function.Predicate;

@Mixin(NamespaceResourceManager.class)
public class NamespaceResourceManagerMixin {

    @ModifyReceiver(
        method = "findResources",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resource/ResourcePack;findResources(Lnet/minecraft/resource/ResourceType;" +
                     "Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/resource/ResourcePack$ResultConsumer;)V"
        )
    )
    private ResourcePack moreprofiling$findResources$startEvent(
        final ResourcePack instance,
        final ResourceType type,
        final String namespace,
        final String startingPath,
        final ResourcePack.ResultConsumer resultConsumer,
        @Share("event") LocalRef<FindResourcesEvent> eventRef
    ) {
        var event = new FindResourcesEvent(type.name(), instance.getName(), namespace, startingPath);
        eventRef.set(event);
        event.begin();
        return instance;
    }

    @Inject(
        method = "findResources",
        at = @At(
            value = "INVOKE",
            shift = At.Shift.BY,
            by = 2,
            target = "Lnet/minecraft/resource/ResourcePack;findResources(Lnet/minecraft/resource/ResourceType;" +
                     "Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/resource/ResourcePack$ResultConsumer;)V"
        )
    )
    private void moreprofiling$findResources$stopEvent(
        final String startingPath,
        final Predicate<Identifier> allowedPathPredicate,
        final CallbackInfoReturnable<Map<Identifier, Resource>> cir,
        @Share("event") LocalRef<FindResourcesEvent> eventRef
    ) {
        eventRef.get().commit();
    }

    @ModifyReceiver(
        method = "findAndAdd",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resource/ResourcePack;findResources(Lnet/minecraft/resource/ResourceType;" +
                     "Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/resource/ResourcePack$ResultConsumer;)V"
        )
    )
    private ResourcePack moreprofiling$findAllResources$startEvent(
        final ResourcePack instance,
        final ResourceType type,
        final String namespace,
        final String startingPath,
        final ResourcePack.ResultConsumer resultConsumer,
        @Share("event") LocalRef<FindAllResourcesEvent> eventRef
    ) {
        var event = new FindAllResourcesEvent(type.name(), instance.getName(), namespace, startingPath);
        eventRef.set(event);
        event.begin();
        return instance;
    }

    @Inject(
        method = "findAndAdd",
        at = @At(
            value = "INVOKE",
            shift = At.Shift.BY,
            by = 2,
            target = "Lnet/minecraft/resource/ResourcePack;findResources(Lnet/minecraft/resource/ResourceType;" +
                     "Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/resource/ResourcePack$ResultConsumer;)V"
        )
    )
    private void moreprofiling$findAllResources$stopEvent(
        final CallbackInfo ci,
        @Share("event") LocalRef<FindAllResourcesEvent> eventRef
    ) {
        eventRef.get().commit();
    }
}
