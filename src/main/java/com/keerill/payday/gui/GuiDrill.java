package com.keerill.payday.gui;

import org.lwjgl.opengl.GL11;

import com.keerill.payday.PayDayMinecraft;
import com.keerill.payday.blocks.tile.TileEntityDrill;
import com.keerill.payday.gui.containers.ContainerDrill;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiDrill extends GuiContainer
{
	public static final int ID = 0;
	
	protected TileEntityDrill tileEntity;
	protected static final ResourceLocation textures = new ResourceLocation(PayDayMinecraft.MODID, "textures/gui/drill.png");
	
	public GuiDrill(IInventory playerInventory, TileEntityDrill tileEntity) 
	{
		super(new ContainerDrill(playerInventory, tileEntity));
		
		this.tileEntity = tileEntity;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(textures);
        
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        String seconds = "" + (this.tileEntity.time / 20);
        
        int progress = this.getProgress();
    	this.drawTexturedModalRect(x + 13, y + 51, 0, -90, progress, 20);
    	
        this.fontRenderer.drawString("Drill Model S 2.1", x + 12, y + 8, 0);
        
        this.drawCenteredString(fontRenderer, this.tileEntity.state.getMessage(), x + this.xSize/2, y + 28, 12955682);
        
        if (this.tileEntity.isWork()) 
        {
        	this.drawCenteredString(fontRenderer, "Seconds remaining", x + this.xSize/2, y + 80, 12955682);
        	this.drawCenteredString(fontRenderer, seconds, x + this.xSize/2, y + 90, 12955682);
        }
	}

	protected int getProgress() 
	{
		int i = this.tileEntity.time;
    	int j = this.tileEntity.startTime;
    	
		return j != 0 && i != 0 ? 150 - (i * 150 / j) : 0;
	}
}
