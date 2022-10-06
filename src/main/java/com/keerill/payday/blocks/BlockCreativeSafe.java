package com.keerill.payday.blocks;

import java.util.List;

import com.keerill.payday.blocks.tile.TileEntitySafe;
import com.keerill.payday.interfaces.IBlockStorageDrillable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public class BlockCreativeSafe extends BlockSafe implements IBlockStorageDrillable
{
	public BlockCreativeSafe(String name) 
	{
		super(name);
	}
	
	@Override
	public int getHackingSeconds() 
	{
		return 10;
	}
	
	@Override
	public Class<TileEntitySafe> getTileEntityClass() 
	{
		return TileEntitySafe.class;
	}

	@Override
	public TileEntitySafe createTileEntity(World world, IBlockState state) 
	{
		return new TileEntitySafe();
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) 
	{
		super.onBlockAdded(worldIn, pos, state);
		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		if(tileEntity instanceof TileEntitySafe)
		{
			if (!worldIn.isRemote)
			{
				LootTable lootTable = worldIn.getLootTableManager()
						.getLootTableFromLocation(new ResourceLocation("payday:creative_safe"));
				
				LootContext ctx = new LootContext.Builder((WorldServer) worldIn)
					    .build();
				
				List<ItemStack> stacks = lootTable.generateLootForPools(worldIn.rand, ctx);
				
				if (!stacks.isEmpty()) {
					TileEntitySafe tileEntitySafe = (TileEntitySafe) tileEntity;
					tileEntitySafe.setItem(0, stacks.get(0));									
				}
			}
			
			worldIn.notifyBlockUpdate(pos, state, state, 3);
		}
	}
}
