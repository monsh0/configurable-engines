package monsh.steamengineconfig.mixin;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlockEntity;
import monsh.steamengineconfig.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PoweredShaftBlockEntity.class, remap = false)
public abstract class PoweredShaftBlockEntityMixin extends GeneratingKineticBlockEntity {

    public PoweredShaftBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "getGeneratedSpeed", at = @At("RETURN"), cancellable = true)
    private void steamengineconfig$getGeneratedSpeed(CallbackInfoReturnable<Float> cir) {
        float baseSpeed = cir.getReturnValueF();
        float newSpeed = baseSpeed * Config.INSTANCE.engineRPM.get() / 64f;
        cir.setReturnValue(newSpeed);
    }

    @ModifyVariable(method = "calculateAddedStressCapacity", at = @At("STORE"), ordinal = 0)
    private float steamengineconfig$calculateAddedStressCapacity(float original) {
        return original * 64f / Config.INSTANCE.engineRPM.get() * Config.INSTANCE.levelsPerEngine.get().floatValue();
    }
}
