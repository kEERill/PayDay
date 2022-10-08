package com.keerill.payday.block.state;

import net.minecraft.util.IStringSerializable;

public enum EnumStorageState implements IStringSerializable {
    CLOSED(0),
    OPENED(1);

    private static final EnumStorageState[] META_LOOKUP = new EnumStorageState[values().length];

    private final int meta;

    EnumStorageState(int meta)
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


    public static EnumStorageState byMetadata(int index)
    {
        if (index < 0 || index > META_LOOKUP.length)
        {
            index = 0;
        }

        return META_LOOKUP[index];
    }

    static
    {
        for (EnumStorageState state : values())
        {
            META_LOOKUP[state.getMetadata()] = state;
        }
    }
}
