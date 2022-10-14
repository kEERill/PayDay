package com.keerill.payday.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

import java.util.List;

public class TileEntitySafeCreative extends TileEntitySafe
{
    public void setupItem(World worldIn)
    {
        LootTable lootTable = worldIn.getLootTableManager()
                .getLootTableFromLocation(new ResourceLocation("payday:creative_safe"));

        LootContext ctx = new LootContext.Builder((WorldServer) worldIn)
                .build();

        List<ItemStack> stacks = lootTable.generateLootForPools(worldIn.rand, ctx);

        if (!stacks.isEmpty()) {
            this.setItem(0, stacks.get(0));
        }
    }
}
