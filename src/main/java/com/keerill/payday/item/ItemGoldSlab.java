package com.keerill.payday.item;

import com.keerill.payday.block.BlockGoldDoubleSlab;
import com.keerill.payday.block.BlockGoldSlab;
import com.keerill.payday.item.base.ItemSlabBase;
import net.minecraft.block.Block;

public class ItemGoldSlab extends ItemSlabBase
{
    public ItemGoldSlab(Block block, BlockGoldSlab singleSlab, BlockGoldDoubleSlab doubleSlab)
    {
        super(block, singleSlab, doubleSlab);
    }
}
