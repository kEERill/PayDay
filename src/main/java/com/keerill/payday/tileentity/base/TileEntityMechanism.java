package com.keerill.payday.tileentity.base;

import com.keerill.payday.block.IBlockMechanism;
import com.keerill.payday.block.IBlockStorage;
import com.keerill.payday.block.base.BlockHorizontal;
import com.keerill.payday.block.state.EnumMechanismState;
import com.keerill.payday.block.state.EnumStorageState;
import com.keerill.payday.tileentity.IHackingMechanism;
import com.keerill.payday.tileentity.ITileEntityMechanism;
import com.keerill.payday.tileentity.state.EnumMechanismTileEntityState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityMechanism extends TileEntityBase implements ITickable, ITileEntityMechanism
{
    public int time = 0;
    public int startTime = 0;

    public EnumMechanismTileEntityState state = EnumMechanismTileEntityState.WAITING;

    public abstract float getMultiplier();

    public void setSeconds(int seconds)
    {
        this.time = seconds * 20;
        this.startTime = seconds * 20;
    }

    public boolean isWork()
    {
        return this.state == EnumMechanismTileEntityState.WORKING;
    }

    private void setState(EnumMechanismTileEntityState state)
    {
        this.state = state;
    }

    protected void start(int seconds)
    {
        this.setSeconds(seconds);
        this.setState(EnumMechanismTileEntityState.WORKING);
        this.onBlockStateUpdated(EnumMechanismState.WORK);
    }

    protected void stop(EnumMechanismTileEntityState state)
    {
        this.setSeconds(0);
        this.setState(state);
        this.onBlockStateUpdated(EnumMechanismState.NOT_WORK);
    }

    protected void finished()
    {
        this.stop(EnumMechanismTileEntityState.FINISHED);
        this.openStorage();
    }

    @Override
    public void update()
    {
        if (this.isWork()) {
            --this.time;

            if (this.time <= 0) {
                this.finished();
            }
        }
    }

    @Override
    public void onUpdate(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileEntity = worldIn.getTileEntity(this.getFacingFrontPosition(pos, state));

        if (tileEntity instanceof IHackingMechanism) {
            IHackingMechanism targetStorageTileEntity = (IHackingMechanism) tileEntity;

            if (
                this.canHackingPosition(worldIn, this.getFacingFrontPosition(pos, state), pos) &&
                targetStorageTileEntity.canMechanismHacking()
            ) {
                if (!this.isWork()) {
                    int hackingValue = targetStorageTileEntity.getHackingMechanismValue();
                    this.start((int) (hackingValue * this.getMultiplier()));
                }

                return;
            }
        }

        if (this.isWork()) {
            this.stop(EnumMechanismTileEntityState.WAITING);
        }
    }

    protected void onBlockStateUpdated(EnumMechanismState mechanismState)
    {
        IBlockState state = this.getBlockState();

        if (state.getBlock() instanceof IBlockMechanism) {
            IBlockMechanism blockMechanism = (IBlockMechanism) state.getBlock();
            blockMechanism.onMechanismStateUpdate(this.getWorld(), this.getPos(), state, mechanismState);
        }
    }

    protected BlockPos getFacingFrontPosition(BlockPos pos, IBlockState state)
    {
        return pos.offset(state.getValue(BlockHorizontal.FACING), 1);
    }

    protected void openStorage()
    {
        World worldIn = this.getWorld();
        BlockPos pos = this.getFacingFrontPosition(this.getPos(), this.getBlockState());
        IBlockState state = worldIn.getBlockState(pos);

        if (state.getBlock() instanceof IBlockStorage) {
            IBlockStorage targetStorage = (IBlockStorage) state.getBlock();
            targetStorage.onStorageUpdateState(worldIn, pos, state, EnumStorageState.OPENED);
        }
    }

    protected boolean canHackingPosition(World worldIn, BlockPos storagePos, BlockPos mechanismPos)
    {
        IBlockState state = worldIn.getBlockState(storagePos);

        if (state.getBlock() instanceof IBlockStorage) {
            return storagePos.offset(state.getValue(BlockHorizontal.FACING), -1)
                .equals(mechanismPos);
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.time = compound.getInteger("time");
        this.startTime = compound.getInteger("startTime");
        this.state = EnumMechanismTileEntityState.byIndex(compound.getInteger("stateIndex"));
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
}
