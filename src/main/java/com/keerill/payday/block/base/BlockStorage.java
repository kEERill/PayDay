package com.keerill.payday.block.base;

import com.keerill.payday.tileentity.base.TileEntityStorage;
import com.keerill.payday.block.IBlockStorage;

import com.keerill.payday.block.state.EnumStorageState;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockStorage<T extends TileEntityStorage> extends BlockTileEntity<T> implements IBlockStorage
{
	public static final PropertyEnum<EnumStorageState> STATE = PropertyEnum.create("state", EnumStorageState.class);
	
	public BlockStorage(Material material)
	{
		super(material);
		
		this.setDefaultState(
			this.getDefaultState()
				.withProperty(STATE, EnumStorageState.OPENED)
		);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) 
	{
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer)
				.withProperty(STATE, EnumStorageState.OPENED);
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, FACING, STATE);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return super.getMetaFromState(state) | (state.getValue(STATE).getMetadata() << 3);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return super.getStateFromMeta(meta).withProperty(STATE, EnumStorageState.byMetadata(meta >> 3));
	}

	@Override
	public void onStorageUpdateState(World worldIn, BlockPos pos, IBlockState state, EnumStorageState storageState)
	{
		if (state.getBlock() instanceof BlockStorage) {
			worldIn.setBlockState(pos, state.withProperty(STATE, storageState));
		}
	}
}
