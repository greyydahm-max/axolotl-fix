package me.fix.mixin;

import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Axolotl.class)
public class AxolotlMixin {

    @Inject(method = "saveToBucketTag", at = @At("HEAD"), cancellable = true)
    private void fixSaveToBucketTag(ItemStack stack, CallbackInfo ci) {
        try {
            Axolotl self = (Axolotl)(Object)this;
            net.minecraft.nbt.CompoundTag tag = stack.getOrDefault(
                net.minecraft.core.component.DataComponents.BUCKET_ENTITY_DATA,
                net.minecraft.world.item.component.CustomData.EMPTY
            ).copyTag();
            tag.putInt("Variant", self.getVariant().ordinal());
            tag.putInt("Age", self.getAge());
            tag.putBoolean("FromBucket", true);
            stack.set(
                net.minecraft.core.component.DataComponents.BUCKET_ENTITY_DATA,
                net.minecraft.world.item.component.CustomData.of(tag)
            );
            ci.cancel();
        } catch (Exception e) {
            ci.cancel();
        }
    }
}
