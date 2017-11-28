package com.ferreusveritas.exampletrees.proxy;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.client.TreeModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;
import com.ferreusveritas.exampletrees.ExampleTrees;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
		registerModels();
	}
	
	public void registerModels() {
		TreeModelHelper.registerTreeModels(ExampleTrees.trees.ironTree);
	}
	
	public void init() {
		TreeModelHelper.regMesher(Item.getItemFromBlock(ExampleTrees.blocks.ironLog));
		
		//Register Meshers for Branches and Seeds
		DynamicTree tree = ExampleTrees.trees.ironTree;
		
		TreeModelHelper.regMesher(Item.getItemFromBlock(tree.getDynamicBranch()));//Block Branch Item Block
		TreeModelHelper.regMesher(tree.getSeed());//Register Seed Item Model
		
		//Register GrowingLeavesBlocks Meshers and Colorizers
		for(BlockDynamicLeaves leaves: TreeHelper.getLeavesMapForModId(ExampleTrees.MODID).values()) {
			Item item = Item.getItemFromBlock(leaves);
			if(item != null) {
				TreeModelHelper.regMesher(item);
				
				//Register a block color handler for the leaves block
				Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
					@Override
					public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
						Block block = state.getBlock();
						if(TreeHelper.isLeaves(block)) {
							BlockDynamicLeaves leaves = (BlockDynamicLeaves) block;
							DynamicTree tree = leaves.getTree(state);
							return tree.foliageColorMultiplier(state, worldIn, pos);
						}
						return 0x00ff00ff;//Magenta shading to indicate error
					}
				}, new Block[] {leaves});
				
				//Register a item color handler for the leaves item block
				Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
					@Override
					public int getColorFromItemstack(ItemStack stack, int tintIndex) {
						return ColorizerFoliage.getFoliageColorBasic();
					}
				}, new Item[] {Item.getItemFromBlock(leaves)});
			}
		}

	}
		
}
