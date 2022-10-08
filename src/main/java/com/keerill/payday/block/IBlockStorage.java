package com.keerill.payday.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockStorage 
{
	boolean canHacking(World worldIn, BlockPos pos, IBlockState state);
	
	void onOpened(World worldIn, BlockPos pos, IBlockState state);
}
