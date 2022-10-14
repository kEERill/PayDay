package com.keerill.payday.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITileEntityMechanism
{
    void onUpdate(World worldIn, BlockPos pos, IBlockState state);
}
