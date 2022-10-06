package com.keerill.payday.items;

import com.keerill.payday.items.base.ItemBase;

public class ItemMoney extends ItemBase
{
	public ItemMoney(String name) {
		super(name);
		
		this.setMaxStackSize(16);
		this.setMaxDamage(0);
	}
}
