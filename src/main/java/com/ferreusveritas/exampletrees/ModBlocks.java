package com.ferreusveritas.exampletrees;

import java.util.ArrayList;
import java.util.Map;

import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.BlockFruit;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.exampletrees.blocks.BlockBunnyPear;
import com.ferreusveritas.exampletrees.blocks.BlockIronLog;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModBlocks {

	public static BlockIronLog ironLog;
	public static BlockFruit bunnyFruit;

	/**
	 * This value will contain a mapping for name to {@link ILeavesProperties} that
	 * will be created by one of the {@link LeavesPaging#build(com.google.gson.JsonObject)}
	 * variations.
	 */
	public static Map<String, ILeavesProperties> leaves;
	
	public static void preInit() {
		ironLog = new BlockIronLog();
		bunnyFruit = new BlockBunnyPear();

		//Set up primitive leaves. This controls what is dropped on shearing, leaves replacement, etc.
		leaves = LeavesPaging.build(new ResourceLocation(ModConstants.MODID, "leaves/common.json"));
		
		/*
		 * For this demonstration it is vital that the leaves properties are never reordered.  If a
		 * leaves properties is removed from the mod then there should be a LeavesProperties.NULLPROPERTIES
		 * used as a placeholder.  In the json file the object label's name should be preceded with a "-"
		 * to show that it's not to be used.
		 */
	}
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		final IForgeRegistry<Block> registry = event.getRegistry();
		
		ArrayList<Block> treeBlocks = new ArrayList<>();
		ModTrees.exampleTrees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
		
		treeBlocks.addAll(LeavesPaging.getLeavesMapForModId(ModConstants.MODID).values());
		
		registry.register(ironLog);
		registry.register(bunnyFruit);
		registry.registerAll(treeBlocks.toArray(new Block[0]));
	}
	
}
