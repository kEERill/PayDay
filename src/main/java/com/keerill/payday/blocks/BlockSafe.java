package com.keerill.payday.blocks;

import com.keerill.payday.blocks.base.BlockStorage;
import com.keerill.payday.blocks.tile.TileEntitySafe;
import com.keerill.payday.interfaces.IBlockStorageDrillable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSafe extends BlockStorage<TileEntitySafe> implements IBlockStorageDrillable
{
	public BlockSafe(String name) 
	{
		super(name, Material.IRON);
		
		this.setBlockUnbreakable();
	}
	
	@Override
	public int getHackingSeconds() 
	{
		return 10;
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if (playerIn.isSneaking()) 
		{
			return false;
		}
		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		if(tileEntity instanceof TileEntitySafe)
		{
			TileEntitySafe tileEntitySafe = (TileEntitySafe) tileEntity;
			
			if(state.getValue(STATE) == BlockStorage.EnumState.OPENED)
			{
				ItemStack item = tileEntitySafe.getItem(0);
				
				if (item != ItemStack.EMPTY) 
				{
					playerIn.inventory.addItemStackToInventory(item);
				}
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean canHackingPositionDrill(World worldIn, BlockPos pos, IBlockState state, IBlockState stateDrill) 
	{
		return worldIn.getBlockState(pos.offset(state.getValue(FACING), -1)).equals(stateDrill);
	}
}
