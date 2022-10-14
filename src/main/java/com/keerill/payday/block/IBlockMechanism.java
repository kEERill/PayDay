package com.keerill.payday.block;

import com.keerill.payday.block.state.EnumMechanismState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockMechanism
{
    void onMechanismStateUpdate(World worldIn, BlockPos pos, IBlockState state, EnumMechanismState mechanismState);
}
