package com.keerill.payday.gui;

import com.keerill.payday.tileentity.TileEntityDrill;
import com.keerill.payday.gui.containers.ContainerDrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		Object object = null;
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		
		switch (ID)
		{
			case GuiDrill.ID:
				object = new ContainerDrill(player.inventory, (TileEntityDrill) tileEntity);
			break;
		}
		
		return object;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		Object object = null;
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		
		switch (ID)
		{
			case GuiDrill.ID:
				object = new GuiDrill(player.inventory, (TileEntityDrill) tileEntity);
			break;
		}
		
		return object;
	}
}
