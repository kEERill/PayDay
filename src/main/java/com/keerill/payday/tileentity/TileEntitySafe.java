package com.keerill.payday.tileentity;

import com.keerill.payday.block.base.BlockStorage;
import com.keerill.payday.block.state.EnumStorageState;
import com.keerill.payday.tileentity.base.TileEntityStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TileEntitySafe extends TileEntityStorage
        implements IHackingMechanism
{
    @Override
    public boolean canMechanismHacking()
    {
        return this.getBlockState().getValue(BlockStorage.STATE) == EnumStorageState.CLOSED;
    }

    @Override
    public int getHackingMechanismValue()
    {
        return 80;
    }

    public ItemStack getItem()
    {
        return this.getItem(0);
    }

    public void getItemForPlayer(EntityPlayer playerIn)
    {
        ItemStack item = this.getItem();

        if (!item.isEmpty()) {
            playerIn.inventory.addItemStackToInventory(item);
        }

        this.markDirty();
    }
}
