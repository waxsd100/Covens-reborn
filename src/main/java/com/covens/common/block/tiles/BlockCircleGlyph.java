package com.covens.common.block.tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.covens.api.ritual.EnumGlyphType;
import com.covens.api.state.StateProperties;
import com.covens.common.block.BlockMod;
import com.covens.common.core.statics.ModConfig;
import com.covens.common.item.ModItems;
import com.covens.common.tile.tiles.TileEntityGlyph;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEndRod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCircleGlyph extends BlockMod implements ITileEntityProvider {

	protected static final AxisAlignedBB FLAT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0025D, 1.0D);

	public BlockCircleGlyph(String id) {
		super(id, Material.GRASS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH).withProperty(StateProperties.GLYPH_TYPE, EnumGlyphType.NORMAL).withProperty(StateProperties.LETTER, 0));
		this.setHardness(5);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if (this.getStateFromMeta(meta).getValue(StateProperties.GLYPH_TYPE).equals(EnumGlyphType.GOLDEN)) {
			return new TileEntityGlyph();
		}
		return null;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (state.getValue(StateProperties.GLYPH_TYPE) == EnumGlyphType.GOLDEN) {
			((TileEntityGlyph) worldIn.getTileEntity(pos)).onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
			return true;
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState floor = worldIn.getBlockState(pos.down());
		return floor.getBlock().canPlaceTorchOnTop(floor, worldIn, pos);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		// Dont change the actual bounding box to the offset, as that's only a visual
		// thing.
		// This is used on the server
		return FLAT_AABB;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return super.getOffset(state, worldIn, pos).scale(ModConfig.CLIENT.glyphImprecision);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return state.getValue(StateProperties.GLYPH_TYPE).equals(EnumGlyphType.GOLDEN);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return new ArrayList<ItemStack>(0);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return 100;
	}

	@Override
	public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos) {
		return PathNodeType.OPEN;
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return null;
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		return super.canPlaceBlockOnSide(worldIn, pos, side) && (side == EnumFacing.UP);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState blockState, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		int color = meta & 3;
		int dir = (meta >> 2) & 3;
		return this.getDefaultState().withProperty(StateProperties.GLYPH_TYPE, EnumGlyphType.values()[color]).withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[dir]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int color = state.getValue(StateProperties.GLYPH_TYPE).ordinal();
		int dir = state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
		return (dir << 2) | color; // Bitwise that's DDCC, where DD is either 00=south, 01=... and CC is 00=normal,
									// 01=golden...
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		int letter = Math.abs(pos.getX() + (pos.getZ() * 2)) % 6;
		return state.withProperty(StateProperties.LETTER, letter);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockHorizontal.FACING, StateProperties.GLYPH_TYPE, StateProperties.LETTER);
	}

	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		double d0 = pos.getX() + 0.5D;
		double d1 = pos.getY() + 0.05D;
		double d2 = pos.getZ() + 0.5D;
		EnumParticleTypes part = this.getParticleFor(stateIn);
		if (part != null) {
			double spreadX = rand.nextGaussian() / 3;
			double spreadZ = rand.nextGaussian() / 3;
			worldIn.spawnParticle(part, d0 + spreadX, d1, d2 + spreadZ, 0.0D, 0.0D, 0.0D, new int[0]);
		}
		TileEntityGlyph te = (TileEntityGlyph) worldIn.getTileEntity(pos);
		if ((te != null) && te.hasRunningRitual()) {
			double spreadX = rand.nextGaussian() * 0.4;
			double spreadZ = rand.nextGaussian() * 0.4;
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleEndRod(worldIn, d0 + spreadX, d1, d2 + spreadZ, 0, 0.02 + (0.1 * rand.nextDouble()), 0));
			if (ModConfig.CLIENT.allGlyphParticles) {
				EnumParticleTypes p = this.getParticleFor(worldIn.getBlockState(pos.add(TileEntityGlyph.small.get(0)[0], 0, TileEntityGlyph.small.get(0)[1])));
				if (p != null) {
					for (int[] coo : TileEntityGlyph.small) {
						if (rand.nextInt(5) == 0) {
							spreadX = rand.nextGaussian() * 0.4;
							spreadZ = rand.nextGaussian() * 0.4;
							worldIn.spawnParticle(p, pos.getX() + coo[0], pos.getY(), pos.getZ() + coo[1], 0, 0.05 + (0.1 * rand.nextDouble()), 0);
						}
					}
				}
				p = this.getParticleFor(worldIn.getBlockState(pos.add(TileEntityGlyph.medium.get(0)[0], 0, TileEntityGlyph.medium.get(0)[1])));
				if (p != null) {
					for (int[] coo : TileEntityGlyph.medium) {
						if (rand.nextInt(5) == 0) {
							spreadX = rand.nextGaussian() * 0.4;
							spreadZ = rand.nextGaussian() * 0.4;
							worldIn.spawnParticle(p, pos.getX() + coo[0], pos.getY(), pos.getZ() + coo[1], 0, 0.05 + (0.1 * rand.nextDouble()), 0);
						}
					}
				}
				p = this.getParticleFor(worldIn.getBlockState(pos.add(TileEntityGlyph.big.get(0)[0], 0, TileEntityGlyph.big.get(0)[1])));
				if (p != null) {
					for (int[] coo : TileEntityGlyph.big) {
						if (rand.nextInt(5) == 0) {
							spreadX = rand.nextGaussian() * 0.4;
							spreadZ = rand.nextGaussian() * 0.4;
							worldIn.spawnParticle(p, pos.getX() + coo[0], pos.getY(), pos.getZ() + coo[1], 0, 0.05 + (0.1 * rand.nextDouble()), 0);
						}
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private EnumParticleTypes getParticleFor(IBlockState blockState) {
		if (blockState.getBlock() == this) {
			EnumGlyphType type = blockState.getValue(StateProperties.GLYPH_TYPE);
			switch (type) {
				case ENDER:
					return EnumParticleTypes.PORTAL;
				case NETHER:
					return EnumParticleTypes.FLAME;
				default:
					break;
			}
		}
		return null;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canPlaceBlockAt(worldIn, pos)) {
			worldIn.destroyBlock(pos, false);
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		int meta = state.getValue(StateProperties.GLYPH_TYPE).ordinal();
		if (meta >= 4) {
			return new ItemStack(Items.AIR);
		}
		return new ItemStack(ModItems.ritual_chalk, 1, meta);
	}

	@Override
	public void registerModel() {// No associated item

	}

	@Override
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.XZ;
	}

}
