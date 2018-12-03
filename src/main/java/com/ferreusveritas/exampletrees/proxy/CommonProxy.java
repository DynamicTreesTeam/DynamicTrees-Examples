package com.ferreusveritas.exampletrees.proxy;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.EnumChance;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.ISpeciesSelector;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.StaticSpeciesSelector;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase.Operation;
import com.ferreusveritas.exampletrees.ModBlocks;
import com.ferreusveritas.exampletrees.ModConstants;
import com.ferreusveritas.exampletrees.ModItems;
import com.ferreusveritas.exampletrees.ModTrees;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class CommonProxy {

	public void preInit() {
		ModBlocks.preInit();
		ModItems.preInit();
		ModTrees.preInit();
	}

	public void init() {
		
		IBiomeDataBasePopulator populator = new IBiomeDataBasePopulator() {
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
		
		WorldGenRegistry.registerBiomeDataBasePopulator(populator);
	}
	
	public void registerModels() {}
	
}
