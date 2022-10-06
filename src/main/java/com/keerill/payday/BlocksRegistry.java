package com.keerill.payday;

import net.minecraft.item.Item;

import com.keerill.payday.blocks.BlockCreativeSafe;
import com.keerill.payday.blocks.BlockDrill;
import com.keerill.payday.blocks.BlockGold;
import com.keerill.payday.blocks.BlockMoneyDoubleSlab;
import com.keerill.payday.blocks.BlockMoneySlab;
import com.keerill.payday.blocks.BlockSafe;
import com.keerill.payday.blocks.base.BlockTileEntity;
import com.keerill.payday.blocks.tile.renderer.TileEntitySafeSpecialRender;
import com.keerill.payday.items.ItemMoneySlab;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BlocksRegistry 
{
	public static final BlockGold GOLD = new BlockGold("block_gold");
	public static final ItemBlock GOLD_ITEM = 
			(ItemBlock) new ItemBlock(GOLD).setRegistryName(GOLD.getRegistryName()).setMaxStackSize(1);
	
	public static final BlockMoneySlab MONEY = new BlockMoneySlab("block_money");
	public static final BlockMoneyDoubleSlab MONEY_DOUBLE = new BlockMoneyDoubleSlab("block_money_double");
	
	public static final ItemMoneySlab MONEY_ITEM = 
			(ItemMoneySlab) new ItemMoneySlab(MONEY, MONEY, MONEY_DOUBLE).setRegistryName(MONEY.getRegistryName()).setMaxStackSize(1);
	
	public static final BlockSafe CREATIVE_SAFE = new BlockCreativeSafe("block_creative_safe");
	public static final ItemBlock CREATIVE_SAFE_ITEM = 
			(ItemBlock) new ItemBlock(CREATIVE_SAFE).setRegistryName(CREATIVE_SAFE.getRegistryName()).setMaxStackSize(1);
	
	public static final BlockDrill DRILL = new BlockDrill("block_drill");
	public static final ItemBlock DRILL_ITEM =
			(ItemBlock) new ItemBlock(DRILL).setRegistryName(DRILL.getRegistryName()).setMaxStackSize(1);
	
	@SubscribeEvent
    public static void onRegistryBlock(RegistryEvent.Register<Block> event) 
	{
    	event.getRegistry().registerAll(GOLD, MONEY, MONEY_DOUBLE, CREATIVE_SAFE, DRILL);
    	
    	BlocksRegistry.registerTileEntities(CREATIVE_SAFE, DRILL);
    	
    	net.minecraft.init.Blocks.FIRE.setFireInfo(MONEY, 5, 20);
    	net.minecraft.init.Blocks.FIRE.setFireInfo(MONEY_DOUBLE, 5, 40);
    }

	@SubscribeEvent
    public static void onRegistryItems(RegistryEvent.Register<Item> event) 
	{
    	event.getRegistry().registerAll(GOLD_ITEM, MONEY_ITEM, CREATIVE_SAFE_ITEM, DRILL_ITEM);
    }
	
	@SubscribeEvent
	public static void registerItems(ModelRegistryEvent event) 
	{
		ItemsRegistry.registerItemModels(GOLD_ITEM, MONEY_ITEM, CREATIVE_SAFE_ITEM, DRILL_ITEM);
	}
	
	public static void registerTileEntities(BlockTileEntity<?> ...blocks)
	{
		for (BlockTileEntity<?> blockTileEntity : blocks) 
		{
			GameRegistry.registerTileEntity(blockTileEntity.getTileEntityClass(), blockTileEntity.getRegistryName());
		}
	}
	
	public static void registerTileEntitiesSpecialRenderer()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(CREATIVE_SAFE.getTileEntityClass(), new TileEntitySafeSpecialRender());
	}
}
