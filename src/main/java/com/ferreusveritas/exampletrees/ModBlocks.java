package com.ferreusveritas.exampletrees;

import java.util.ArrayList;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.exampletrees.blocks.BlockIronLog;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModBlocks {

	public static BlockIronLog ironLog;

	public static void preInit() {
		ironLog = new BlockIronLog();
	}
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		final IForgeRegistry<Block> registry = event.getRegistry();
						
		ArrayList<Block> treeBlocks = new ArrayList<>();
		ModTrees.exampleTrees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
		treeBlocks.addAll(TreeHelper.getLeavesMapForModId(ModConstants.MODID).values());
		
		registry.register(ironLog);
		registry.registerAll(treeBlocks.toArray(new Block[0]));
	}
	
}
