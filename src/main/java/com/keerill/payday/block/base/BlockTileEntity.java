package com.keerill.payday.block.base;

import com.keerill.payday.block.IBlockTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class BlockTileEntity<T extends TileEntity> extends BlockHorizontal implements IBlockTileEntity<T>
{
	public BlockTileEntity(Material material)
	{
		super(material);

		this.hasTileEntity = true;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) 
	{
		return true;
	}
	
	public abstract Class<T> getTileEntityClass();
	
	@Override
	public abstract T createTileEntity(World world, IBlockState state);

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);

		worldIn.removeTileEntity(pos);
	}
}
