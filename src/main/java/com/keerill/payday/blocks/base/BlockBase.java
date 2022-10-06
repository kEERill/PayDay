package com.keerill.payday.blocks.base;

import com.keerill.payday.PayDayMinecraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends Block
{
	public BlockBase(String name, Material material)
	{
		super(material);
		
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		
		this.setCreativeTab(PayDayMinecraft.PayDayTab);
	}
}
