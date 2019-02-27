package com.covens.common.content.cauldron.brews;

import java.util.HashMap;
import java.util.Map;

import com.covens.api.cauldron.DefaultModifiers;
import com.covens.api.cauldron.IBrewModifierList;
import com.covens.common.block.ModBlocks;
import com.covens.common.content.cauldron.BrewMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlazedTerracotta;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

/**
 * Created by Joseph on 4/14/2018.
 */
public class PotionFireWorld extends BrewMod {

	private final Map<Block, IBlockState> stateMap = new HashMap<>();

	public PotionFireWorld() {
		super("fireworld", true, 0xED2939, true, 0, 40);
		this.stateMap.put(Blocks.GRASS_PATH, Blocks.RED_NETHER_BRICK.getDefaultState());
		this.stateMap.put(Blocks.GRAVEL, Blocks.SOUL_SAND.getDefaultState());
		this.stateMap.put(Blocks.COBBLESTONE, Blocks.NETHERRACK.getDefaultState());
		this.stateMap.put(Blocks.PLANKS, Blocks.NETHER_BRICK.getDefaultState());
		this.stateMap.put(Blocks.OAK_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		this.stateMap.put(Blocks.SPRUCE_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		this.stateMap.put(Blocks.ACACIA_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		this.stateMap.put(Blocks.JUNGLE_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		this.stateMap.put(Blocks.BIRCH_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		this.stateMap.put(Blocks.DARK_OAK_FENCE, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		this.stateMap.put(Blocks.PACKED_ICE, Blocks.MAGMA.getDefaultState());
		this.stateMap.put(Blocks.ICE, Blocks.MAGMA.getDefaultState());
		this.stateMap.put(Blocks.WOOL, Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED));
		this.stateMap.put(Blocks.FARMLAND, Blocks.SOUL_SAND.getDefaultState());
		this.stateMap.put(Blocks.DIRT, Blocks.NETHERRACK.getDefaultState());
		this.stateMap.put(Blocks.GRASS, Blocks.NETHERRACK.getDefaultState());
		this.stateMap.put(Blocks.IRON_BLOCK, ModBlocks.nethersteel.getDefaultState());
		this.stateMap.put(Blocks.GLASS_PANE, Blocks.STAINED_GLASS_PANE.getDefaultState().withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.RED));
		this.stateMap.put(Blocks.STAINED_GLASS_PANE, Blocks.STAINED_GLASS_PANE.getDefaultState().withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.RED));
		this.stateMap.put(Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED));
		this.stateMap.put(Blocks.STAINED_HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED));
		this.stateMap.put(Blocks.WATER, Blocks.LAVA.getDefaultState());
		this.stateMap.put(Blocks.GLASS, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.RED));
		this.stateMap.put(Blocks.STAINED_GLASS, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.RED));
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		entity.setFire((1 + amplifier) * 2);
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 2 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);

		BlockPos posI = pos.add(box, box / 2, box);
		BlockPos posF = pos.add(-box, -box / 2, -box);

		Iterable<MutableBlockPos> spots = BlockPos.getAllInBoxMutable(posI, posF);
		for (BlockPos spot : spots) {
			if (spot.distanceSq(pos) < (2 + ((box * box) / 2))) {
				IBlockState state = world.getBlockState(spot);
				if (world.rand.nextInt(4) <= (modifiers.getLevel(DefaultModifiers.POWER).orElse(0) / 2)) {
					if (BlockStairs.isBlockStairs(state)) {
						IBlockState newState = Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF));
						world.setBlockState(spot, newState);
					} else if (state.getBlock() instanceof BlockFence) {
						world.setBlockState(spot, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 3);
					} else if (state.getBlock() instanceof BlockLog) {
						EnumFacing.Axis bone_axis = EnumFacing.Axis.Y;
						BlockLog.EnumAxis log_axis = state.getValue(BlockLog.LOG_AXIS);
						if (log_axis == EnumAxis.X) {
							bone_axis = Axis.X;
						} else if (log_axis == EnumAxis.Z) {
							bone_axis = Axis.Z;
						}
						world.setBlockState(spot, Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, bone_axis), 3);
					} else if (this.stateMap.containsKey(state.getBlock())) {
						world.setBlockState(spot, this.stateMap.get(state.getBlock()), 3);
					} else if (state.getMaterial() == Material.SNOW) {
						world.setBlockToAir(spot);
					} else if (state.getBlock() == Blocks.WATER) {
						world.setBlockState(spot, Blocks.LAVA.getDefaultState(), 3);
					} else if (state.getBlock() == Blocks.FLOWING_WATER) {
						world.setBlockState(spot, Blocks.FLOWING_LAVA.getDefaultState().withProperty(BlockLiquid.LEVEL, state.getValue(BlockLiquid.LEVEL)), 2);
					} else if (state.getBlock() instanceof BlockLeaves) {
						world.setBlockState(spot, Blocks.NETHER_WART_BLOCK.getDefaultState(), 3);
					} else if (state.getBlock() instanceof BlockGlazedTerracotta) {
						world.setBlockState(spot, Blocks.RED_GLAZED_TERRACOTTA.getDefaultState().withProperty(BlockHorizontal.FACING, state.getValue(BlockHorizontal.FACING)), 3);
					}

					if ((state.getBlock() != Blocks.NETHERRACK) && (world.getBlockState(spot).getBlock() == Blocks.NETHERRACK) && world.isAirBlock(spot.up()) && (world.rand.nextInt(10) == 0)) {
						world.setBlockState(spot.up(), Blocks.FIRE.getDefaultState(), 3);
					}

				}
			}
		}
	}
}
