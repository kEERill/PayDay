package com.keerill.payday.block.base;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockSlabBase extends BlockSlab 
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockSlabBase(Material materialIn)
	{
		super(materialIn);

		this.useNeighborBrightness = true;

		this.setDefaultState(
			this.blockState.getBaseState()
				.withProperty(FACING, EnumFacing.SOUTH)
				.withProperty(HALF, EnumBlockHalf.BOTTOM)
		);
	}

	@Override
	public boolean isDouble() {
		return false;
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return this.isDouble();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		IBlockState iblockstate = this.getDefaultState()
				.withProperty(FACING, EnumFacing.getHorizontal(meta & 7));

		if (!this.isDouble())
		{
			iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		}

		return iblockstate;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();

		if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
		{
			i |= 8;
		}

		return i;
	}

	@Override
	public IProperty<?> getVariantProperty() 
	{
		return HALF;
	}
	
	@Override
	public Comparable<EnumBlockHalf> getTypeForItem(ItemStack stack)
	{
		return EnumBlockHalf.BOTTOM;
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, FACING, HALF);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return this.isDouble() ? 2 : 1;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{

		return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
				.withProperty(FACING, placer.getHorizontalFacing());
	}
}
