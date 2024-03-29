package com.keerill.payday.tileentity.base;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TileEntityContainer extends TileEntityBase
{
	protected ItemStack[] inventory = new ItemStack [1];
	
    public int getSize()
    {
        return inventory.length;
    }
    
    public void setItem(int i, ItemStack item)
    {
    	inventory[i] = item;
    }

    public ItemStack getItem(int i)
    {
        return inventory[i] == null ? ItemStack.EMPTY : inventory[i];
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        
        if(tagCompound.hasKey("Items", 9)) {
            NBTTagList tagList = (NBTTagList) tagCompound.getTag("Items");
            ItemStack[] items = new ItemStack[this.getSize()];

            for(int i = 0; i < tagList.tagCount(); ++i) {
                NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
                byte slot = itemTag.getByte("Slot");

                if(slot >= 0 && slot < items.length) {
                    this.inventory[slot] = new ItemStack(itemTag);
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        
        NBTTagList inventory = new NBTTagList();
        
        for(int i = 0; i < this.getSize(); ++i) {
            ItemStack stack = this.getItem(i);

            NBTTagCompound itemTag = new NBTTagCompound();
            itemTag.setByte("Slot", (byte) i);
            stack.writeToNBT(itemTag);
            inventory.appendTag(itemTag);
        }
        
        tagCompound.setTag("Items", inventory);

        return tagCompound;
    }
}
