package com.ferreusveritas.exampletrees;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

	public void register(IForgeRegistry<Item> registry) {
		
		ItemBlock itemBlock = new ItemBlock(ExampleTrees.blocks.ironLog);
		itemBlock.setRegistryName(ExampleTrees.blocks.ironLog.getRegistryName());
		registry.register(itemBlock);
		
		for(BlockDynamicLeaves leavesBlock: TreeHelper.getLeavesMapForModId(ExampleTrees.MODID).values()) {
			registry.register(new ItemBlock(leavesBlock).setRegistryName(leavesBlock.getRegistryName()));
		}
	}
	
}
