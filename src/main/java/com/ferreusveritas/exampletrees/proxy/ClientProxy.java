package com.ferreusveritas.exampletrees.proxy;

import java.util.Optional;

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

import net.minecraft.util.ResourceLocation;

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
		
		//Register Seed Item Models for Species not created in a TreeFamily class
		ModelHelper.regModel(TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "bunny")).getSeed());
	}
	
	public void registerColorHandlers() {
		
		final int magenta = 0x00FF00FF;//for errors.. because magenta sucks.
		
		//TREE PARTS
		
		//Register GrowingLeavesBlocks Colorizers
		for(BlockDynamicLeaves leaves: LeavesPaging.getLeavesMapForModId(ModConstants.MODID).values()) {
			ModelHelper.regColorHandler(leaves, (state, worldIn, pos, tintIndex) -> {
				return Optional.of(state.getBlock()).filter(TreeHelper::isLeaves)
					.map(b -> ((BlockDynamicLeaves) b).getProperties(state).foliageColorMultiplier(state, worldIn, pos) ).orElse(magenta);
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
