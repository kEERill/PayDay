package com.keerill.payday;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

import com.keerill.payday.items.ItemMoney;
import com.keerill.payday.items.PayDayItemArmor;

import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder("payday")
@Mod.EventBusSubscriber
public class ItemsRegistry 
{
	protected static final ItemArmor.ArmorMaterial dallasMaterial =
			EnumHelper.addArmorMaterial("payday:dallas", "payday:dallas", 9, new int[]{2, 4, 6, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	
	protected static final ItemArmor.ArmorMaterial chainsMaterial = 
			EnumHelper.addArmorMaterial("payday:chains", "payday:chains", 9, new int[]{2, 4, 6, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	
	protected static final ItemArmor.ArmorMaterial wolfMaterial = 
			EnumHelper.addArmorMaterial("payday:wolf", "payday:wolf", 9, new int[]{2, 4, 6, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	
	protected static final ItemArmor.ArmorMaterial hoxtonMaterial = 
			EnumHelper.addArmorMaterial("payday:hoxton", "payday:hoxton", 9, new int[]{2, 4, 6, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
	
	@ObjectHolder("payday:dallas")
    public static final PayDayItemArmor DALLAS = new PayDayItemArmor("dallas", dallasMaterial, 1, EntityEquipmentSlot.HEAD);
	
	@ObjectHolder("payday:chains")
    public static final PayDayItemArmor CHAINS = new PayDayItemArmor("chains", chainsMaterial, 1, EntityEquipmentSlot.HEAD);
	
	@ObjectHolder("payday:wolf")
    public static final PayDayItemArmor WOLF = new PayDayItemArmor("wolf", wolfMaterial, 1, EntityEquipmentSlot.HEAD);
	
	@ObjectHolder("payday:hoxton")
    public static final PayDayItemArmor HOXTON = new PayDayItemArmor("hoxton", hoxtonMaterial, 1, EntityEquipmentSlot.HEAD);
	
	public static final ItemMoney MONEY = new ItemMoney("money");
    
	
	@SubscribeEvent
    public static void onRegistryItem(RegistryEvent.Register<Item> event) 
	{
    	event.getRegistry().registerAll(DALLAS, CHAINS, WOLF, HOXTON, MONEY);
    }
	
	@SubscribeEvent
	public static void registerItems(ModelRegistryEvent event) 
	{
		ItemsRegistry.registerItemModels(DALLAS, CHAINS, WOLF, HOXTON, MONEY);
	}
	
	public static void registerItemModels(Item ...items)
	{
		for (Item item: items)
		{
			PayDayMinecraft.proxy.registerItemRenderer(item, 0, item.getRegistryName().toString());			
		}
	}
}
