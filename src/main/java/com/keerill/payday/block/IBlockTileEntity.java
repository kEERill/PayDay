package com.keerill.payday.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public interface IBlockTileEntity<T extends TileEntity> {
    Class<T> getTileEntityClass();

    ResourceLocation getRegistryName();
}
