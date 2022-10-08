package com.keerill.payday.block;

import java.util.Random;

import com.keerill.payday.PayDay;
import com.keerill.payday.block.base.BlockHorizontal;
import com.keerill.payday.block.base.BlockTileEntity;
import com.keerill.payday.tileentity.TileEntityDrill;
import com.keerill.payday.block.state.EnumToolState;
import com.keerill.payday.gui.GuiDrill;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDrill extends BlockTileEntity<TileEntityDrill>
{
	public static final PropertyEnum<EnumToolState> STATE = PropertyEnum.create("state", EnumToolState.class);
	
	private static final AxisAlignedBB AXIS_ALIGNED_BB = new AxisAlignedBB(0.1D, 0.0D, 0.0D, 0.7D, 0.8D, 1.0D);
	private static final AxisAlignedBB AXIS_ALIGNED_BB_EAST = new AxisAlignedBB(0.0D, 0.0D, 0.3D, 1.0D, 0.8D, 0.9D);
	private static final AxisAlignedBB AXIS_ALIGNED_BB_NORTH = new AxisAlignedBB(0.3D, 0.0D, 0.0D, 0.9D, 0.8D, 1.0D);
	private static final AxisAlignedBB AXIS_ALIGNED_BB_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.1D, 1.0D, 0.8D, 0.7D);

	public BlockDrill()
	{
		super(Material.IRON);
		
		this.setDefaultState(
			this.getDefaultState()
				.withProperty(STATE, EnumToolState.NOT_WORK)
		);
	}

	@Override
	public Class<TileEntityDrill> getTileEntityClass() 
	{
		return TileEntityDrill.class;
	}

	@Override
	public TileEntityDrill createTileEntity(World world, IBlockState state) 
	{
		return new TileEntityDrill();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		switch (state.getValue(FACING))
		{
			case WEST:
				return AXIS_ALIGNED_BB_WEST;
			case NORTH:
				return AXIS_ALIGNED_BB_NORTH;
			case EAST:
				return AXIS_ALIGNED_BB_EAST;
			default:
				return AXIS_ALIGNED_BB;
		}
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) 
	{
		this.updatingWorkingDrill(worldIn, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) 
	{
		this.updatingWorkingDrill(worldIn, pos, state);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if (!worldIn.isRemote) {
			playerIn.openGui(PayDay.instance, GuiDrill.ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}

	public void onDrillStarted(World worldIn, BlockPos pos, IBlockState state)
	{
		if (state.getBlock() instanceof BlockDrill) {
			worldIn.setBlockState(pos, state.withProperty(STATE, EnumToolState.WORK));
		}
	}

	public void onDrillStopped(World worldIn, BlockPos pos, IBlockState state)
	{
		if (state.getBlock() instanceof BlockDrill) {
			worldIn.setBlockState(pos, state.withProperty(STATE, EnumToolState.NOT_WORK));
		}
	}

	public void onDrillEnded(World worldIn, BlockPos pos, IBlockState state)
	{
		IBlockState iBlockState = this.getFacingFrontBlockState(worldIn, pos, state);
		
		if (iBlockState.getBlock() instanceof IBlockStorageDrillable) {
			IBlockStorageDrillable iBlockStorageDrillable = (IBlockStorageDrillable) iBlockState.getBlock();
			iBlockStorageDrillable.onOpened(worldIn, this.getFacingFrontPosition(pos, state), iBlockState);
		}
	}

	public void updatingWorkingDrill(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		if (tileEntity instanceof TileEntityDrill) 
		{
			TileEntityDrill tileEntityDrill = (TileEntityDrill) tileEntity;
			IBlockState iBlockStateDrillable = this.getFacingFrontBlockState(worldIn, pos, state);
			
			if (iBlockStateDrillable.getBlock() instanceof IBlockStorageDrillable)
			{
				IBlockStorageDrillable iBlockStorageDrillable = (IBlockStorageDrillable) iBlockStateDrillable.getBlock();
				
				if (iBlockStorageDrillable.canHackingPositionDrill(worldIn, this.getFacingFrontPosition(pos, state), iBlockStateDrillable, state))
				{
					if (iBlockStorageDrillable.canHacking(worldIn, pos, iBlockStateDrillable) && !tileEntityDrill.isWork())
					{
						tileEntityDrill.startWork(iBlockStorageDrillable.getHackingSeconds());
						worldIn.notifyBlockUpdate(pos, state, state, 3);
					}
					
					return;
				}
			}
			
			if (tileEntityDrill.isWork()) 
			{
				tileEntityDrill.stopWork();
				worldIn.notifyBlockUpdate(pos, state, state, 3);
			}
		}
	}
	
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
		return super.getStateFromMeta(meta).withProperty(STATE, EnumToolState.byMetadata(meta >> 3));
	}

	private IBlockState getFacingFrontBlockState(World worldIn, BlockPos pos, IBlockState state)
	{
		return worldIn.getBlockState(this.getFacingFrontPosition(pos, state));
	}

	private BlockPos getFacingFrontPosition(BlockPos pos, IBlockState state) 
	{
		return pos.offset(state.getValue(BlockHorizontal.FACING), 1);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) 
	{
		if(stateIn.getValue(STATE) == EnumToolState.WORK)
		{
	    	switch (stateIn.getValue(FACING))
			{
				case WEST:
					for (int i1 = 0; i1 < 2; ++i1)
			        {
			            double d6 = (double) pos.getX() + 0.1;
			            double d11 = (double) pos.getY() + 0.6;
			            double d16 = (double) pos.getZ() + 0.5;
			            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d6, d11, d16, 0.0D, 0.0D, 0.0D, new int[0]);
			        }
					break;
					
				case NORTH:
					for (int i1 = 0; i1 < 2; ++i1)
			        {
			            double d6 = (double) pos.getX() + 0.5;
			            double d11 = (double) pos.getY() + 0.6;
			            double d16 = (double) pos.getZ() + 0.1;
			            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d6, d11, d16, 0.0D, 0.0D, 0.0D, new int[0]);
			        }
					break;
					
				case EAST:
					for (int i1 = 0; i1 < 2; ++i1)
			        {
			            double d6 = (double) pos.getX() + 0.9;
			            double d11 = (double) pos.getY() + 0.6;
			            double d16 = (double) pos.getZ() + 0.5;
			            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d6, d11, d16, 0.0D, 0.0D, 0.0D, new int[0]);
			        }
					break;
					
				default:
					for (int i1 = 0; i1 < 2; ++i1)
			        {
			            double d6 = (double) pos.getX() + 0.5;
			            double d11 = (double) pos.getY() + 0.6;
			            double d16 = (double) pos.getZ() + 0.9;
			            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d6, d11, d16, 0.0D, 0.0D, 0.0D, new int[0]);
			        }
					break;
			}
		}
	}
}
