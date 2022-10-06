package com.keerill.payday.proxy;

import com.keerill.payday.PayDayMinecraft;
import com.keerill.payday.gui.GuiHandler;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy 
{
    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) 
    {
    	NetworkRegistry.INSTANCE.registerGuiHandler(PayDayMinecraft.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {}
    
    public void registerItemRenderer(Item item, int meta, String name) {}
}
