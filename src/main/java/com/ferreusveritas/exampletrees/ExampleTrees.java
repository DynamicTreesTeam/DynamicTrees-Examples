package com.ferreusveritas.exampletrees;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.EnumChance;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.ISpeciesSelector;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.StaticSpeciesSelector;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase.Operation;
import com.ferreusveritas.exampletrees.proxy.CommonProxy;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
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
		
		proxy.preInit();
	}
	
	//Do your mod setup. Build whatever data structures you care about.
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		WorldGenRegistry.registerBiomeDataBasePopulator(populator);
		
		proxy.init();
	}

	public IBiomeDataBasePopulator populator = new IBiomeDataBasePopulator() {
		@Override
		public void populate(BiomeDataBase dbase) {
			Species ironTree = TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "iron"));
			ISpeciesSelector ironTreeSelector = new StaticSpeciesSelector(ironTree);
			
			Biome.REGISTRY.forEach(biome -> {
				if(BiomeDictionary.hasType(biome, Type.MESA)) {	//We want this tree to generate in mesa biomes
					dbase.setSpeciesSelector(biome, ironTreeSelector, Operation.REPLACE);
					dbase.setChanceSelector(biome, (rnd, spc, rad) -> { return rnd.nextFloat() < 0.025f ? EnumChance.OK : EnumChance.UNHANDLED; }, Operation.SPLICE_BEFORE);
					dbase.setDensitySelector(biome, (rnd, nd) -> { return -1; } , Operation.SPLICE_BEFORE);
				}
			});
		}	
	};
	
}
