package com.ferreusveritas.exampletrees;

import java.util.ArrayList;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.exampletrees.blocks.BlockIronLog;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModBlocks {

	public static BlockIronLog ironLog;

	public static LeavesProperties ironLeavesProperties;
	public static LeavesProperties coalLeavesProperties;
	public static LeavesProperties[] exampleLeavesProperties;
	
	public static void preInit() {
		ironLog = new BlockIronLog();
		
		//Set up primitive leaves. This controls what is dropped on shearing, leaves replacement, etc.
		ironLeavesProperties = new LeavesProperties(Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER)) {
			@Override
			@SideOnly(Side.CLIENT)
			public int foliageColorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos) {
				int hashmap = (32 & ((pos.getX() * 2536123) ^ (pos.getY() * 642361431 ) ^ (pos.getZ() * 86547653)));
				int r = 150 + (32 & hashmap) ;   //173
				int g = 56 + (16 & (hashmap * 763621));
				int b = 24;
				
				return r << 16 | g << 8 | b;
			}
		};
		
		coalLeavesProperties = new LeavesProperties(Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA)) {
			@Override
			@SideOnly(Side.CLIENT)
			public int foliageColorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos) {
				return 0x00D1C451;
			}
		};
		
		//For this demonstration it is vital that these are never reordered.  If a leaves properties is removed from the
		//mod then there should be a LeavesProperties.NULLPROPERTIES used as a placeholder.
		exampleLeavesProperties = new LeavesProperties[] {
				ironLeavesProperties,
				coalLeavesProperties
		};
		
		for(LeavesProperties lp : exampleLeavesProperties) {
			LeavesPaging.getNextLeavesBlock(ModConstants.MODID, lp);
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
