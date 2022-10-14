package com.keerill.payday.block;

import com.keerill.payday.block.base.BlockStorage;
import com.keerill.payday.tileentity.TileEntitySafe;
import com.keerill.payday.tileentity.TileEntitySafeCreative;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class BlockSafe extends BlockStorage<TileEntitySafe>
{
	public BlockSafe()
	{
		super(Material.IRON);

		this.setBlockUnbreakable();
	}
	
	@Override
	public Class<TileEntitySafe> getTileEntityClass()
	{
		return TileEntitySafe.class;
	}

	@Override
	public TileEntitySafeCreative createTileEntity(World world, IBlockState state)
	{
		return new TileEntitySafeCreative();
	}
}
