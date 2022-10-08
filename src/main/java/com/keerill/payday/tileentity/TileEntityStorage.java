package com.keerill.payday.tileentity;

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
