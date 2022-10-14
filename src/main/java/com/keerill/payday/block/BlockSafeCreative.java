package com.keerill.payday.block;

import com.keerill.payday.block.base.BlockStorage;
import com.keerill.payday.block.state.EnumStorageState;
import com.keerill.payday.tileentity.TileEntitySafeCreative;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSafeCreative extends BlockStorage<TileEntitySafeCreative>
{
	public BlockSafeCreative()
	{
		super(Material.IRON);

		this.setBlockUnbreakable();

		this.setDefaultState(
			this.getDefaultState()
				.withProperty(STATE, EnumStorageState.CLOSED)
		);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer)
			.withProperty(STATE, EnumStorageState.CLOSED);
	}
	
	@Override
	public Class<TileEntitySafeCreative> getTileEntityClass()
	{
		return TileEntitySafeCreative.class;
	}

	@Override
	public TileEntitySafeCreative createTileEntity(World world, IBlockState state)
	{
		return new TileEntitySafeCreative();
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) 
	{
		super.onBlockAdded(worldIn, pos, state);

		if (worldIn.isRemote) {
			return;
		}

		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if(tileEntity instanceof TileEntitySafeCreative) {
			TileEntitySafeCreative targetStorage = (TileEntitySafeCreative) tileEntity;
			targetStorage.setupItem(worldIn);

			worldIn.notifyBlockUpdate(pos, state, state, 3);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
									EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (playerIn.isSneaking()) {
			return false;
		}

		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if(tileEntity instanceof TileEntitySafeCreative) {
			TileEntitySafeCreative tileEntityStorage = (TileEntitySafeCreative) tileEntity;

			if(state.getValue(STATE) == EnumStorageState.OPENED) {
				if (!worldIn.isRemote) {
					tileEntityStorage.getItemForPlayer(playerIn);
					worldIn.notifyBlockUpdate(pos, state, state, 2);
				}
			}
		}

		return true;
	}
}
