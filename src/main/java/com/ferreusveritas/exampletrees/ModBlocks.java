package com.ferreusveritas.exampletrees;

import java.util.ArrayList;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.exampletrees.blocks.BlockIronLog;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModBlocks {

	public static BlockIronLog ironLog;

	public static LeavesProperties ironLeavesProperties;
	public static LeavesProperties[] exampleLeavesProperties;
	
	public static void preInit() {
		ironLog = new BlockIronLog();
		
		//Set up primitive leaves. This controls what is dropped on shearing, leaves replacement, etc.
		ironLeavesProperties = new LeavesProperties(Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER));
		
		exampleLeavesProperties = new LeavesProperties[] {
				ironLeavesProperties
		};
		
		int seq = 0;
		
		for(LeavesProperties lp : exampleLeavesProperties) {
			TreeHelper.getLeavesBlockForSequence(ModConstants.MODID, seq++, lp);
		}
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
