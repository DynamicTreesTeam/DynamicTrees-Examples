package com.ferreusveritas.exampletrees.proxy;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.client.BlockColorMultipliers;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.exampletrees.ModBlocks;
import com.ferreusveritas.exampletrees.ModConstants;
import com.ferreusveritas.exampletrees.ModTrees;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		super.preInit();
		registerJsonColorMultipliers();
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
		
		//Register Seed Item Models for Species not created in a TreeFamily class
		ModelHelper.regModel(TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "bunny")).getSeed());
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
		}
		
	}
	
	public void registerJsonColorMultipliers() {
		
		//Register programmable custom block color providers for LeavesPropertiesJson
		BlockColorMultipliers.register(new ResourceLocation(ModConstants.MODID, "rusty"), 
			(state, worldIn,  pos, tintIndex) -> {
				int hashmap = (32 & ((pos.getX() * 2536123) ^ (pos.getY() * 642361431 ) ^ (pos.getZ() * 86547653)));
				int r = 150 + (32 & hashmap) ;   //173
				int g = 56 + (16 & (hashmap * 763621));
				int b = 24;
				
				return r << 16 | g << 8 | b;
			}
		);
	}
	
}
