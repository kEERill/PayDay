package com.keerill.payday.blocks;

import java.util.Random;

import com.keerill.payday.PayDayMinecraft;
import com.keerill.payday.blocks.base.BlockHorizontal;
import com.keerill.payday.blocks.base.BlockTileEntity;
import com.keerill.payday.blocks.tile.TileEntityDrill;
import com.keerill.payday.gui.GuiDrill;
import com.keerill.payday.interfaces.IBlockStorageDrillable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDrill extends BlockTileEntity<TileEntityDrill>
{
	public static final PropertyEnum<BlockDrill.EnumState> STATE = PropertyEnum.create("state", BlockDrill.EnumState.class);
	
	private static final AxisAlignedBB AXIS_ALIGNED_BB = new AxisAlignedBB(0.1D, 0.0D, 0.0D, 0.7D, 0.8D, 1.0D);
	private static final AxisAlignedBB AXIS_ALIGNED_BB_EAST = new AxisAlignedBB(0.0D, 0.0D, 0.3D, 1.0D, 0.8D, 0.9D);
	private static final AxisAlignedBB AXIS_ALIGNED_BB_NORTH = new AxisAlignedBB(0.3D, 0.0D, 0.0D, 0.9D, 0.8D, 1.0D);
	private static final AxisAlignedBB AXIS_ALIGNED_BB_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.1D, 1.0D, 0.8D, 0.7D);

	public BlockDrill(String name) 
	{
		super(name, Material.IRON);
		
		this.setDefaultState(
			this.getDefaultState()
				.withProperty(STATE, BlockDrill.EnumState.NOTWORKER)
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
		if (!worldIn.isRemote) 
		{
			playerIn.openGui(PayDayMinecraft.instance, GuiDrill.ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
	
	/**
	 * —обытие срабатывает, когда дрель начинает работу
	 * 
	 * @param worldIn
	 * @param pos
	 * @param state
	 */
	public void onDrillStarted(World worldIn, BlockPos pos, IBlockState state)
	{
		if (state.getBlock() instanceof BlockDrill)
		{
			worldIn.setBlockState(pos, state.withProperty(STATE, BlockDrill.EnumState.WORKER));
		}
	}
	
	/**
	 * —обытие срабатывает, когда дрель прекращает работу
	 * 
	 * @param worldIn
	 * @param pos
	 * @param state
	 */
	public void onDrillStopped(World worldIn, BlockPos pos, IBlockState state)
	{
		if (state.getBlock() instanceof BlockDrill)
		{
			worldIn.setBlockState(pos, state.withProperty(STATE, BlockDrill.EnumState.NOTWORKER));
		}
	}
	
	/**
	 * —обытие вызываетс€, когда дрель заканчивает свою работу
	 * 
	 * @param worldIn
	 * @param pos
	 * @param state
	 */
	public void onDrillEnded(World worldIn, BlockPos pos, IBlockState state)
	{
		IBlockState iBlockState = this.getFacingFrontBlockState(worldIn, pos, state);
		
		if (iBlockState.getBlock() instanceof IBlockStorageDrillable)
		{
			IBlockStorageDrillable iBlockStorageDrillable = (IBlockStorageDrillable) iBlockState.getBlock();
			iBlockStorageDrillable.onOpened(worldIn, this.getFacingFrontPosition(pos, state), iBlockState);
		}
	}
	
	/**
	 * ѕровер€ет, стоит ли перед ним блок, который имеет
	 * интерфейс IDrillable. ≈сли блок имеет интерфейс IDrillable и работа дрели не начата,
	 * то следует начать работу. ≈сли же блок не наследует интерфейс IDrillable то завершить работу, если работа уже начата
	 * 
	 * @param worldIn
	 * @param pos
	 * @param state
	 */
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
				return;
			}
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, STATE });
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return super.getMetaFromState(state) | (state.getValue(STATE).getMetadata() << 3);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return super.getStateFromMeta(meta).withProperty(STATE, BlockDrill.EnumState.byMetadata(meta >> 3));
	}
	
	/**
	 * ¬озвращает BlockState, который находитс€ перед этим блоком
	 * 
	 * @param worldIn
	 * @param pos
	 * @param state
	 * @return IBlockState
	 */
	private IBlockState getFacingFrontBlockState(World worldIn, BlockPos pos, IBlockState state)
	{
		return worldIn.getBlockState(this.getFacingFrontPosition(pos, state));
	}

	/**
	 * ¬озвращает координаты блоке перед этим блоком
	 * 
	 * @param pos
	 * @param state
	 * @return
	 */
	private BlockPos getFacingFrontPosition(BlockPos pos, IBlockState state) 
	{
		return pos.offset(state.getValue(BlockHorizontal.FACING), 1);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) 
	{
		if(stateIn.getValue(STATE) == BlockDrill.EnumState.WORKER)
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
	
	public static enum EnumState implements IStringSerializable
	{
		WORKER(0), 
		NOTWORKER(1);
		
		private static final BlockDrill.EnumState[] META_LOOKUP = new BlockDrill.EnumState[values().length];

		private int meta;
		
		EnumState(int meta) 
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
		
		
		public static BlockDrill.EnumState byMetadata(int index)
		{
			if (index < 0 || index > META_LOOKUP.length)
			{
				index = 0;
			}
			
			return META_LOOKUP[index];
		}
		
       static
	   {
	        for (BlockDrill.EnumState state : values())
	        {
	            META_LOOKUP[state.getMetadata()] = state;
	        }
	   }
	}
}
