package com.keerill.payday.blocks.base;

import com.keerill.payday.blocks.BlockSafe;
import com.keerill.payday.blocks.tile.TileEntityStorage;
import com.keerill.payday.interfaces.IBlockStorage;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockStorage<T extends TileEntityStorage> extends BlockTileEntity<T> implements IBlockStorage
{
	public static final PropertyEnum<BlockStorage.EnumState> STATE = PropertyEnum.<BlockStorage.EnumState>create("state", BlockStorage.EnumState.class);
	
	public BlockStorage(String name, Material material) 
	{
		super(name, material);
		
		this.setDefaultState(
			this.getDefaultState()
				.withProperty(STATE, BlockStorage.EnumState.CLOSED)
		);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) 
	{
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer)
				.withProperty(STATE, BlockStorage.EnumState.CLOSED);
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, STATE });
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return super.getMetaFromState(state) | (state.getValue(STATE).getMetadata() << 3);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return super.getStateFromMeta(meta).withProperty(STATE, BlockStorage.EnumState.byMetadata(meta >> 3));
	}
	
	public void onOpened(World worldIn, BlockPos pos, IBlockState state) 
	{
		if (state.getBlock() instanceof BlockSafe)
		{
			worldIn.setBlockState(pos, state.withProperty(STATE, BlockStorage.EnumState.OPENED));
		}
	}

	public boolean canHacking(World worldIn, BlockPos pos, IBlockState state) 
	{
		return state.getBlock() instanceof BlockSafe && state.getValue(STATE) == BlockStorage.EnumState.CLOSED;
	}
	
	public static enum EnumState implements IStringSerializable
	{
		CLOSED(0), 
		OPENED(1);
		
		private static final BlockStorage.EnumState[] META_LOOKUP = new BlockStorage.EnumState[values().length];

		private int meta;
		
		EnumState(int meta) 
		{
			this.meta = meta;
		}

		@Override
		public String getName() 
		{
			return this.toString().toLowerCase();
		}
		
		public int getMetadata()
		{
			return this.meta;
		}
		
		
		public static BlockStorage.EnumState byMetadata(int index)
		{
			if (index < 0 || index > META_LOOKUP.length)
			{
				index = 0;
			}
			
			return META_LOOKUP[index];
		}
		
       static
	   {
	        for (BlockStorage.EnumState state : values())
	        {
	            META_LOOKUP[state.getMetadata()] = state;
	        }
	   }
	}
}
