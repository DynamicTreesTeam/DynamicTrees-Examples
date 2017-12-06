package com.ferreusveritas.exampletrees;

import java.util.ArrayList;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;
import com.ferreusveritas.exampletrees.blocks.BlockIronLog;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModBlocks {

	public static BlockIronLog ironLog;
	public static BlockDynamicSapling ironSapling;

	public static void preInit() {
		ironLog = new BlockIronLog();
		ironSapling = new BlockDynamicSapling("ironsapling");// Create a sapling block
	}
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		final IForgeRegistry<Block> registry = event.getRegistry();
						
		registry.register(ironLog);
		registry.register(ironSapling);
		
		ArrayList<Block> treeBlocks = new ArrayList<Block>();

		for(DynamicTree tree: ModTrees.exampleTrees) {
			tree.getRegisterableBlocks(treeBlocks);
		}

		for(Block block: treeBlocks) {
			registry.register(block);
		}
		
		for(BlockDynamicLeaves leavesBlock: TreeHelper.getLeavesMapForModId(ModConstants.MODID).values()) {
			registry.register(leavesBlock);
		}
	}
	
}
