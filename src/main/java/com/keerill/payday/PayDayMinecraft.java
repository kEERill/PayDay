package com.keerill.payday;

import com.keerill.payday.proxy.CommonProxy;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = PayDayMinecraft.MODID, name = PayDayMinecraft.NAME, version = PayDayMinecraft.VERSION)
public class PayDayMinecraft 
{
	@Instance(PayDayMinecraft.MODID)
	public static PayDayMinecraft instance;
	
    public static final String MODID = "payday";
    public static final String NAME = "PayDay";
    public static final String VERSION = "3.0";
    
    @SidedProxy(clientSide = "com.keerill.payday.proxy.ClientProxy", serverSide = "com.keerill.payday.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    public static final CreativeTabs PayDayTab = new CreativeTabs("payday")
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemsRegistry.DALLAS);
        }
    };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
}
