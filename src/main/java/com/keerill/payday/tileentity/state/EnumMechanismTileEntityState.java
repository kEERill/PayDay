package com.keerill.payday.tileentity.state;

import net.minecraft.util.IStringSerializable;

public enum EnumMechanismTileEntityState implements IStringSerializable
{
    WAITING(0, "Is not working"),
    WORKING(1, "Working"),
    FINISHED(2, "Drill work finished!");

    private final int index;
    private final String message;

    private static final EnumMechanismTileEntityState[] STATES = new EnumMechanismTileEntityState[values().length];

    EnumMechanismTileEntityState(int index, String message)
    {
        this.message = message;
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }

    public String getMessage()
    {
        return this.message;
    }

    @Override
    public String getName()
    {
        return this.toString().toLowerCase();
    }

    public static EnumMechanismTileEntityState byIndex(int index)
    {
        if (index < 0 || index > STATES.length) {
            index = 0;
        }

        return STATES[index];
    }

    static
    {
        for (EnumMechanismTileEntityState state : values()) {
            STATES[state.getIndex()] = state;
        }
    }
}
