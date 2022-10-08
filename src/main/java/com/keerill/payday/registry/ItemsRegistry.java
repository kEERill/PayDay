package com.keerill.payday.registry;

import com.keerill.payday.PayDay;
import com.keerill.payday.PayDayRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

import com.keerill.payday.item.ItemMoney;
import com.keerill.payday.item.PayDayItemArmor;

import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraft.inventory.EntityEquipmentSlot;

@Mod.EventBusSubscriber
public class ItemsRegistry
{
	protected static final ItemArmor.ArmorMaterial dallasMaterial =
			EnumHelper.addArmorMaterial("payday:item_dallas", "payday:dallas", 9, new int[]{2, 4, 6, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	
	protected static final ItemArmor.ArmorMaterial chainsMaterial = 
			EnumHelper.addArmorMaterial("payday:item_chains", "payday:chains", 9, new int[]{2, 4, 6, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	
	protected static final ItemArmor.ArmorMaterial wolfMaterial = 
			EnumHelper.addArmorMaterial("payday:item_wolf", "payday:wolf", 9, new int[]{2, 4, 6, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	
	protected static final ItemArmor.ArmorMaterial hoxtonMaterial = 
			EnumHelper.addArmorMaterial("payday:item_hoxton", "payday:hoxton", 9, new int[]{2, 4, 6, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	
    public static final PayDayItemArmor DALLAS;
    public static final PayDayItemArmor CHAINS;
    public static final PayDayItemArmor WOLF;
    public static final PayDayItemArmor HOXTON;
	public static final ItemMoney MONEY;

	static
	{
		DALLAS = (PayDayItemArmor) new PayDayItemArmor(dallasMaterial, 1, EntityEquipmentSlot.HEAD)
				.setCreativeTab(PayDay.PayDayTab)
				.setRegistryName(PayDay.MOD_ID, "item_dallas")
				.setUnlocalizedName("dallas");

		CHAINS = (PayDayItemArmor) new PayDayItemArmor(chainsMaterial, 1, EntityEquipmentSlot.HEAD)
				.setCreativeTab(PayDay.PayDayTab)
				.setRegistryName(PayDay.MOD_ID, "item_chains")
				.setUnlocalizedName("chains");

		WOLF = (PayDayItemArmor) new PayDayItemArmor(wolfMaterial, 1, EntityEquipmentSlot.HEAD)
				.setCreativeTab(PayDay.PayDayTab)
				.setRegistryName(PayDay.MOD_ID, "item_wolf")
				.setUnlocalizedName("wolf");

		HOXTON = (PayDayItemArmor) new PayDayItemArmor(hoxtonMaterial, 1, EntityEquipmentSlot.HEAD)
				.setCreativeTab(PayDay.PayDayTab)
				.setRegistryName(PayDay.MOD_ID, "item_hoxton")
				.setUnlocalizedName("hoxton");

		MONEY = (ItemMoney) new ItemMoney()
				.setCreativeTab(PayDay.PayDayTab)
				.setRegistryName(PayDay.MOD_ID, "item_money")
				.setUnlocalizedName("money");
	}

	public static void register()
	{
		registerItem(DALLAS);
		registerItem(CHAINS);
		registerItem(WOLF);
		registerItem(HOXTON);
		registerItem(MONEY);
	}

	private static void registerItem(Item item)
	{
		PayDayRegistry.Items.add(item);
	}
}
