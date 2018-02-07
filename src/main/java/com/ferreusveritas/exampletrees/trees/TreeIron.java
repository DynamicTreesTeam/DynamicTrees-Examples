package com.ferreusveritas.exampletrees.trees;

import java.util.List;
import java.util.Random;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorSeed;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.exampletrees.ModBlocks;
import com.ferreusveritas.exampletrees.ModConstants;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class TreeIron extends DynamicTree {
	
	//Species need not be created as a nested class.  They can be created after the tree has already been constructed.
	public class TreeIronSpecies extends Species {
		
		public TreeIronSpecies(DynamicTree treeFamily) {
			super(treeFamily.getName(), treeFamily, ModBlocks.ironLeavesProperties);

			//Immensely slow-growing, stocky tree that pulls trace amounts of iron from the dirt
			setBasicGrowingParameters(0.5f, 10.0f, getUpProbability(), getLowestBranchHeight(), 0.1f);
			
			//Setup the dynamic sapling.  This could be done outside of the constructor but here is fine.
			setDynamicSapling(new BlockDynamicSapling("ironsapling").getDefaultState());
			
			//This will allow the tree to grow in the Mesa which has very little traditional dirt.
			addAcceptableSoil(Blocks.STAINED_HARDENED_CLAY, Blocks.HARDENED_CLAY, Blocks.SAND);
			
			//Let's pretend that iron trees have a hard time around water because of rust or something
			envFactor(Type.BEACH, 0.1f);
			envFactor(Type.WET, 0.25f);
			envFactor(Type.WATER, 0.25f);
			envFactor(Type.DRY, 1.05f);
			
			addDropCreator(new DropCreatorSeed(0.1f) {
				@Override
				public List<ItemStack> getHarvestDrop(World world, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int soilLife, int fortune) {
					return dropList;
				}
				
				@Override
				public List<ItemStack> getLeavesDrop(IBlockAccess access, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, int fortune) {
					return dropList;
				}
			});
		}
		
		@Override
		public boolean isBiomePerfect(Biome biome) {
			//Let's pretend that Dry Mesa biomes have a lot of iron in the clays that help these trees grow.
			return BiomeDictionary.hasType(biome, Type.MESA);
		}
		
		/*@Override
		public void addJoCodes() {
			//Disable adding of JoCodes
		}*/
		
	}
		
	public TreeIron(int seq) {
		super(new ResourceLocation(ModConstants.MODID, "iron"));
		
		//Set up primitive log. This controls what is dropped on harvest.
		setPrimitiveLog(ModBlocks.ironLog.getDefaultState());

		ModBlocks.ironLeavesProperties.setTree(this);
	}

	@Override
	public void createSpecies() {
		setCommonSpecies(new TreeIronSpecies(this));
		getCommonSpecies().generateSeed();
	}

	//Since we created a DynamicSapling in the common species we need to let it out to be registered.
	@Override
	public List<Block> getRegisterableBlocks(List<Block> blockList) {
		blockList.add(getCommonSpecies().getDynamicSapling().getBlock());
		return super.getRegisterableBlocks(blockList);
	}
	
	@Override
	public int foliageColorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos) {
		int hashmap = (32 & ((pos.getX() * 2536123) ^ (pos.getY() * 642361431 ) ^ (pos.getZ() * 86547653)));
		int r = 150 + (32 & hashmap) ;   //173
		int g = 56 + (16 & (hashmap * 763621));
		int b = 24;
		
		return r << 16 | g << 8 | b;
	}
}
