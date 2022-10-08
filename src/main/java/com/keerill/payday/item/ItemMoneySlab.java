package com.keerill.payday.item;

import com.keerill.payday.block.BlockMoneyDoubleSlab;
import com.keerill.payday.block.BlockMoneySlab;

import com.keerill.payday.item.base.ItemSlabBase;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

public class ItemMoneySlab extends ItemSlabBase
{
	public ItemMoneySlab(Block block, BlockMoneySlab singleSlab, BlockMoneyDoubleSlab doubleSlab) {
		super(block, singleSlab, doubleSlab);
	}

    @Override
    protected <T extends Comparable<T>> IBlockState makeState(IBlockState state, IProperty<T> p_185055_1_, Comparable<?> p_185055_2_) {
        return super.makeState(state, p_185055_1_, p_185055_2_)
                .withProperty(BlockMoneySlab.BOTTOM, state.getValue(BlockMoneySlab.BOTTOM));
    }
}
