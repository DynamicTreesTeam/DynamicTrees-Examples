package com.ferreusveritas.exampletrees;

import java.util.ArrayList;

import com.ferreusveritas.dynamictrees.api.TreeHelper;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModItems {

	public static Item itemIronLog;
	
	public static void preInit() {
		//Mod Specific items are created here		
		itemIronLog = new ItemBlock(ModBlocks.ironLog).setRegistryName(ModBlocks.ironLog.getRegistryName());
	}
	
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		ArrayList<Item> treeItems = new ArrayList<>();
		ModTrees.exampleTrees.forEach(tree -> tree.getRegisterableItems(treeItems));
		TreeHelper.getLeavesMapForModId(ModConstants.MODID).forEach((key, block) -> treeItems.add(makeItemBlock(block)));

		registry.register(itemIronLog);
		registry.registerAll(treeItems.toArray(new Item[0]));
	}
	
	public static Item makeItemBlock(Block block) {
		return new ItemBlock(block).setRegistryName(block.getRegistryName());
	}
	
}
