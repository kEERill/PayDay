package com.keerill.payday.blocks;

import java.util.Random;

import com.keerill.payday.BlocksRegistry;
import com.keerill.payday.blocks.base.BlockSlabBase;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMoneySlab extends BlockSlabBase
{
	public static boolean fallInstantly;
	
	public static final PropertyBool BOTTOM = PropertyBool.create("bottom");
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public BlockMoneySlab(String name)
	{
		super(name, Material.WOOD);
		
		this.setDefaultState(
			this.blockState.getBaseState()
				.withProperty(FACING, EnumFacing.NORTH)
				.withProperty(BOTTOM, false)
				.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM)
		);
		
		this.setSoundType(SoundType.WOOD);
		this.setHardness(this.isDouble() ? 1.5F : 0.75F);
		this.setHarvestLevel("shovel", 0);
	}
	
	public boolean isDouble()
	{
		return false;
	}
	
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	public boolean isFullCube(IBlockState state) 
	{
		return this.isDouble();
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) 
	{
		return state.withProperty(BOTTOM, this.checkBottomBlock(worldIn, pos));
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) 
	{
		IBlockState iBlockState = worldIn.getBlockState(pos.down());
		
		if (iBlockState.getBlock() instanceof BlockMoneySlab)
		{
			BlockMoneySlab block = (BlockMoneySlab) iBlockState.getBlock();
			return block.isDouble();
		}
		
		return super.canPlaceBlockAt(worldIn, pos);
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState iBlockState = worldIn.getBlockState(pos.down());
		
		EnumFacing enumFacing = iBlockState.getBlock() instanceof BlockMoneyDoubleSlab
			? iBlockState.getValue(FACING)
			: placer.getHorizontalFacing();
		
		return this.getDefaultState()
				.withProperty(FACING, enumFacing)
				.withProperty(BOTTOM, this.checkBottomBlock(worldIn, pos))
				.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
	}
	
	protected boolean checkBottomBlock(IBlockAccess worldIn, BlockPos pos)
	{
		Block block = worldIn.getBlockState(pos.down()).getBlock();
		return block == Blocks.AIR || block == BlocksRegistry.MONEY || block == BlocksRegistry.MONEY_DOUBLE;
	}
	
	@Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return state.isOpaqueCube();
    }
	
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.SOLID;
    }
	
    @Override
    public int getMetaFromState(IBlockState state)
    {
    	int meta = 0;
    	
    	meta = meta | state.getValue(FACING).getHorizontalIndex();
        
        if (((Boolean) state.getValue(BOTTOM)).booleanValue()) 
        {
        	meta |= 8;
        }
        
        return meta;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
        		.withProperty(FACING, EnumFacing.getHorizontal(meta & 7))
        		.withProperty(BOTTOM, Boolean.valueOf((meta & 8) != 0))
        		.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
    }
    
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, HALF, BOTTOM });			
	}
	
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote) 
        {
            this.checkFallable(worldIn, pos);
        }
    }
    
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) 
    {
    	return new ItemStack(BlocksRegistry.MONEY_ITEM);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	return BlocksRegistry.MONEY_ITEM;
    }
    
    @Override
    public int quantityDropped(Random random) 
    {
    	return this.isDouble() ? 2 : 1;
    }

    private void checkFallable(World worldIn, BlockPos pos)
    {
        if ((worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down()))) && pos.getY() >= 0) 
        {
            if (!fallInstantly && worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32))) 
            {
                if (!worldIn.isRemote) 
                {
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, worldIn.getBlockState(pos));
                    worldIn.spawnEntity(entityfallingblock);
                }
            }
            else 
            {
                IBlockState state = worldIn.getBlockState(pos);
                worldIn.setBlockToAir(pos);
                BlockPos blockpos;

                for (blockpos = pos.down(); (worldIn.isAirBlock(blockpos) || canFallThrough(worldIn.getBlockState(blockpos))) && blockpos.getY() > 0; blockpos = blockpos.down()) 
                {
                    ;
                }

                if (blockpos.getY() > 0) 
                {
                    worldIn.setBlockState(blockpos.up(), state); //Forge: Fix loss of state information during world gen.
                }
            }
        }
    }

    public int tickRate(World worldIn)
    {
        return 2;
    }

    public static boolean canFallThrough(IBlockState state)
    {
        Block block = state.getBlock();
        Material material = state.getMaterial();
        return block == Blocks.FIRE || material == Material.AIR || material == Material.WATER || material == Material.LAVA;
    }


    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (rand.nextInt(16) == 0)
        {
            BlockPos blockpos = pos.down();

            if (canFallThrough(worldIn.getBlockState(blockpos)))
            {
                double d0 = (double)((float)pos.getX() + rand.nextFloat());
                double d1 = (double)pos.getY() - 0.05D;
                double d2 = (double)((float)pos.getZ() + rand.nextFloat());
                worldIn.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(stateIn));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getDustColor(IBlockState state)
    {
        return -16777216;
    }
}
