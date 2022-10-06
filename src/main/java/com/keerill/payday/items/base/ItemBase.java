package com.keerill.payday.items.base;

import com.keerill.payday.PayDayMinecraft;

import net.minecraft.item.Item;

public abstract class ItemBase extends Item
{
	public ItemBase(String name)
	{
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		
		this.setCreativeTab(PayDayMinecraft.PayDayTab);
	}
}
