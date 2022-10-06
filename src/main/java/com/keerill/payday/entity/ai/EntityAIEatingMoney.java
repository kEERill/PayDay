package com.keerill.payday.entity.ai;

import com.keerill.payday.BlocksRegistry;
import com.keerill.payday.ItemsRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIEatingMoney extends EntityAIBase
{
	protected int runDelay;
	private final EntityCreature entity;
	protected BlockPos destinationBlock = BlockPos.ORIGIN;
	
	private final double movementSpeed;
    private int timeoutCounter = 0;
    
    private int searchLength = 0;
    private int searchDelay = -1;
    private final int nominalSearchLength;
	private boolean endedTask;

    public EntityAIEatingMoney(EntityCreature entity, double movementSpeed, int searchLength)
    {
    	this.entity = entity;
    	this.movementSpeed = movementSpeed;
    	this.nominalSearchLength = searchLength;
    	
    	this.setMutexBits(7);    	
    }
    
    @Override
    public boolean shouldContinueExecuting() 
    {
    	if (!this.continueExecuting()) 
    	{
    		this.runDelay = this.entity.getRNG().nextInt(25) + 10;
    		return false;
    	}
    	
    	return true;
    }
    
	@Override
	public boolean shouldExecute()
    {
		if (this.runDelay == 0)
		{
			this.searchLength = this.searchLength == 0
				? this.nominalSearchLength : this.searchLength;
						
			this.runDelay = 200 + this.entity.getRNG().nextInt(200);
            boolean finded = this.searchForDestination();

            /**
             * Ситуация, когда сущность сломала блок и попыталась найти
             * ещё один по близости в радиусе двух блоков и не нашла, то делаем
             * почти сразу же новый поиск с номинальным радиусом
             */
            if (!finded && this.searchLength != this.nominalSearchLength)
            {
            	this.searchLength = this.nominalSearchLength;
            	this.runDelay = this.entity.getRNG().nextInt(25) + 10;
            }
            
            return finded;
		}

        --this.runDelay;
        return false;
    }

    public void startExecuting()
    {
    	this.searchLength = this.nominalSearchLength;
    	this.endedTask = false;
    	
        this.entity.getNavigator()
        	.tryMoveToXYZ(this.destinationBlock.getX(), this.destinationBlock.getY(), this.destinationBlock.getZ(), this.movementSpeed);
    }
    
    @Override
    public void resetTask() 
    {
    	this.timeoutCounter = 0;
    	this.runDelay = this.entity.getRNG().nextInt(25) + 10;
    }
    
    @Override
    public void updateTask() 
    {
    	this.entity.getLookHelper()
    		.setLookPosition(this.destinationBlock.getX(), this.destinationBlock.getY(), this.destinationBlock.getZ(), (float) this.entity.getHorizontalFaceSpeed(), (float) this.entity.getVerticalFaceSpeed());

		if (this.searchDelay == 0)
		{
			if (!this.shouldMoveTo(this.entity.world, this.destinationBlock))
			{
				if (!this.search(this.entity.getPosition(), 2))
				{
					this.endedTask = true;
				}
				
				this.searchDelay = -1;
				return;
			}
			
			this.searchDelay = -1;
		}

		if (this.searchDelay > 0)
		{
			--this.searchDelay;
			return;
		}
    	
    	if (this.entity.getDistanceSqToCenter(this.destinationBlock) > 1.5D)
        {
            ++this.timeoutCounter;
            
            if (this.timeoutCounter % 40 == 0)
            {
            	if (this.search(this.entity.getPosition(), 2))
            	{
            		this.timeoutCounter = 0;
            	}
            	
                this.entity.getNavigator()
                	.tryMoveToXYZ(this.destinationBlock.getX(), this.destinationBlock.getY(), this.destinationBlock.getZ(), this.movementSpeed);
            }
        }
        else
        {
        	if (this.shouldMoveTo(this.entity.world, this.destinationBlock)) 
        	{
        		this.onRemoveMoneyBlock(this.entity.world, this.destinationBlock, this.entity);
        	}
        }
    }

	protected boolean shouldMoveTo(World worldIn, BlockPos pos) 
	{
		IBlockState state = worldIn.getBlockState(pos);
		return state.getBlock() == BlocksRegistry.MONEY_DOUBLE || state.getBlock() == BlocksRegistry.MONEY;
	}
	
	/**
	 * Возвращает true, во время активной работы пути сущности к блоку
	 * @return boolean
	 */
	private boolean continueExecuting()
	{
		return !this.endedTask && this.timeoutCounter <= 400;
	}
	
	private void onRemoveMoneyBlock(World worldIn, BlockPos pos, EntityCreature entity)
	{
		worldIn.playEvent(2001, pos, Block.getIdFromBlock(worldIn.getBlockState(pos).getBlock()));
		worldIn.setBlockToAir(pos);
		
		entity.entityDropItem(new ItemStack(ItemsRegistry.MONEY, entity.getRNG().nextInt(3)), 1.0F);
		entity.eatGrassBonus();
		
		this.searchDelay = this.entity.getRNG().nextInt(15) + 10;
	}

	private boolean searchForDestination()
    {
        return this.search(this.entity.getPosition(), this.searchLength);
    }
	
	protected boolean search(BlockPos pos, int searchLength)
	{
        for (int k = 0; k <= 1; k = k > 0 ? -k : 1 - k)
        {
            for (int l = 0; l < searchLength; ++l)
            {
                for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1)
                {
                    for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1)
                    {
                        BlockPos blockpos1 = pos.add(i1, k - 1, j1);

                        if (this.entity.isWithinHomeDistanceFromPosition(blockpos1) && this.shouldMoveTo(this.entity.world, blockpos1))
                        {
                            this.destinationBlock = blockpos1;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
	}
}
