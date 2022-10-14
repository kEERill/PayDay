package com.keerill.payday.proxy;

import com.keerill.payday.tileentity.TileEntitySafeCreative;
import com.keerill.payday.tileentity.renderer.TileEntitySafeSpecialRender;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import java.util.Objects;

public class ClientProxy extends CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
    	super.preInit(event);
    	
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySafeCreative.class, new TileEntitySafeSpecialRender());
    }

    public void init(FMLInitializationEvent event)
    {
    	super.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) 
    {
    	super.postInit(event);
    }
    
    @Override
    public void registerItemRenderer(Item item, int meta)
    {
        ResourceLocation registryName = Objects.requireNonNull(item.getRegistryName());
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(registryName, "inventory"));
    }
}
