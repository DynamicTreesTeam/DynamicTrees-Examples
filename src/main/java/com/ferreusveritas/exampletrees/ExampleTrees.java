package com.ferreusveritas.exampletrees;

import com.ferreusveritas.dynamictrees.api.WorldGenRegistry;
import com.ferreusveritas.exampletrees.proxy.CommonProxy;
import com.ferreusveritas.exampletrees.trees.Trees;
import com.ferreusveritas.exampletrees.worldgen.BiomeDensityProvider;
import com.ferreusveritas.exampletrees.worldgen.BiomeTreeSelector;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = ExampleTrees.MODID, version=ExampleTrees.VERSION,dependencies="after:dynamictrees;")

public class ExampleTrees {

	public static final String MODID = "exampletrees";
	public static final String VERSION = "0.6.6";
	
	@Instance(MODID)
	public static ExampleTrees instance;
	
	@SidedProxy(clientSide = "com.ferreusveritas.exampletrees.proxy.ClientProxy", serverSide = "com.ferreusveritas.exampletrees.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static ModBlocks blocks;
	public static ModItems items;
	public static ModRecipes recipes;
	public static Trees trees;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry.
		
		blocks = new ModBlocks();
		items = new ModItems();
		recipes = new ModRecipes();
		trees = new Trees();
		
		//Register the trees.  A seed for the tree is automatically created
		//if one hasn't been set manually.
		trees.register();
		
		//Register Biome Handlers
		if(WorldGenRegistry.isWorldGenEnabled()) {
			registerBiomeHandlers();
		}
		
		proxy.preInit();
	}
	
	public void registerBiomeHandlers() {
		
		WorldGenRegistry.registerBiomeTreeSelector(new BiomeTreeSelector());
		WorldGenRegistry.registerBiomeDensityProvider(new BiomeDensityProvider());
		
		//Register any biome handlers here
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		//Do your mod setup. Build whatever data structures you care about. Register recipes.
		
		proxy.init();
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent e){
		//Handle interaction with other mods, complete your setup based on this.
	}
	
	@Mod.EventBusSubscriber(modid = ExampleTrees.MODID)
	public static class RegistrationHandler {
		
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			final IForgeRegistry<Block> registry = event.getRegistry();
			
			trees.registerBlocks(registry);
			blocks.register(registry);
		}
			
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			final IForgeRegistry<Item> registry = event.getRegistry();
			
			trees.registerItems(registry);
			items.register(registry);
		}

		@SubscribeEvent
		public static void registerRecipes(final RegistryEvent.Register<IRecipe> event) {
			final IForgeRegistry<IRecipe> registry = event.getRegistry();

			trees.registerRecipes(registry);
			recipes.register(registry);
		}

	}
}
