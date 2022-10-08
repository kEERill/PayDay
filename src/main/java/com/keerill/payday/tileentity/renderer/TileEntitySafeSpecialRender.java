package com.keerill.payday.tileentity.renderer;


import com.keerill.payday.block.BlockSafe;
import com.keerill.payday.tileentity.TileEntitySafe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntitySafeSpecialRender extends TileEntitySpecialRenderer<TileEntitySafe>
{
	private final EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D);
	
	@Override
	public void render(TileEntitySafe tileentity, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		
		this.entityItem.hoverStart = 0.0F;

		ItemStack item = tileentity.getItem(0);
		
		if(item != ItemStack.EMPTY) 
		{
			IBlockState iblockstate = this.getWorld().getBlockState(tileentity.getPos());
			EnumFacing facing = iblockstate.getValue(BlockSafe.FACING);

			this.entityItem.setItem(item.copy());
			this.entityItem.setNoDespawn();
			
			float scaleX, scaleY, scaleZ;
			
			scaleX = 1;
			scaleY = 1;
			scaleZ = 1;
			
			x += 0.5;
			z += 0.5;
			
			if (item.getItem() instanceof ItemBlock) {
				y -= 0.325F;
				
				scaleX = 2.3F;
				scaleY = 2.3F;
				scaleZ = 2.3F;
			}
			
			GlStateManager.pushMatrix();
			{

				int rotate = -90 * facing.getHorizontalIndex();
				
				GlStateManager.translate(x, y, z);
				GlStateManager.rotate(rotate, 0, 1, 0);
				GlStateManager.scale(scaleX, scaleY, scaleZ);
				Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
			}
			GlStateManager.popMatrix();
		}
	}
}
