package me.fix.mixin;

import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Axolotl.class)
public class AxolotlMixin {

    @Inject(method = "saveToBucketTag", at = @At("HEAD"), cancellable = true)
    private void fixSaveToBucketTag(CompoundTag tag, CallbackInfo ci) {
        try {
            Axolotl self = (Axolotl)(Object)this;
            // Save variant manually without touching brain memory
            tag.putInt("Variant", self.getVariant().ordinal());
            tag.putInt("Age", self.getAge());
            tag.putBoolean("FromBucket", true);
            ci.cancel();
        } catch (Exception e) {
            // If anything goes wrong, cancel to avoid crash
            ci.cancel();
        }
    }
}
