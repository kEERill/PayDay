package com.keerill.payday.block;

import java.util.Random;

import com.keerill.payday.block.base.BlockSlabFallBase;
import com.keerill.payday.registry.BlocksRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMoneySlab extends BlockSlabFallBase
{
	public static final PropertyBool BOTTOM = PropertyBool.create("bottom");
	
	public BlockMoneySlab()
	{
		super(Material.WOOD);
		
		this.setDefaultState(
			this.getDefaultState()
				.withProperty(BOTTOM, false)
		);
		
		this.setSoundType(SoundType.WOOD);
		this.setHardness(this.isDouble() ? 1.5F : 0.75F);
		this.setHarvestLevel("shovel", 0);
	}

    @Override
    public String getUnlocalizedName(int meta) {
        return this.getUnlocalizedName();
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state.withProperty(BOTTOM, this.checkBottomBlock(worldIn, pos));
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
				.withProperty(BOTTOM, this.checkBottomBlock(worldIn, pos));
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

        if (state.getValue(BOTTOM)) {
        	meta |= 8;
        }

        return meta;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
        		.withProperty(FACING, EnumFacing.getHorizontal(meta & 7))
        		.withProperty(BOTTOM, (meta & 8) != 0)
        		.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
    }
    
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, FACING, HALF, BOTTOM);
	}
    
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) 
    {
    	return new ItemStack(Item.getItemFromBlock(BlocksRegistry.MONEY));
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	return Item.getItemFromBlock(BlocksRegistry.MONEY);
    }

    @SideOnly(Side.CLIENT)
    public int getDustColor(IBlockState state)
    {
        return -16777216;
    }
}
