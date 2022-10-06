package com.keerill.payday.items;

import com.keerill.payday.PayDayMinecraft;

import net.minecraft.item.ItemArmor;
import net.minecraft.inventory.EntityEquipmentSlot;

public class PayDayItemArmor extends ItemArmor
{
	public PayDayItemArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
    {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        
        this.setCreativeTab(PayDayMinecraft.PayDayTab);
    }
}
