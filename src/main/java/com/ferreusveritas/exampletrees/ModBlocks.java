package com.ferreusveritas.exampletrees;

import java.util.ArrayList;
import java.util.Map;

import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.exampletrees.blocks.BlockIronLog;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModBlocks {

	public static BlockIronLog ironLog;
	public static Map<String, ILeavesProperties> leaves;
	
	public static void preInit() {
		ironLog = new BlockIronLog();
		
		//For this demonstration it is vital that these are never reordered.  If a leaves properties is removed from the
		//mod then there should be a LeavesProperties.NULLPROPERTIES used as a placeholder.
		//Set up primitive leaves. This controls what is dropped on shearing, leaves replacement, etc.
		leaves = LeavesPaging.buildAll(
			"iron", "{`color`:`@exampletrees:rustyleaves`,`leaves`:`minecraft:wool color=red`}",
			"coal", "{`color`:`#D1C451`,`leaves`:`minecraft:leaves2 variant=acacia`}"
		);
		
	}
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		final IForgeRegistry<Block> registry = event.getRegistry();
						
		ArrayList<Block> treeBlocks = new ArrayList<>();
		ModTrees.exampleTrees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
		
		treeBlocks.addAll(LeavesPaging.getLeavesMapForModId(ModConstants.MODID).values());
		
		registry.register(ironLog);
		registry.registerAll(treeBlocks.toArray(new Block[0]));
	}
	
}
