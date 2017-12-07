package com.ferreusveritas.exampletrees;

import com.ferreusveritas.dynamictrees.api.WorldGenRegistry;
import com.ferreusveritas.exampletrees.proxy.CommonProxy;
import com.ferreusveritas.exampletrees.worldgen.BiomeDensityProvider;
import com.ferreusveritas.exampletrees.worldgen.BiomeSpeciesSelector;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModConstants.MODID, version=ModConstants.VERSION, dependencies="after:dynamictrees;")

public class ExampleTrees {
	
	@Instance(ModConstants.MODID)
	public static ExampleTrees instance;
	
	@SidedProxy(clientSide = "com.ferreusveritas.exampletrees.proxy.ClientProxy", serverSide = "com.ferreusveritas.exampletrees.proxy.CommonProxy")
	public static CommonProxy proxy;

	//Run before anything else. Read your config, create blocks, items, etc.
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		ModBlocks.preInit();
		ModItems.preInit();
		ModTrees.preInit();
		
		//Register Biome Handlers
		registerBiomeHandlers();
		
		proxy.preInit();
	}
	
	//Register any biome handlers here
	public void registerBiomeHandlers() {
		
		if(WorldGenRegistry.isWorldGenEnabled()) {
			WorldGenRegistry.registerBiomeTreeSelector(new BiomeSpeciesSelector());
			WorldGenRegistry.registerBiomeDensityProvider(new BiomeDensityProvider());
		}
		
	}
	
	//Do your mod setup. Build whatever data structures you care about.
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}
	
}
