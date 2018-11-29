package com.ferreusveritas.exampletrees;

import java.util.ArrayList;
import java.util.Collections;

import com.ferreusveritas.dynamictrees.api.TreeBuilder;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorHarvest;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.exampletrees.trees.TreeIron;

import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ModTrees {

	//Sometimes it helps to cache a few blockstates
	public static final IBlockState acaciaLeaves = Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA);
	public static ArrayList<TreeFamily> exampleTrees = new ArrayList<TreeFamily>();
	
	public static void preInit() {
		
		//Method 1: Create the tree manually
		TreeFamily ironTree = new TreeIron();//All of the heavy lifting is done in the TreeIron class
		
		//Method 2: Use the tree builder
		TreeFamily coalTree = new TreeBuilder()
				.setName(ModConstants.MODID, "coal")
				.setDynamicLeavesProperties(ModBlocks.coalLeavesProperties)
				.setPrimitiveLog(Blocks.COAL_BLOCK.getDefaultState())//Harvesting will result in coal blocks
				.setPrimitiveLeaves(acaciaLeaves)
				.build();
		
		//Make the tree drop coal when harvested for fun
		coalTree.getCommonSpecies().addDropCreator(new DropCreatorHarvest(new ResourceLocation(ModConstants.MODID, "coal"), new ItemStack(Items.COAL), 0.001f));
		
		//Register all of the trees
		Collections.addAll(exampleTrees, ironTree, coalTree);
		exampleTrees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));
	}
	
}
