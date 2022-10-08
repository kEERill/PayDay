package com.keerill.payday.block.state;

import net.minecraft.util.IStringSerializable;

public enum EnumToolState implements IStringSerializable {
    WORK(0),
    NOT_WORK(1);

    private static final EnumToolState[] META_LOOKUP = new EnumToolState[values().length];

    private final int meta;

    EnumToolState(int meta)
    {
        this.meta = meta;
    }

    @Override
    public String getName()
    {
        return this.toString().toLowerCase();
    }

    public int getMetadata()
    {
        return this.meta;
    }


    public static EnumToolState byMetadata(int index)
    {
        if (index < 0 || index > META_LOOKUP.length)
        {
            index = 0;
        }

        return META_LOOKUP[index];
    }

    static
    {
        for (EnumToolState state : values())
        {
            META_LOOKUP[state.getMetadata()] = state;
        }
    }
}
