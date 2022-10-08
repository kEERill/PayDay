package com.keerill.payday;

import com.keerill.payday.proxy.CommonProxy;

import com.keerill.payday.registry.ItemsRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = PayDay.MOD_ID, name = PayDay.NAME, version = PayDay.VERSION)
public class PayDay
{
    public static final String MOD_ID = "payday";
    public static final String NAME = "PayDay";
    public static final String VERSION = "3.0";

    @Instance(PayDay.MOD_ID)
    public static PayDay instance;
    
    @SidedProxy(clientSide = "com.keerill.payday.proxy.ClientProxy", serverSide = "com.keerill.payday.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    public static final CreativeTabs PayDayTab = new CreativeTabs("tabPayday")
    {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemsRegistry.DALLAS);
        }

        @Override
        public String getTranslatedTabLabel() {
            return "PayDay";
        }
    };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        PayDayRegistry.init();

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
