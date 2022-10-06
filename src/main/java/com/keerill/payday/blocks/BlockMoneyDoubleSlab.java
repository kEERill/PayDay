package com.keerill.payday.blocks;

public class BlockMoneyDoubleSlab extends BlockMoneySlab {

	public BlockMoneyDoubleSlab(String name) 
	{
		super(name);
	}

	@Override
	public boolean isDouble() 
	{
		return true;
	}
}
