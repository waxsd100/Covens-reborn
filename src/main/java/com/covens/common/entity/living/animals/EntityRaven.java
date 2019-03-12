package com.covens.common.entity.living.animals;

import java.util.Set;

import com.covens.common.core.statics.ModSounds;
import com.covens.common.entity.living.EntityMultiSkin;
import com.covens.common.item.ModItems;
import com.covens.common.lib.LibIngredients;
import com.covens.common.lib.LibMod;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwnerFlying;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityRaven extends EntityMultiSkin {

	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/raven");
	private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.GOLD_NUGGET, ModItems.silver_nugget);
	// private static final String[] names = {"Huginn", "Muninn", "Morrigan",
	// "Bhusunda", "Pallas", "Qrow", "Nevermore", "Corvus", "Apollo", "Odin",
	// "Badhbh", "Bran", "Crowe", "Scarecrow", "Santa Caws", "Valravn", "Cain",
	// "Mabel", "Grip", "Harbinger", "Shani", "Diablo", "Raven", "Charlie",
	// "Unidan", "Yatagarasu", "Samjokgo", "Ischys"}; //I'm trash lmao
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityRaven.class, DataSerializers.VARINT);

	public EntityRaven(World worldIn) {
		super(worldIn);
		this.setSize(0.4f, 0.4f);
		this.moveHelper = new EntityFlyHelperRaven(this);

	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(1);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(7.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.85d);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(TINT, 0xFFFFFF);
		this.aiSit = new EntityAISit(this);
	}

	@Override
	protected void setupTamedAI() {
		this.tasks.addTask(5, new EntityAIFollowOwnerFlying(this, 2, 10, 2));
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAIPanic(this, 0.5D));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, new EntityAIMate(this, 0.8d));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWanderAvoidWaterFlying(this, 1));
	}

	@Override
	protected PathNavigate createNavigator(World worldIn) {
		PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
		pathnavigateflying.setCanOpenDoors(false);
		pathnavigateflying.setCanFloat(false);
		pathnavigateflying.setCanEnterDoors(true);
		return pathnavigateflying;
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		if (!this.isTamed() && TAME_ITEMS.contains(itemstack.getItem())) {
			if (!player.capabilities.isCreativeMode) {
				itemstack.shrink(1);
			}

			if (!this.isSilent()) {
				this.world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PARROT_EAT, this.getSoundCategory(), 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
			}

			if (!this.world.isRemote) {
				if ((this.rand.nextInt(10) == 0) && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
					this.setTamedBy(player);
					this.playTameEffect(true);
					this.world.setEntityState(this, (byte) 7);
				} else {
					this.playTameEffect(false);
					this.world.setEntityState(this, (byte) 6);
				}
			}
			return true;
		}
		return true;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityRaven(this.world);
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		// NO-OP
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.RAVEN_CRY;
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
		// NO-OP
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return LibIngredients.anySeed.apply(stack);
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		if (otherAnimal == this) {
			return false;
		} else if (!this.isTamed()) {
			return false;
		} else if (!(otherAnimal instanceof EntityRaven)) {
			return false;
		} else {
			EntityRaven raven = (EntityRaven) otherAnimal;

			if (!raven.isTamed()) {
				return false;
			} else if (raven.isSitting()) {
				return false;
			} else {
				return this.isInLove() && raven.isInLove();
			}
		}
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor(this.posX);
		int j = MathHelper.floor(this.getEntityBoundingBox().minY);
		int k = MathHelper.floor(this.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		Block block = this.world.getBlockState(blockpos.down()).getBlock();
		return (block instanceof BlockLeaves) || (block == Blocks.GRASS) || (block instanceof BlockLog) || ((block == Blocks.AIR) && (this.world.getLight(blockpos) > 8) && super.getCanSpawnHere());
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
		if (!entityIn.equals(this.getOwner())) {
			super.collideWithEntity(entityIn);
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		}
		if (this.aiSit != null) {
			this.aiSit.setSitting(false);
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public int getSkinTypes() {
		return 1;
	}

	public class EntityFlyHelperRaven extends EntityFlyHelper {
		
		public EntityFlyHelperRaven(EntityLiving p_i47418_1_) {
			super(p_i47418_1_);
		}

		@Override
		public void onUpdateMoveHelper() {
			if (this.action == EntityMoveHelper.Action.MOVE_TO) {
				this.action = EntityMoveHelper.Action.WAIT;
				this.entity.setNoGravity(true);
				double dx = this.posX - this.entity.posX;
				double dy = this.posY - this.entity.posY;
				double dz = this.posZ - this.entity.posZ;
				double distSq = (dx * dx) + (dy * dy) + (dz * dz);

				if (distSq < 2.500000277905201E-7D) {
					this.entity.setMoveVertical(0.0F);
					this.entity.setMoveForward(0.0F);
					return;
				}

				float maxYaw = (float) (MathHelper.atan2(dz, dx) * (180D / Math.PI)) - 90.0F;
				this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, maxYaw, 10.0F);
				float moveSpeed;
				float groundHorizontalMoveSpeed = (float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				float flyingHorizontalMoveSpeed = (float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue());
				if (this.entity.onGround) {
					moveSpeed = groundHorizontalMoveSpeed / 10;
				} else {
					moveSpeed = flyingHorizontalMoveSpeed;
				}

				this.entity.setAIMoveSpeed(moveSpeed);
				double horizontalDistSq = MathHelper.sqrt((dx * dx) + (dz * dz));
				float pitchMax = (float) (-(MathHelper.atan2(dy, horizontalDistSq) * (180D / Math.PI)));
				this.entity.rotationPitch = this.limitAngle(this.entity.rotationPitch, pitchMax, 10.0F);
				this.entity.setMoveVertical(dy > 0.0D ? flyingHorizontalMoveSpeed : -flyingHorizontalMoveSpeed);
			} else {
				this.entity.setNoGravity(false);
				this.entity.setMoveVertical(0.0F);
				this.entity.setMoveForward(0.0F);
			}
		}
	}

}
