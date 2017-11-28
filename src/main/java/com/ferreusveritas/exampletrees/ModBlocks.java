package com.ferreusveritas.exampletrees;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.exampletrees.blocks.BlockIronLog;

import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

	public Block ironLog = new BlockIronLog();

	//Create a sapling block
	public BlockDynamicSapling ironSapling = new BlockDynamicSapling("ironsapling");

	public void register(IForgeRegistry<Block> registry) {
		registry.register(ironLog);
		registry.register(ironSapling);
		
		for(BlockDynamicLeaves leavesBlock: TreeHelper.getLeavesMapForModId(ExampleTrees.MODID).values()) {
			registry.register(leavesBlock);
		}
	}
}
