package com.keerill.payday.gui.containers;

import com.keerill.payday.blocks.tile.TileEntityDrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class ContainerDrill extends Container
{
	protected TileEntityDrill tileEntity;
	
	public ContainerDrill(IInventory playerInventory, TileEntityDrill tileEntity) 
	{
		this.tileEntity = tileEntity;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		return true;
	}

}
