package com.ferreusveritas.exampletrees;

import java.util.ArrayList;
import java.util.Collections;

import com.ferreusveritas.dynamictrees.api.TreeBuilder;
import com.ferreusveritas.dynamictrees.api.treedata.IFoliageColorHandler;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorHarvest;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.exampletrees.trees.TreeIron;

import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class ModTrees {

	//Sometimes it helps to cache a few blockstates
	public static final IBlockState acaciaLeaves = Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA);
	
	public static ArrayList<DynamicTree> exampleTrees = new ArrayList<DynamicTree>();
	
	//If you're going to let the leaves be handled for you then I recommend using an enum to serialize your block sequences
	public enum DemoTree {
		IRONTREE,
		COALTREE;
		
		public int getSeq() {
			return ordinal();
		}
	}
	
	public static void preInit() {
		
		//Method 1: Create the tree manually
		DynamicTree ironTree = new TreeIron(DemoTree.IRONTREE.getSeq());//All of the heavy lifting is done in the TreeIron class
		
		//Method 2: Use the tree builder
		DynamicTree coalTree = new TreeBuilder()
				.setName(ModConstants.MODID, "Coal")
				.setDynamicLeavesSequence(DemoTree.COALTREE.getSeq())
				.setPrimitiveLog(Blocks.COAL_BLOCK.getDefaultState(), new ItemStack(Blocks.COAL_BLOCK, 1))//Harvesting will result in coal blocks
				.setPrimitiveLeaves(acaciaLeaves, new ItemStack(acaciaLeaves.getBlock(), 1, acaciaLeaves.getValue(BlockNewLeaf.VARIANT).getMetadata() & 3))
				.setColorHandler(new IFoliageColorHandler() {
					@Override
					public int foliageColorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos) {
						return 0x00D1C451;
					}
				})
				.build();
		
		//Make the tree drop coal when harvested for fun
		coalTree.getCommonSpecies().addDropCreator(new DropCreatorHarvest(new ResourceLocation(ModConstants.MODID, "coal"), new ItemStack(Items.COAL), 0.001f));
		
		//Register all of the trees
		Collections.addAll(exampleTrees, ironTree, coalTree);
		exampleTrees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));
	}
	
}
