package com.ferreusveritas.exampletrees.worldgen;

import java.util.Random;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.treedata.ISpecies;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeSpeciesSelector;
import com.ferreusveritas.exampletrees.ModConstants;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeSpeciesSelector implements IBiomeSpeciesSelector {
	
	ISpecies ironTree;
	
	@Override
	public String getName() {
		return ModConstants.MODID + ":" + "default";
	}
	
	@Override
	public void init() {
		ironTree = TreeRegistry.findSpecies(ModConstants.MODID, "iron");
	}
	
	@Override
	public Decision getSpecies(World world, Biome biome, BlockPos pos, IBlockState dirt, Random random) {
		
		//We want this tree to generate in mesa biomes
		if(BiomeDictionary.hasType(biome, Type.MESA)) {
			return new Decision(ironTree);
		}
		
		return new Decision();//unhandled.. pass it to the next selector in the chain
	}
	
	@Override
	public int getPriority() {
		return 1;//The default selector runs at priority 0.  Since this is priority 1 this will run first.
	}

}