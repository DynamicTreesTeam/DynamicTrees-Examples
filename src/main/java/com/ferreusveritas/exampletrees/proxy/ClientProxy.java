package com.ferreusveritas.exampletrees.proxy;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;
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
		//registerColorHandlers();
	}
	
	@Override
	public void registerModels() {
		
		ModelHelper.regModel(ModBlocks.ironLog);
		
		//TREE PARTS
		
		//Register Meshers for Branches and Seeds
		for(DynamicTree tree: ModTrees.exampleTrees) {
			ModelHelper.regModel(tree.getDynamicBranch());//Register Branch itemBlock
			ModelHelper.regModel(tree.getCommonSpecies().getSeed());//Register Seed Item Models
			ModelHelper.regModel(tree);//Register custom state mapper for branch
		}
		
		//Register GrowingLeavesBlocks Meshers and Colorizers
		for(BlockDynamicLeaves leaves: TreeHelper.getLeavesMapForModId(ModConstants.MODID).values()) {
			Item item = Item.getItemFromBlock(leaves);
			ModelHelper.regModel(item);
		}

		//Register the file loader for Branch models
		//ModelLoaderRegistry.registerLoader(new ModelLoaderBranch());
	}
	
	public void registerColorHandlers() {
		
		final int magenta = 0x00FF00FF;//for errors.. because magenta sucks.
		
		//TREE PARTS
		
		//Register GrowingLeavesBlocks Colorizers
		for(BlockDynamicLeaves leaves: TreeHelper.getLeavesMapForModId(ModConstants.MODID).values()) {
			
			ModelHelper.regColorHandler(leaves, new IBlockColor() {
				@Override
				public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
					Block block = state.getBlock();
					if(TreeHelper.isLeaves(block)) {
						return ((BlockDynamicLeaves) block).getTree(state).foliageColorMultiplier(state, worldIn, pos);
					}
					return magenta;
				}
			});
				
			ModelHelper.regColorHandler(Item.getItemFromBlock(leaves), new IItemColor() {
				@Override
				public int getColorFromItemstack(ItemStack stack, int tintIndex) {
					return ColorizerFoliage.getFoliageColorBasic();
				}
			});
		}

		//Register Sapling Colorizer
		ModelHelper.regColorHandler(ModBlocks.ironSapling, new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
				return ModBlocks.ironSapling.getSpecies(state).getTree().foliageColorMultiplier(state, world, pos);
			}
		});
		
	}
	
}
