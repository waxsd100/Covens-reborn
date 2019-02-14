package com.covens.common.block.tiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nullable;

import com.covens.api.mp.IMagicPowerContainer;
import com.covens.common.block.BlockMod;
import com.covens.common.block.ModBlocks;
import com.covens.common.core.helper.Log;
import com.covens.common.core.statics.ModCreativeTabs;
import com.covens.common.item.ModItems;
import com.covens.common.tile.tiles.TileEntityPlacedItem;
import com.covens.common.tile.tiles.TileEntityWitchAltar;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.patchouli.common.multiblock.Multiblock;

public class BlockWitchAltar extends BlockMod implements ITileEntityProvider {

	public static final PropertyAltar ALTAR_TYPE = new PropertyAltar("altar_multiblock", AltarMultiblockType.class, Arrays.asList(AltarMultiblockType.values()));
	public static final PropertyInteger COLOR = PropertyInteger.create("color", 0, 16);

	public BlockWitchAltar(String id, Material material) {
		super(id, material);
		this.setHardness(2);
		this.setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ALTAR_TYPE, AltarMultiblockType.UNFORMED).withProperty(COLOR, 16));
	}

	private static boolean isMBBase(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock().equals(ModBlocks.witch_altar) && world.getBlockState(pos).getValue(ALTAR_TYPE).equals(AltarMultiblockType.UNFORMED);
	}

	@SuppressWarnings("deprecation")
	public static void notifyAround(BlockPos pos, World world) {
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				BlockPos bp = pos.add(dx, 0, dy);
				if (world.getBlockState(bp).getBlock().equals(ModBlocks.witch_altar)) {
					world.notifyBlockUpdate(bp, world.getBlockState(bp), world.getBlockState(bp).getBlock().getActualState(world.getBlockState(bp), world, bp), 11);
				}
			}
		}
	}

	@Nullable
	public static BlockPos getAltarTileFromMultiblock(IBlockAccess world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() != ModBlocks.witch_altar) {
			return null;
		}
		if (state.getBlock().hasTileEntity(state)) {
			return pos;
		} else if (state.getValue(ALTAR_TYPE).equals(AltarMultiblockType.CORNER)) {
			for (EnumFacing h : EnumFacing.HORIZONTALS) {
				IBlockState stateAdj = world.getBlockState(pos.offset(h));
				if (stateAdj.getBlock().equals(ModBlocks.witch_altar) && (stateAdj.getValue(ALTAR_TYPE).equals(AltarMultiblockType.SIDE) || stateAdj.getValue(ALTAR_TYPE).equals(AltarMultiblockType.TILE))) {
					return getAltarTileFromMultiblock(world, pos.offset(h));
				}
			}
		} else if (state.getValue(ALTAR_TYPE).equals(AltarMultiblockType.SIDE)) {
			for (EnumFacing h : EnumFacing.HORIZONTALS) {
				IBlockState stateAdj = world.getBlockState(pos.offset(h));
				if (stateAdj.getBlock().equals(ModBlocks.witch_altar) && stateAdj.getValue(ALTAR_TYPE).equals(AltarMultiblockType.TILE)) {
					return pos.offset(h);
				}
			}
		}
		return null;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return state.getValue(ALTAR_TYPE).equals(AltarMultiblockType.TILE);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return meta == 2 ? new TileEntityWitchAltar() : null;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return !this.hasFormedBlocksAround(worldIn, pos.north(), pos.south(), pos.east(), pos.west(), pos.west().north(), pos.west().south(), pos.east().north(), pos.east().south());
	}

	private boolean hasFormedBlocksAround(World world, BlockPos... pos) {
		for (BlockPos p : pos) {
			IBlockState bs = world.getBlockState(p);
			if (bs.getBlock().equals(ModBlocks.witch_altar) && !bs.getValue(ALTAR_TYPE).equals(AltarMultiblockType.UNFORMED)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ALTAR_TYPE, COLOR);
	}

	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ALTAR_TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ALTAR_TYPE, AltarMultiblockType.values()[meta]);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (worldIn instanceof Multiblock) {
			return state.withProperty(COLOR, (int) (System.currentTimeMillis() / 1000) % 16);
		}
		if (state.getValue(ALTAR_TYPE) != AltarMultiblockType.UNFORMED) {
			TileEntityWitchAltar tea = (TileEntityWitchAltar) worldIn.getTileEntity(getAltarTileFromMultiblock(worldIn, pos));
			return state.withProperty(COLOR, tea.getColor().ordinal());
		}
		return state;
	}

	private boolean tryFormMultiblockFromCorner(World worldIn, BlockPos firstCorner, BlockPos secondCorner) {
		ArrayList<BlockPos> blocks = new ArrayList<BlockPos>(6);

		int y = firstCorner.getY();
		int sx = Math.min(firstCorner.getX(), secondCorner.getX());
		int ex = Math.max(firstCorner.getX(), secondCorner.getX());
		int sy = Math.min(firstCorner.getZ(), secondCorner.getZ());
		int ey = Math.max(firstCorner.getZ(), secondCorner.getZ());

		for (int i = sx - 1; i <= (ex + 1); i++) {
			for (int j = sy - 1; j <= (ey + 1); j++) { // -1 +1 to check the border too, it needs to be a non-witch_altar block,
														// doesn't matter meta
				BlockPos checked = new BlockPos(i, y, j);
				if (!isMBBase(worldIn, checked) && (i >= sx) && (i <= ex) && (j >= sy) && (j <= ey)) {
					return false; // If something in the witch_altar area is not an unformed witch_altar, abort
				}
				blocks.add(checked);
				if (worldIn.getBlockState(checked).getBlock().equals(ModBlocks.witch_altar) && ((i < sx) || (i > ex) || (j < sy) || (j > ey))) {
					return false; // At least one other witch_altar block is touching the multiblock you're trying
									// to form
				}
			}
		}

		if ((ex - sx) < (ey - sy)) {
			worldIn.setBlockState(new BlockPos(sx, y, sy + 1), ModBlocks.witch_altar.getDefaultState().withProperty(BlockWitchAltar.ALTAR_TYPE, BlockWitchAltar.AltarMultiblockType.TILE));
			worldIn.setBlockState(new BlockPos(ex, y, sy + 1), ModBlocks.witch_altar.getDefaultState().withProperty(BlockWitchAltar.ALTAR_TYPE, BlockWitchAltar.AltarMultiblockType.SIDE));
		} else {
			worldIn.setBlockState(new BlockPos(sx + 1, y, sy), ModBlocks.witch_altar.getDefaultState().withProperty(BlockWitchAltar.ALTAR_TYPE, BlockWitchAltar.AltarMultiblockType.TILE));
			worldIn.setBlockState(new BlockPos(sx + 1, y, ey), ModBlocks.witch_altar.getDefaultState().withProperty(BlockWitchAltar.ALTAR_TYPE, BlockWitchAltar.AltarMultiblockType.SIDE));
		}
		worldIn.setBlockState(new BlockPos(sx, y, sy), ModBlocks.witch_altar.getDefaultState().withProperty(BlockWitchAltar.ALTAR_TYPE, BlockWitchAltar.AltarMultiblockType.CORNER));
		worldIn.setBlockState(new BlockPos(sx, y, ey), ModBlocks.witch_altar.getDefaultState().withProperty(BlockWitchAltar.ALTAR_TYPE, BlockWitchAltar.AltarMultiblockType.CORNER));
		worldIn.setBlockState(new BlockPos(ex, y, sy), ModBlocks.witch_altar.getDefaultState().withProperty(BlockWitchAltar.ALTAR_TYPE, BlockWitchAltar.AltarMultiblockType.CORNER));
		worldIn.setBlockState(new BlockPos(ex, y, ey), ModBlocks.witch_altar.getDefaultState().withProperty(BlockWitchAltar.ALTAR_TYPE, BlockWitchAltar.AltarMultiblockType.CORNER));
		return true;
	}

	@Override
	public boolean requiresUpdates() {
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if ((pos.getY() == fromPos.getY()) && world.isAirBlock(fromPos) && !this.checkRecursive(world, pos, 0, new ArrayList<BlockPos>(6)) && blockIn.equals(ModBlocks.witch_altar)) {
			this.dismantleRecursive(world, pos);
		} else if ((fromPos.getY() == (pos.getY() + 1)) && (state.getValue(ALTAR_TYPE) != AltarMultiblockType.UNFORMED)) {
			// This should never cause NPEs. If it does, investigate why
			// getAltarTileFromMultiblock returned null from a formed piece of altar
			((TileEntityWitchAltar) world.getTileEntity(getAltarTileFromMultiblock(world, pos))).scheduleUpgradeCheck();
		}
	}

	private void dismantleRecursive(World world, BlockPos pos) {
		world.setBlockState(pos, this.getDefaultState());

		if (world.getBlockState(pos.north()).getBlock().equals(ModBlocks.witch_altar) && !world.getBlockState(pos.north()).getValue(ALTAR_TYPE).equals(AltarMultiblockType.UNFORMED)) {
			this.dismantleRecursive(world, pos.north());
		}
		if (world.getBlockState(pos.south()).getBlock().equals(ModBlocks.witch_altar) && !world.getBlockState(pos.south()).getValue(ALTAR_TYPE).equals(AltarMultiblockType.UNFORMED)) {
			this.dismantleRecursive(world, pos.south());
		}
		if (world.getBlockState(pos.west()).getBlock().equals(ModBlocks.witch_altar) && !world.getBlockState(pos.west()).getValue(ALTAR_TYPE).equals(AltarMultiblockType.UNFORMED)) {
			this.dismantleRecursive(world, pos.west());
		}
		if (world.getBlockState(pos.east()).getBlock().equals(ModBlocks.witch_altar) && !world.getBlockState(pos.east()).getValue(ALTAR_TYPE).equals(AltarMultiblockType.UNFORMED)) {
			this.dismantleRecursive(world, pos.east());
		}
	}

	private boolean checkRecursive(IBlockAccess world, BlockPos pos, int i, ArrayList<BlockPos> arrayList) {
		if ((i == 5) && world.getBlockState(pos).getBlock().equals(ModBlocks.witch_altar) && !world.getBlockState(pos).getValue(ALTAR_TYPE).equals(AltarMultiblockType.UNFORMED)) {
			return true;
		}
		arrayList.add(pos);
		if (!arrayList.contains(pos.north()) && world.getBlockState(pos).getBlock().equals(ModBlocks.witch_altar)) {
			return this.checkRecursive(world, pos.north(), i + 1, arrayList);
		}
		if (!arrayList.contains(pos.south()) && world.getBlockState(pos).getBlock().equals(ModBlocks.witch_altar)) {
			return this.checkRecursive(world, pos.south(), i + 1, arrayList);
		}
		if (!arrayList.contains(pos.west()) && world.getBlockState(pos).getBlock().equals(ModBlocks.witch_altar)) {
			return this.checkRecursive(world, pos.west(), i + 1, arrayList);
		}
		if (!arrayList.contains(pos.east()) && world.getBlockState(pos).getBlock().equals(ModBlocks.witch_altar)) {
			return this.checkRecursive(world, pos.east(), i + 1, arrayList);
		}
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if (!worldIn.isRemote && (playerIn.getHeldItem(hand).getItem() == Item.getItemFromBlock(Blocks.CARPET)) && (state.getValue(ALTAR_TYPE) == AltarMultiblockType.UNFORMED) && !playerIn.isSneaking()) {
			if (this.tryFormAltar(worldIn, pos)) {
				int newColor = playerIn.getHeldItem(hand).getMetadata();
				this.setColor(worldIn, pos, newColor);
				if (!playerIn.isCreative()) {
					playerIn.getHeldItem(hand).shrink(1);
				}
				return true;
			}
		}

		if (!worldIn.isRemote && worldIn.getBlockState(pos.up()).getBlock().isReplaceable(worldIn, pos.up()) && ((playerIn.getHeldItem(hand).getItem() == ModItems.athame) || (playerIn.getHeldItem(hand).getItem() == ModItems.boline) || (playerIn.getHeldItem(hand).getItem() == ModItems.silver_sword) || (playerIn.getHeldItem(hand).getItem() == ModItems.cold_iron_sword) || (playerIn.getHeldItem(hand).getItem() == ModItems.pentacle)) && (facing == EnumFacing.UP)) {
			worldIn.setBlockState(pos.up(), ModBlocks.placed_item.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(playerIn.rotationYaw)), 3);
			((TileEntityPlacedItem) worldIn.getTileEntity(pos.up())).setItem(playerIn.getHeldItem(hand).splitStack(1));
			return true;
		}

		if (hand == EnumHand.MAIN_HAND) {
			if (playerIn.getHeldItem(hand).getItem().equals(Item.getItemFromBlock(Blocks.CARPET)) && !playerIn.isSneaking()) {
				if (!state.getValue(ALTAR_TYPE).equals(AltarMultiblockType.UNFORMED)) {
					int newColor = playerIn.getHeldItem(hand).getMetadata();
					int oldColor = ((TileEntityWitchAltar) worldIn.getTileEntity(getAltarTileFromMultiblock(worldIn, pos))).getColor().ordinal();
					if (newColor != oldColor) {
						Log.i(newColor + " " + oldColor);
						this.setColor(worldIn, pos, newColor);
						if (!playerIn.isCreative()) {
							playerIn.getHeldItem(hand).shrink(1);
						}
					}
					return true;
				}
			} else if (!worldIn.isRemote && playerIn.getHeldItem(hand).isEmpty() && (worldIn.getBlockState(pos).getValue(ALTAR_TYPE) != AltarMultiblockType.UNFORMED)) {
				BlockPos tilePos = getAltarTileFromMultiblock(worldIn, pos);
				if (tilePos != null) {
					TileEntityWitchAltar tea = (TileEntityWitchAltar) worldIn.getTileEntity(tilePos);
					tea.forceFullScan();
					IMagicPowerContainer magicPoints = tea.getCapability(IMagicPowerContainer.CAPABILITY, null);
					playerIn.sendStatusMessage(new TextComponentString(magicPoints.getAmount() + "/" + magicPoints.getMaxAmount() + " (x" + tea.getCurrentGain() + ")"), true);
					return true;
				}
			}
		}
		return false;
	}

	private boolean tryFormAltar(World worldIn, BlockPos pos) {

		if (this.checkAndForm(worldIn, pos, pos.north().north().east(), pos.north().north().west(), pos.north().east().east(), pos.north().west().west(), pos.south().south().east(), pos.south().south().west(), pos.south().east().east(), pos.south().west().west())) {
			return true;
		}
		if (this.tryFormMultiblockFromCorner(worldIn, pos.east(), pos.south().west())) {
			return true;
		}
		if (this.tryFormMultiblockFromCorner(worldIn, pos.east(), pos.north().west())) {
			return true;
		}
		if (this.tryFormMultiblockFromCorner(worldIn, pos.north(), pos.south().west())) {
			return true;
		}
		if (this.tryFormMultiblockFromCorner(worldIn, pos.north(), pos.south().east())) {
			return true;
		}
		return false;
	}

	private boolean checkAndForm(World world, BlockPos currentPos, BlockPos... posCheckArray) {
		for (BlockPos posCheck : posCheckArray) {
			if (isMBBase(world, posCheck) && this.tryFormMultiblockFromCorner(world, currentPos, posCheck)) {
				return true;
			}
		}
		return false;
	}

	public void setColor(World world, BlockPos pos, int newColor) {
		if (world.getBlockState(pos).getValue(ALTAR_TYPE).equals(AltarMultiblockType.TILE)) {
			TileEntityWitchAltar tea = (TileEntityWitchAltar) world.getTileEntity(pos);
			tea.setColor(EnumDyeColor.values()[newColor]);
			notifyAround(pos, world);
		} else if (world.getBlockState(pos).getValue(ALTAR_TYPE).equals(AltarMultiblockType.CORNER)) {
			for (EnumFacing ef : EnumFacing.HORIZONTALS) {
				BlockPos bp = pos.offset(ef);
				if (world.getBlockState(bp).getBlock().equals(ModBlocks.witch_altar) && (world.getBlockState(bp).getValue(ALTAR_TYPE).equals(AltarMultiblockType.SIDE) || world.getBlockState(bp).getValue(ALTAR_TYPE).equals(AltarMultiblockType.TILE))) {
					((BlockWitchAltar) world.getBlockState(bp).getBlock()).setColor(world, bp, newColor);
					break;
				}
			}
		} else if (world.getBlockState(pos).getValue(ALTAR_TYPE).equals(AltarMultiblockType.SIDE)) {
			for (EnumFacing ef : EnumFacing.HORIZONTALS) {
				BlockPos bp = pos.offset(ef);
				if (world.getBlockState(bp).getBlock().equals(ModBlocks.witch_altar) && world.getBlockState(bp).getValue(ALTAR_TYPE).equals(AltarMultiblockType.TILE)) {
					((BlockWitchAltar) world.getBlockState(bp).getBlock()).setColor(world, bp, newColor);
					break;
				}
			}
		}
	}

	// ###########################################################################################################

	public static enum AltarMultiblockType implements IStringSerializable {
		UNFORMED, CORNER, TILE, SIDE;

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

	}

	public static class PropertyAltar extends PropertyEnum<AltarMultiblockType> {
		protected PropertyAltar(String name, Class<AltarMultiblockType> valueClass, Collection<AltarMultiblockType> allowedValues) {
			super(name, valueClass, allowedValues);
		}
	}
}
