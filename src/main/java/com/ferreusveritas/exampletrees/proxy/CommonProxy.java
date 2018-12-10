package com.ferreusveritas.exampletrees.proxy;

import com.ferreusveritas.exampletrees.ModBlocks;
import com.ferreusveritas.exampletrees.ModItems;
import com.ferreusveritas.exampletrees.ModTrees;

public class CommonProxy {

	public void preInit() {
		ModBlocks.preInit();
		ModItems.preInit();
		ModTrees.preInit();
	}

	public void init() {}
	
	public void registerModels() {}
	
}
