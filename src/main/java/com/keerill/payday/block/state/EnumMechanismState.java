package com.keerill.payday.block.state;

import net.minecraft.util.IStringSerializable;

public enum EnumMechanismState implements IStringSerializable {
    WORK(0),
    NOT_WORK(1);

    private static final EnumMechanismState[] META_LOOKUP = new EnumMechanismState[values().length];

    private final int meta;

    EnumMechanismState(int meta)
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


    public static EnumMechanismState byMetadata(int index)
    {
        if (index < 0 || index > META_LOOKUP.length)
        {
            index = 0;
        }

        return META_LOOKUP[index];
    }

    static
    {
        for (EnumMechanismState state : values())
        {
            META_LOOKUP[state.getMetadata()] = state;
        }
    }
}
