package com.ferreusveritas.exampletrees;

import java.util.ArrayList;
import java.util.Collections;

import com.ferreusveritas.dynamictrees.api.TreeBuilder;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorHarvest;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenFruit;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
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
		
		//Method 1: Create the tree family manually the classic way(most control)
		TreeFamily ironTree = new TreeIron();//All of the heavy lifting is done in the TreeIron class
		
		//Method 2: Use the tree builder to create a tree family plus one common species(easiest)
		TreeFamily coalTree = new TreeBuilder()
				.setName(ModConstants.MODID, "coal")
				.setDynamicLeavesProperties(ModBlocks.coalLeavesProperties)
				.setPrimitiveLog(Blocks.COAL_BLOCK.getDefaultState())//Harvesting will result in coal blocks
				.setPrimitiveLeaves(acaciaLeaves)
				.build();
		
		//Make the tree drop coal when harvested for fun
		coalTree.getCommonSpecies().addDropCreator(new DropCreatorHarvest(new ResourceLocation(ModConstants.MODID, "coal"), new ItemStack(Items.COAL), 0.001f));
		
		//Method 3: Extending an existing tree family with a new species(must be an already existing tree family, wood will be of the family type)
		Species darkOak = TreeRegistry.findSpeciesSloppy("dynamictrees:darkoak");
		Species bunnyOak = new Species(new ResourceLocation(ModConstants.MODID, "bunny"), darkOak.getFamily(), darkOak.getLeavesProperties());
			bunnyOak.addGenFeature(new FeatureGenFruit(bunnyOak, Blocks.LIT_PUMPKIN.getDefaultState(), Blocks.LIT_PUMPKIN.getDefaultState()));
		
		
		//Register all of the trees
		Collections.addAll(exampleTrees, ironTree, coalTree);
		exampleTrees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));
	}
	
}
