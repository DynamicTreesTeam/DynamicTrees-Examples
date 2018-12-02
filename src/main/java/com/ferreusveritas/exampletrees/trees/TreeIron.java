package com.ferreusveritas.exampletrees.trees;

import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorSeed;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.exampletrees.ModBlocks;
import com.ferreusveritas.exampletrees.ModConstants;

import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class TreeIron extends TreeFamily {
	
	//Species need not be created as a nested class.  They can be created after the tree has already been constructed.
	public class TreeIronSpecies extends Species {
		
		public TreeIronSpecies(TreeFamily treeFamily) {
			super(treeFamily.getName(), treeFamily, ModBlocks.leaves.get("iron"));

			//Immensely slow-growing, stocky tree that pulls trace amounts of iron from the dirt
			setBasicGrowingParameters(0.5f, 10.0f, getUpProbability(), getLowestBranchHeight(), 0.1f);
			
			//This will allow the tree to grow in the Mesa which has very little traditional dirt.
			addAcceptableSoil(Blocks.STAINED_HARDENED_CLAY, Blocks.HARDENED_CLAY, Blocks.SAND);
			
			//Let's pretend that iron trees have a hard time around water because of rust or something
			envFactor(Type.BEACH, 0.1f);
			envFactor(Type.WET, 0.25f);
			envFactor(Type.WATER, 0.25f);
			envFactor(Type.DRY, 1.05f);
			
			addDropCreator(new DropCreatorSeed(0.1f));//Make the seed dropping 10 times more rare
		}
		
		@Override
		public boolean isBiomePerfect(Biome biome) {
			//Let's pretend that Dry Mesa biomes have a lot of iron in the clays that help these trees grow.
			return BiomeDictionary.hasType(biome, Type.MESA);
		}
		
	}
		
	public TreeIron() {
		super(new ResourceLocation(ModConstants.MODID, "iron"));
		
		//Set up primitive log. This controls what is dropped on harvest.
		setPrimitiveLog(ModBlocks.ironLog.getDefaultState());
		
		ModBlocks.leaves.get("iron").setTree(this);
	}

	@Override
	public void createSpecies() {
		setCommonSpecies(new TreeIronSpecies(this));
		getCommonSpecies().generateSeed();
	}
	
}
