package com.ferreusveritas.exampletrees;

import java.util.ArrayList;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModItems {

	public static void preInit() {
		//Mod Specific items are created here
	}
	
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		registerItemBlock(registry, ModBlocks.ironLog);
		
		ArrayList<Item> treeItems = new ArrayList<Item>();
		ArrayList<Block> treeBlocks = new ArrayList<Block>();

		for(DynamicTree tree: ModTrees.exampleTrees) {
			tree.getRegisterableBlocks(treeBlocks);
			registry.register(tree.getCommonSpecies().getSeed());
		}

		for(Item item: treeItems) {
			registry.register(item);
		}
		
		for(Block block: treeBlocks) {
			registerItemBlock(registry, block);
		}

		for(BlockDynamicLeaves leavesBlock: TreeHelper.getLeavesMapForModId(ModConstants.MODID).values()) {
			registry.register(new ItemBlock(leavesBlock).setRegistryName(leavesBlock.getRegistryName()));
		}
	}
	
	public static void registerItemBlock(final IForgeRegistry<Item> registry, Block block) {
		registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
	
}
