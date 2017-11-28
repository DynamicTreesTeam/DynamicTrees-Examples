package com.ferreusveritas.exampletrees.trees;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.trees.DynamicTree;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class Trees {
	
	//Create the tree
	public DynamicTree ironTree = new TreeIron();
	
	//Register the trees.  A seed for the tree is automatically created
	//if one hasn't been set manually.
	public void register() {
		TreeRegistry.registerTree(ironTree);
	}
	
	public void registerBlocks(IForgeRegistry<Block> registry) {
		ironTree.registerBlocks(registry);
	}
	
	public void registerItems(IForgeRegistry<Item> registry) {
		ironTree.registerItems(registry);
	}
	
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		ironTree.registerRecipes(registry);
	}
	
}

