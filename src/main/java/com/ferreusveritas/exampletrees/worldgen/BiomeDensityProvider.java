package com.ferreusveritas.exampletrees.worldgen;

import java.util.Random;

import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDensityProvider;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.exampletrees.ModConstants;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeDensityProvider implements IBiomeDensityProvider {

	@Override
	public ResourceLocation getName() {
		return new ResourceLocation(ModConstants.MODID, "default");
	}

	@Override
	public int getPriority() {
		return 1;
	}

	@Override
	public double getDensity(Biome biome, double noiseDensity, Random random) {		
		return -1;//Unhandled.. Don't do anything.  Just pass the request to the next provider in the list
	}

	@Override
	public EnumChance chance(Biome biome, Species tree, int radius, Random random) {
				
		if(BiomeDictionary.hasType(biome, Type.MESA)) {
			return random.nextFloat() < 0.025f ? EnumChance.OK : EnumChance.UNHANDLED;
		}
		
		return EnumChance.UNHANDLED;
	}

}
