package com.keerill.payday.blocks.base;

import com.keerill.payday.PayDayMinecraft;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public abstract class BlockSlabBase extends BlockSlab 
{

	public BlockSlabBase(String name, Material materialIn) 
	{
		super(materialIn);
		
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		
		this.setCreativeTab(PayDayMinecraft.PayDayTab);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, EnumBlockHalf.BOTTOM));
		
		this.useNeighborBrightness = true;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		if(!this.isDouble()) {
			return this.getDefaultState().withProperty(HALF, EnumBlockHalf.values()[meta % EnumBlockHalf.values().length]);
		}
		
		return this.getDefaultState();
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		if(!this.isDouble()) {
			return 0;
		}
		
		return ((EnumBlockHalf)state.getValue(HALF)).ordinal() + 1;
	}
	
	@Override
	public String getUnlocalizedName(int meta) 
	{
		return this.getUnlocalizedName();
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
		return new BlockStateContainer(this, new IProperty[] { HALF });
	}
}
