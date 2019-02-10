package com.covens.common.block.natural;

import static net.minecraft.block.BlockHorizontal.FACING;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.covens.client.fx.ParticleF;
import com.covens.common.Covens;
import com.covens.common.block.ModBlocks;
import com.covens.common.core.statics.ModCreativeTabs;
import com.covens.common.core.statics.ModSounds;
import com.covens.common.item.ModItems;
import com.covens.common.lib.LibMod;
import com.google.common.collect.Lists;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zabi.minecraft.minerva.client.blockmodels.IModelRegister;
import zabi.minecraft.minerva.client.blockmodels.ModelHandler;


public class BlockBeehive extends BlockFalling implements IModelRegister {

	private static final AxisAlignedBB BOX = new AxisAlignedBB(0.18F, 0, 0.18F, 0.82F, 1, 0.82F);

	public BlockBeehive(String id) {
		super(Material.GRASS);
		this.setTranslationKey(id);
		this.setDefaultState(this.blockState.getBaseState());
		this.setRegistryName(LibMod.MOD_ID, id);
		this.setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB);
		this.setSoundType(SoundType.PLANT);
		this.setResistance(1F);
		this.setHardness(1F);
	}

	@Override
	public void onEndFalling(World world, BlockPos pos, IBlockState falling, IBlockState falling2) {
		world.destroyBlock(pos, false);
		world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(5)).forEach(elb -> {
			elb.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 1, true, true));
		});
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			this.checkFallableHive(worldIn, pos);
		}
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		player.addStat(StatList.getBlockStats(this));
		player.addExhaustion(0.005F);
		if (stack.getItem() == ModItems.boline) {
			spawnAsEntity(worldIn, pos, new ItemStack(ModBlocks.beehive));
		} else {
			ArrayList<ItemStack> drops = Lists.newArrayList(new ItemStack(ModItems.empty_honeycomb, player.getRNG().nextInt(2 + EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack))));
			this.harvesters.set(player);
			ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack), 1, false, player);
			for (ItemStack is : drops) {
				spawnAsEntity(worldIn, pos, is);
			}
			this.harvesters.set(null);
		}
	}

	private void checkFallableHive(World worldIn, BlockPos pos) {
		IBlockState above = worldIn.getBlockState(pos.up());
		if (!above.getBlock().isLeaves(above, worldIn, pos.up()) && (pos.getY() >= 0) && worldIn.isAirBlock(pos.down())) {
			if (!fallInstantly && worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32))) {
				if (!worldIn.isRemote) {
					EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldIn, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, worldIn.getBlockState(pos));
					entityfallingblock.setHurtEntities(false);
					entityfallingblock.shouldDropItem = false;
					worldIn.spawnEntity(entityfallingblock);
				}
			} else {
				IBlockState state = worldIn.getBlockState(pos);
				worldIn.setBlockToAir(pos);
				BlockPos blockpos;
				for (blockpos = pos.down(); (worldIn.isAirBlock(blockpos) || canFallThrough(worldIn.getBlockState(blockpos))) && (blockpos.getY() > 0); blockpos = blockpos.down()) {
					;
				}
				if (blockpos.getY() > 0) {
					worldIn.setBlockState(blockpos.up(), state);
				}
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		final EnumFacing facing = EnumFacing.byHorizontalIndex(meta);
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		final EnumFacing facing = state.getValue(FACING);
		return facing.getHorizontalIndex();
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOX;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public int quantityDropped(Random random) {
		return random.nextInt(5);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(10) == 0) {
			Covens.proxy.spawnParticle(ParticleF.BEE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
		}
		if (rand.nextInt(25) == 0) {
			worldIn.playSound(null, pos, ModSounds.BUZZ, SoundCategory.BLOCKS, 0.2F, 1F);
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ModItems.empty_honeycomb;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> ret = new ArrayList<ItemStack>();
		Random rand = world instanceof World ? ((World) world).rand : new Random();

		ItemStack honeycomb = new ItemStack(this.getItemDropped(state, rand, fortune), this.quantityDropped(rand), this.damageDropped(state));
		if (!honeycomb.isEmpty()) {
			ret.add(honeycomb);
		}
		return ret;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		final EnumFacing enumfacing = EnumFacing.fromAngle(placer.rotationYaw).getOpposite();
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
