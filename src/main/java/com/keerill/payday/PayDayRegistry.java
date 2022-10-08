package com.keerill.payday;

import com.keerill.payday.block.IBlockTileEntity;
import com.keerill.payday.registry.BlocksRegistry;
import com.keerill.payday.registry.ItemsRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.LinkedList;
import java.util.List;

public class PayDayRegistry
{
    @Mod.EventBusSubscriber(modid = PayDay.MOD_ID)
    public static class Blocks
    {
        private static final List<Block> BLOCKS = new LinkedList<>();

        public static void add(Block block)
        {
            BLOCKS.add(block);
        }

        @SubscribeEvent
        public static void register(final RegistryEvent.Register<Block> event)
        {
            BLOCKS.forEach(block -> event.getRegistry().register(block));
        }
    }

    @Mod.EventBusSubscriber(modid = PayDay.MOD_ID)
    public static class Items
    {
        private static final List<Item> ITEMS = new LinkedList<>();

        public static void add(Item item)
        {
            ITEMS.add(item);
        }

        @SubscribeEvent
        public static void register(final RegistryEvent.Register<Item> event)
        {
            ITEMS.forEach(item -> event.getRegistry().register(item));
        }
    }

    @Mod.EventBusSubscriber(modid = PayDay.MOD_ID)
    public static class TileEntities
    {
        @SubscribeEvent
        public static void register(final RegistryEvent.Register<Block> event)
        {
            Blocks.BLOCKS.forEach(TileEntities::registerTileEntityBlock);
        }

        private static void registerTileEntityBlock(Block block)
        {
            if (block instanceof IBlockTileEntity) {
                IBlockTileEntity<TileEntity> iBlockTileEntity = (IBlockTileEntity<TileEntity>) block;
                GameRegistry.registerTileEntity(iBlockTileEntity.getTileEntityClass(), iBlockTileEntity.getRegistryName());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = PayDay.MOD_ID)
    public static class Models
    {
        @SubscribeEvent
        public static void register(ModelRegistryEvent event)
        {
            Items.ITEMS.forEach(Models::registerRender);
        }

        private static void registerRender(Item item)
        {
            PayDay.proxy.registerItemRenderer(item, 0);
        }
    }

    public static void init()
    {
        BlocksRegistry.register();
        ItemsRegistry.register();
    }
}
