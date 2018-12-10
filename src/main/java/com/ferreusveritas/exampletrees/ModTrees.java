package com.ferreusveritas.exampletrees;

import java.util.ArrayList;
import java.util.Collections;

import com.ferreusveritas.dynamictrees.api.TreeBuilder;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry.BiomeDataBasePopulatorRegistryEvent;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.EnumChance;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.ISpeciesSelector;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.StaticSpeciesSelector;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorHarvest;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenFruit;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.SpeciesRare;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase.Operation;
import com.ferreusveritas.exampletrees.trees.TreeIron;

import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModTrees {

	//Sometimes it helps to cache a few blockstates
	public static final IBlockState acaciaLeaves = Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA);
	
	//A conveniant list of the TreeFamilies we've made so we can easily loop through them at certain loading stages
	public static ArrayList<TreeFamily> exampleTrees = new ArrayList<TreeFamily>();
	
	public static void preInit() {
		
		//Method 1: Create the tree family manually the classic way(most control)
		TreeFamily ironTree = new TreeIron();//All of the heavy lifting is done in the TreeIron class
		
		//Method 2: Use the tree builder to create a tree family plus one common species(easiest)
		TreeFamily coalTree = new TreeBuilder()
				.setName(ModConstants.MODID, "coal")
				.setDynamicLeavesProperties(ModBlocks.leaves.get("coal"))
				.setPrimitiveLog(Blocks.COAL_BLOCK.getDefaultState())//Harvesting will result in coal blocks
				.setPrimitiveLeaves(acaciaLeaves)
				.build();
		
		//Make the tree drop coal when harvested for fun
		coalTree.getCommonSpecies().addDropCreator(new DropCreatorHarvest(new ResourceLocation(ModConstants.MODID, "coal"), new ItemStack(Items.COAL), 0.001f));
		
		//Method 3: Extend an existing tree family with a new species(must be an already existing tree family, wood will be of the family type)
		Species darkOak = TreeRegistry.findSpecies("dynamictrees:darkoak");
		Species bunnyOak = new SpeciesRare(new ResourceLocation(ModConstants.MODID, "bunny"), darkOak.getFamily())
				.setBasicGrowingParameters(0.30f, 18.0f, 4, 4, 0.8f)
				.setGrowthLogicKit(TreeRegistry.findGrowthLogicKit("darkoak"))
				.setSoilLongevity(14)//Grows for a long long time
				.addGenFeature(new FeatureGenFruit(ModBlocks.bunnyFruit))
				.generateSeed();
		
		//Register all of the species in the newly created trees
		Collections.addAll(exampleTrees, ironTree, coalTree);
		exampleTrees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));
		
		//Register the individual species not part of a new tree
		Species.REGISTRY.register(bunnyOak);
	}

	@SubscribeEvent
	public static void registerDataBasePopulators(final BiomeDataBasePopulatorRegistryEvent event) {
		
		IBiomeDataBasePopulator populator = new IBiomeDataBasePopulator() {
			@Override
			public void populate(BiomeDataBase dbase) {
				Species ironTree = TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "iron"));
				ISpeciesSelector ironTreeSelector = new StaticSpeciesSelector(ironTree);
				
				Biome.REGISTRY.forEach(biome -> {
					if(BiomeDictionary.hasType(biome, Type.MESA)) {	//We want this tree to generate in mesa biomes
						dbase.setSpeciesSelector(biome, ironTreeSelector, Operation.REPLACE);
						dbase.setChanceSelector(biome, (rnd, spc, rad) -> { return rnd.nextFloat() < 0.025f ? EnumChance.OK : EnumChance.UNHANDLED; }, Operation.SPLICE_BEFORE);
						dbase.setDensitySelector(biome, (rnd, nd) -> { return -1; } , Operation.SPLICE_BEFORE);
					}
				});
			}	
		};
				
		event.register(populator);
	}
	
}
