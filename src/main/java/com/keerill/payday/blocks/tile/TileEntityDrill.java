package com.keerill.payday.blocks.tile;

import com.keerill.payday.blocks.BlockDrill;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityDrill extends TileEntityBase implements ITickable
{
	public int time = 0;
	public int startTime = 0;
	
	public TileEntityDrill.EnumState state = EnumState.WAITING;
	
	public void setSeconds(int seconds)
	{
		this.time = seconds * 20;
		this.startTime = seconds * 20;
	}
	
	public void startWork(int seconds)
	{
		this.start(TileEntityDrill.EnumState.WORKING, seconds);
	}
	
	public boolean isWork()
	{
		return this.state == EnumState.WORKING;
	}
	
	public void stopWork()
	{
		this.stop(TileEntityDrill.EnumState.WAITING);
	}
	
	protected void finished()
	{
		this.stop(TileEntityDrill.EnumState.FINISHED);
		
		IBlockState iblockstate = this.getBlockState();
		
		if (iblockstate.getBlock() instanceof BlockDrill) 
		{
			BlockDrill block = (BlockDrill) iblockstate.getBlock();
			block.onDrillEnded(this.getWorld(), this.getPos(), iblockstate);
		}
	}
	
	protected void start(TileEntityDrill.EnumState state, int seconds)
	{
		this.setSeconds(seconds);
		this.state = EnumState.WORKING;
		
		IBlockState iBlockState = this.getBlockState();
		
		if (iBlockState.getBlock() instanceof BlockDrill)
		{
			BlockDrill blockDrill = (BlockDrill) iBlockState.getBlock();
			blockDrill.onDrillStarted(this.getWorld(), this.getPos(), iBlockState);
		}
	}
	
	protected void stop(TileEntityDrill.EnumState state)
	{
		this.setSeconds(0);
		this.state = state;
		
		IBlockState iBlockState = this.getBlockState();
		
		if (iBlockState.getBlock() instanceof BlockDrill)
		{
			BlockDrill blockDrill = (BlockDrill) iBlockState.getBlock();
			blockDrill.onDrillStopped(this.getWorld(), this.getPos(), iBlockState);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		
		this.time = compound.getInteger("time");
		this.startTime = compound.getInteger("startTime");
		this.state = TileEntityDrill.EnumState.byIndex(compound.getInteger("stateIndex"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		
		compound.setInteger("time", this.time);
		compound.setInteger("startTime", this.startTime);
		compound.setInteger("stateIndex", this.state.getIndex());
		
		return compound;
	}

	@Override
	public void update() 
	{
		if (this.isWork())
		{
			--this.time;
			
			if (this.time <= 0) 
			{
				this.finished();
			}
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) 
	{
		return oldState.getBlock() != newSate.getBlock();
	}
	
	private IBlockState getBlockState()
	{
		return this.getWorld().getBlockState(this.getPos());
	}
	
	public static enum EnumState implements IStringSerializable
	{
		WAITING(0, "Is not working"),
		WORKING(1, "Working"),
		FINISHED(2, "Drill work finished!");
		
		private int index;
		private String message;
		
		private static final TileEntityDrill.EnumState[] STATES = new TileEntityDrill.EnumState[values().length];
		
		EnumState(int index, String message)
		{
			this.message = message;
			this.index = index;
		}
		
		public String getMessage()
		{
			return this.message;
		}
		
		public int getIndex()
		{
			return index;
		}
		
		public static TileEntityDrill.EnumState byIndex(int index)
		{
			if (index < 0 || index > STATES.length)
			{
				index = 0;
			}
			
			return STATES[index];
		}
		
		static
	    {
	        for (TileEntityDrill.EnumState state : values())
	        {
	        	STATES[state.getIndex()] = state;
	        }
	    }

		@Override
		public String getName() 
		{
			return this.toString().toLowerCase();
		}
	}
}
