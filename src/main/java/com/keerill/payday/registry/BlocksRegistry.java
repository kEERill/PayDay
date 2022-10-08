package com.keerill.payday.registry;

import com.keerill.payday.PayDay;
import com.keerill.payday.PayDayRegistry;
import com.keerill.payday.block.*;
import com.keerill.payday.item.ItemGoldSlab;
import net.minecraft.block.BlockSlab;

import com.keerill.payday.tileentity.renderer.TileEntitySafeSpecialRender;
import com.keerill.payday.item.ItemMoneySlab;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

@Mod.EventBusSubscriber
public class BlocksRegistry 
{
	public static final BlockGoldSlab GOLD;
	public static final BlockGoldDoubleSlab GOLD_DOUBLE;
	public static final ItemGoldSlab GOLD_ITEM;
	public static final BlockMoneySlab MONEY;
	public static final BlockMoneyDoubleSlab MONEY_DOUBLE;
	public static final ItemMoneySlab MONEY_ITEM;
	public static final BlockSafe CREATIVE_SAFE;
	public static BlockDrill DRILL;

	static
	{
		GOLD = (BlockGoldSlab) new BlockGoldSlab()
				.setCreativeTab(PayDay.PayDayTab)
				.setUnlocalizedName("block_gold")
				.setRegistryName("block_gold");

		GOLD_DOUBLE = (BlockGoldDoubleSlab) new BlockGoldDoubleSlab()
				.setCreativeTab(PayDay.PayDayTab)
				.setUnlocalizedName("block_gold_double")
				.setRegistryName("block_gold_double");

		GOLD_ITEM = (ItemGoldSlab) new ItemGoldSlab(GOLD, GOLD, GOLD_DOUBLE)
				.setMaxStackSize(1);

		DRILL = (BlockDrill) new BlockDrill()
				.setCreativeTab(PayDay.PayDayTab)
				.setRegistryName(PayDay.MOD_ID,"block_drill")
				.setUnlocalizedName("block_drill");

		CREATIVE_SAFE = (BlockSafe) new BlockSafeCreative()
				.setCreativeTab(PayDay.PayDayTab)
				.setRegistryName(PayDay.MOD_ID, "block_safe_creative")
				.setUnlocalizedName("block_safe_creative");

		MONEY = (BlockMoneySlab) new BlockMoneySlab()
				.setCreativeTab(PayDay.PayDayTab)
				.setRegistryName(PayDay.MOD_ID,"block_money")
				.setUnlocalizedName("block_money");

		MONEY_DOUBLE = (BlockMoneyDoubleSlab) new BlockMoneyDoubleSlab()
				.setCreativeTab(PayDay.PayDayTab)
				.setRegistryName(PayDay.MOD_ID, "block_money_double")
				.setUnlocalizedName("block_money_double");

		MONEY_ITEM = (ItemMoneySlab) new ItemMoneySlab(MONEY, MONEY, MONEY_DOUBLE)
				.setMaxStackSize(1);
	}

	public static void register()
	{
		registerBlockOnce(DRILL);
		registerBlockOnce(CREATIVE_SAFE);

		registerBlockSlab(GOLD, GOLD_DOUBLE, GOLD_ITEM);
		registerBlockSlab(MONEY, MONEY_DOUBLE, MONEY_ITEM);
	}

	private static void registerBlock(Block block, ItemBlock itemBlock)
	{
		itemBlock = (ItemBlock) itemBlock
				.setRegistryName(Objects.requireNonNull(block.getRegistryName()));

		PayDayRegistry.Blocks.add(block);
		PayDayRegistry.Items.add(itemBlock);
	}

	private static void registerBlockOnce(Block block)
	{
		registerBlock(block, (ItemBlock) new ItemBlock(block).setMaxStackSize(1));
	}

	private static void registerBlockSlab(BlockSlab block, BlockSlab doubleBlock, ItemSlab itemBlock)
	{
		PayDayRegistry.Blocks.add(doubleBlock);
		registerBlock(block, itemBlock);
	}

	private static void registerBlock(Block block)
	{
		registerBlock(block, new ItemBlock(block));
	}
	
	public static void registerTileEntitiesSpecialRenderer()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(CREATIVE_SAFE.getTileEntityClass(), new TileEntitySafeSpecialRender());
	}

	@SubscribeEvent
	public static void onRegistryBlock(RegistryEvent.Register<Block> event)
	{
		net.minecraft.init.Blocks.FIRE.setFireInfo(MONEY, 5, 20);
		net.minecraft.init.Blocks.FIRE.setFireInfo(MONEY_DOUBLE, 5, 40);
	}
}
