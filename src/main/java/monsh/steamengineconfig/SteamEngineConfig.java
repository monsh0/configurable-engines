package monsh.steamengineconfig;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.boiler.BoilerHeater;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SteamEngineConfig.MODID)
public class SteamEngineConfig {
    public static final String MODID = "steamengineconfig";
    public static final Logger LOGGER = LogManager.getLogger();

    public SteamEngineConfig(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::setup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LOGGER.info("Applying BoilerHeater overrides for passive sources");

            BoilerHeater.REGISTRY.register(Blocks.FIRE, (l, p, s) -> Config.INSTANCE.passiveSourceHeat.get().heatValue);
            BoilerHeater.REGISTRY.register(Blocks.MAGMA_BLOCK, (l, p, s) -> Config.INSTANCE.passiveSourceHeat.get().heatValue);
            BoilerHeater.REGISTRY.register(Blocks.CAMPFIRE, (l, p, s) -> Config.INSTANCE.passiveSourceHeat.get().heatValue);
            BoilerHeater.REGISTRY.register(Blocks.SOUL_CAMPFIRE, (l, p, s) -> Config.INSTANCE.passiveSourceHeat.get().heatValue);

            LOGGER.info("Heat overrides applied.");
        });
    }
}
