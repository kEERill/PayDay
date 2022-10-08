package com.keerill.payday.block;

import com.keerill.payday.block.base.BlockStorage;
import com.keerill.payday.tileentity.TileEntitySafe;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSafe extends BlockStorage<TileEntitySafe> implements IBlockStorageDrillable
{
	public BlockSafe()
	{
		super(Material.IRON);

		this.setBlockUnbreakable();
	}
	
	@Override
	public int getHackingSeconds() 
	{
		return 80;
	}
	
	@Override
	public Class<TileEntitySafe> getTileEntityClass() 
	{
		return TileEntitySafe.class;
	}

	@Override
	public TileEntitySafe createTileEntity(World world, IBlockState state) 
	{
		return new TileEntitySafe();
	}

	@Override
	public boolean canHackingPositionDrill(World worldIn, BlockPos pos, IBlockState state, IBlockState stateDrill) 
	{
		return worldIn.getBlockState(pos.offset(state.getValue(FACING), -1)).equals(stateDrill);
	}
}
