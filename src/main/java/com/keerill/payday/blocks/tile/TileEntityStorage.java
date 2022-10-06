package com.keerill.payday.blocks.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityStorage extends TileEntityContainer
{
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) 
	{
		return oldState.getBlock() != newSate.getBlock();
	}
}
