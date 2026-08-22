package monsh.steamengineconfig.mixin;

import com.simibubi.create.api.boiler.BoilerHeater;
import com.simibubi.create.content.fluids.tank.BoilerHeaters;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import monsh.steamengineconfig.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BoilerHeaters.class, remap = false)
public class BoilerHeatersMixin {

    @Inject(method = "blazeBurner", at = @At("HEAD"), cancellable = true)
    private static void steamengineconfig$blazeBurner(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Integer> cir) {
        BlazeBurnerBlock.HeatLevel value = state.getValue(BlazeBurnerBlock.HEAT_LEVEL);

        if (value == BlazeBurnerBlock.HeatLevel.NONE) {
            cir.setReturnValue(BoilerHeater.NO_HEAT);
        } else if (value == BlazeBurnerBlock.HeatLevel.SEETHING) {
            cir.setReturnValue(Config.INSTANCE.superheatedBurnerHeat.get());
        } else if (value.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING)) {
            cir.setReturnValue(Config.INSTANCE.litBurnerHeat.get());
        } else {
            cir.setReturnValue(Config.INSTANCE.unlitBurnerHeat.get().heatValue);
        }
        cir.cancel();
    }

    @Inject(method = "passive", at = @At("RETURN"), cancellable = true)
    private static void steamengineconfig$passive(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Integer> cir) {
        if (cir.getReturnValue() == BoilerHeater.PASSIVE_HEAT) {
            cir.setReturnValue(Config.INSTANCE.passiveSourceHeat.get().heatValue);
        }
    }
}
