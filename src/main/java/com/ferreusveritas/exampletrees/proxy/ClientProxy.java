package com.ferreusveritas.exampletrees.proxy;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.exampletrees.ModBlocks;
import com.ferreusveritas.exampletrees.ModConstants;
import com.ferreusveritas.exampletrees.ModTrees;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		super.preInit();
	}
	
	@Override
	public void init() {
		super.init();
		registerColorHandlers();
	}
	
	@Override
	public void registerModels() {
		
		ModelHelper.regModel(ModBlocks.ironLog);
		
		//TREE PARTS
		
		//Register Meshers for Branches and Seeds
		for(TreeFamily tree: ModTrees.exampleTrees) {
			ModelHelper.regModel(tree.getDynamicBranch());//Register Branch itemBlock
			ModelHelper.regModel(tree.getCommonSpecies().getSeed());//Register Seed Item Models
			ModelHelper.regModel(tree);//Register custom state mapper for branch
		}
		
		//Register GrowingLeavesBlocks Meshers and Colorizers
		for(BlockDynamicLeaves leaves: LeavesPaging.getLeavesMapForModId(ModConstants.MODID).values()) {
			Item item = Item.getItemFromBlock(leaves);
			ModelHelper.regModel(item);
		}

	}
	
	public void registerColorHandlers() {
		
		final int magenta = 0x00FF00FF;//for errors.. because magenta sucks.
		
		//TREE PARTS
		
		//Register GrowingLeavesBlocks Colorizers
		for(BlockDynamicLeaves leaves: LeavesPaging.getLeavesMapForModId(ModConstants.MODID).values()) {
			
			ModelHelper.regColorHandler(leaves, new IBlockColor() {
				@Override
				public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
					Block block = state.getBlock();
					if(TreeHelper.isLeaves(block)) {
						return ((BlockDynamicLeaves) block).getProperties(state).foliageColorMultiplier(state, worldIn, pos);
					}
					return magenta;
				}
			});
				
			ModelHelper.regColorHandler(Item.getItemFromBlock(leaves), new IItemColor() {
				@Override
				public int colorMultiplier(ItemStack stack, int tintIndex) {
					return ColorizerFoliage.getFoliageColorBasic();
				}
			});
		}

		//Register Sapling Colorizers
		for(TreeFamily tree: ModTrees.exampleTrees) {
			ModelHelper.regDynamicSaplingColorHandler((BlockDynamicSapling) tree.getCommonSpecies().getDynamicSapling().getBlock());
		}

	}
	
}
