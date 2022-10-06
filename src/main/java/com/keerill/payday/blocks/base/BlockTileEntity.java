package com.keerill.payday.blocks.base;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class BlockTileEntity<T extends TileEntity> extends BlockHorizontal
{
	public BlockTileEntity(String name, Material material) 
	{
		super(name, material);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) 
	{
		return true;
	}
	
	public abstract Class<T> getTileEntityClass();
	
	@Override
	public abstract T createTileEntity(World world, IBlockState state);
}
