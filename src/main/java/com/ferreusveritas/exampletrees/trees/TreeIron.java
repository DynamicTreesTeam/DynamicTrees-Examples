package com.ferreusveritas.exampletrees.trees;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.treedata.ISpecies;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.exampletrees.ModBlocks;
import com.ferreusveritas.exampletrees.ModConstants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class TreeIron extends DynamicTree {

	public class TreeIronSpecies extends Species {

		public TreeIronSpecies(DynamicTree treeFamily) {
			super(treeFamily.getName(), treeFamily);

			//Immensely slow-growing, stocky tree that pulls trace amounts of iron from the dirt
			setBasicGrowingParameters(0.5f, 10.0f, getUpProbability(), getLowestBranchHeight(), 0.1f);
			
			//Set the dynamic sapling
			setDynamicSapling(ModBlocks.ironSapling.getDefaultState());
			
			//Let's pretend that iron trees have a hard time around water because of rust or something
			envFactor(Type.BEACH, 0.1f);
			envFactor(Type.WET, 0.25f);
			envFactor(Type.WATER, 0.25f);
			envFactor(Type.DRY, 1.05f);
			
		}
		
		@Override
		public boolean isBiomePerfect(Biome biome) {
			//Let's pretend that Dry Mesa biomes have a lot of iron in the clays that help these trees grow.
			return BiomeDictionary.hasType(biome, Type.MESA);
		}
	
		@Override
		public boolean isAcceptableSoil(IBlockAccess blockAccess, BlockPos pos, IBlockState soilBlockState) {
			Block block = soilBlockState.getBlock();
			
			//Make the tree able to grow on sand and hardened clay so it can spawn in Mesa biomes
			if(block == Blocks.STAINED_HARDENED_CLAY || block == Blocks.HARDENED_CLAY || block == Blocks.SAND) {
				return true;
			}
			//Also make it able to grow on the traditional surfaces
			return super.isAcceptableSoil(blockAccess, pos, soilBlockState);
		}
		
		/*@Override
		public void addJoCodes() {
			//Disable adding of JoCodes
		}*/
		
	}
	
	ISpecies species;
	
	public TreeIron() {
		super(new ResourceLocation(ModConstants.MODID, "iron"), 0);

		//Set up primitive log. This controls what is dropped on harvest, block hardness, flammability, etc.
		IBlockState primLog = ModBlocks.ironLog.getDefaultState();
		setPrimitiveLog(primLog, new ItemStack(primLog.getBlock()));
		
		//Set up primitive leaves. This controls what is dropped on shearing, branch support, leaves replacement, etc.
		IBlockState primLeaves = Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
		setPrimitiveLeaves(primLeaves, new ItemStack(primLeaves.getBlock(), 1, primLeaves.getValue(BlockColored.COLOR).getMetadata()));
	}

	@Override
	public ISpecies getCommonSpecies() {
		return species;
	}

	@Override
	public void createSpecies() {
		species = TreeRegistry.registerSpecies(new TreeIronSpecies(this));
	}
	
}
