package com.keerill.payday.block;

import com.keerill.payday.block.state.EnumStorageState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockStorage 
{
	void onStorageUpdateState(World worldIn, BlockPos pos, IBlockState state, EnumStorageState storageState);
}
