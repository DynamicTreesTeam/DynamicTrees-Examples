package com.ferreusveritas.exampletrees.blocks;

import java.util.Random;

import com.ferreusveritas.dynamictrees.blocks.BlockFruit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBunnyPear extends BlockFruit {
	
	public BlockBunnyPear() {
		this("bunny");
	}
	
	public BlockBunnyPear(String name) {
		super(name);
	}
	
	static final AxisAlignedBB[] BUNNY_AABB = new AxisAlignedBB[] {
		new AxisAlignedBB( 6.5/16.0, 16.0/16.0,  6.5/16.0,  9.5/16.0, 15.0/16.0,  9.5/16.0),
		new AxisAlignedBB( 7.0/16.0, 15.8/16.0,  7.0/16.0,  9.0/16.0, 13.8/16.0,  9.0/16.0),
		new AxisAlignedBB( 6.5/16.0, 15.0/16.0,  6.5/16.0,  9.5/16.0, 11.0/16.0,  9.5/16.0),
		new AxisAlignedBB( 6.0/16.0, 15.0/16.0,  6.0/16.0, 10.0/16.0, 10.0/16.0, 10.0/16.0)
	};
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BUNNY_AABB[state.getValue(AGE)];
	}
	
	@Override
	protected boolean matureAction(World world, BlockPos pos, IBlockState state, Random rand) {
		if(!world.isRemote && rand.nextInt(4) == 3) {
			EntityRabbit bunny = new EntityRabbit(world);
			bunny.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
			bunny.setGrowingAge(-24000);
			world.spawnEntity(bunny);
			return true;
		}
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
}
