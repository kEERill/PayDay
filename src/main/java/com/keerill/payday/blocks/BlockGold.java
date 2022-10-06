package com.keerill.payday.blocks;

import com.keerill.payday.blocks.base.BlockHorizontal;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockGold extends BlockHorizontal
{
	public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0, 0, 0, 1D, 0.5D, 1D);
	
	public BlockGold(String name)
	{
		super(name, Material.WOOD);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}
}
