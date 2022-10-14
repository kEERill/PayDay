package com.keerill.payday.block.base;

import com.keerill.payday.block.IBlockMechanism;
import com.keerill.payday.block.state.EnumMechanismState;
import com.keerill.payday.tileentity.ITileEntityMechanism;
import com.keerill.payday.tileentity.base.TileEntityMechanism;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockMechanism<T extends TileEntityMechanism> extends BlockTileEntity<T> implements IBlockMechanism
{
    public static final PropertyEnum<EnumMechanismState> STATE = PropertyEnum.create("state", EnumMechanismState.class);

    public BlockMechanism(Material material)
    {
        super(material);

        this.setDefaultState(
            this.getDefaultState()
                .withProperty(STATE, EnumMechanismState.NOT_WORK)
        );
    }

    @Override
    public abstract Class<T> getTileEntityClass();

    @Override
    public abstract T createTileEntity(World world, IBlockState state);

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, STATE);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return super.getMetaFromState(state) | (state.getValue(STATE).getMetadata() << 3);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return super.getStateFromMeta(meta)
                .withProperty(STATE, EnumMechanismState.byMetadata(meta >> 3));
    }

    @Override
    public void onMechanismStateUpdate(World worldIn, BlockPos pos, IBlockState state, EnumMechanismState mechanismState)
    {
        if (state.getBlock() instanceof BlockMechanism) {
            worldIn.setBlockState(pos, state.withProperty(STATE, mechanismState));
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        this.onUpdateTileEntity(worldIn, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
                                ItemStack stack)
    {
        this.onUpdateTileEntity(worldIn, pos, state);
    }

    public void onUpdateTileEntity(World worldIn, BlockPos pos, IBlockState state)
    {
        if (worldIn.isRemote) {
            return;
        }

        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity instanceof ITileEntityMechanism) {
            ITileEntityMechanism iTileEntityMechanism = (ITileEntityMechanism) tileEntity;
            iTileEntityMechanism.onUpdate(worldIn, pos, state);
        }
    }
}
