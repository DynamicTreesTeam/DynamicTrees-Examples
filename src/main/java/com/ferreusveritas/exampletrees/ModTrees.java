package com.ferreusveritas.exampletrees;

import java.util.ArrayList;

import com.ferreusveritas.dynamictrees.trees.DynamicTree;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.exampletrees.trees.TreeIron;

public class ModTrees {
	
	public static DynamicTree ironTree;
	
	public static ArrayList<DynamicTree> exampleTrees = new ArrayList<DynamicTree>();
	
	public static void preInit() {
		
		//Create the tree
		ironTree = new TreeIron();
		
		exampleTrees.add(ironTree);
		
		((Species) ironTree.getCommonSpecies()).generateSeed();
	}
	
}

