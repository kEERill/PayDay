package com.keerill.payday.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockStorageDrillable extends IBlockStorage
{
	int getHackingSeconds();
	
	boolean canHackingPositionDrill(World worldIn, BlockPos pos, IBlockState state, IBlockState stateDrill);
}
