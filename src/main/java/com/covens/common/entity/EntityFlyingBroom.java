package com.covens.common.entity;

import java.lang.reflect.Field;
import java.util.Arrays;

import javax.annotation.Nullable;

import com.covens.api.mp.MPContainer;
import com.covens.common.Covens;
import com.covens.common.item.ModItems;
import com.covens.common.lib.LibReflection;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zabi.minecraft.minerva.common.utils.DimensionalPosition;

@Mod.EventBusSubscriber
public class EntityFlyingBroom extends Entity {

	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityFlyingBroom.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FUEL = EntityDataManager.<Integer>createKey(EntityFlyingBroom.class, DataSerializers.VARINT); // ONLY SYNCHRONIZED WHEN EMPTY OR FULL
	private static final int MAX_FUEL = 100;
	Field isJumping = LibReflection.field("isJumping", "field_70703_bu", EntityLivingBase.class);
	private DimensionalPosition orig_position;

	public EntityFlyingBroom(World world) {
		super(world);
		this.setSize(1f, 1);
	}

	public EntityFlyingBroom(World world, double x, double y, double z, int type) {
		this(world);
		this.setPosition(x, y, z);
		this.setType(type);
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
	}

	@SubscribeEvent(receiveCanceled = false, priority = EventPriority.LOWEST)
	public static void onUnmounting(EntityMountEvent evt) {
		if ((evt.getEntityBeingMounted() instanceof EntityFlyingBroom) && evt.isDismounting()) {
			EntityFlyingBroom broom = (EntityFlyingBroom) evt.getEntityBeingMounted();
			EntityPlayer source = (EntityPlayer) evt.getEntityMounting();
			if (!source.isDead) {
				if (broom.getType() == 3) {
					BlockPos start = broom.getMountPos();
					if (start == null || broom.world.provider.getDimension() == broom.getMountDim()) {
						broom.setPositionAndUpdate(start.getX(), start.getY(), start.getZ());
						source.setPositionAndUpdate(start.getX(), start.getY(), start.getZ());
					}
				}
				if (!broom.world.isRemote) { // TODO check for owl
					EntityItem ei = new EntityItem(broom.world, source.posX, source.posY, source.posZ, new ItemStack(ModItems.broom, 1, broom.getType()));
					broom.world.spawnEntity(ei);
					broom.setDead();
					ei.onCollideWithPlayer(source);
				}
			}
		}
	}

	@Override
	public double getMountedYOffset() {
		return 0.4;
	}

	private BlockPos getMountPos() {
		return this.orig_position == null ? null : this.orig_position.getPosition();
	}

	private int getMountDim() {
		return this.orig_position == null ? 0 : this.orig_position.getDim();
	}

	public void setMountPos(BlockPos pos, int dim) {
		this.orig_position = new DimensionalPosition(pos, dim);
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(TYPE, 0);
		this.getDataManager().register(FUEL, 0);
		this.setEntityBoundingBox(new AxisAlignedBB(this.getPosition()).contract(0, 1, 0));
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return entityIn instanceof EntityPlayer;
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		this.doBlockCollisions();
		int broomType = this.getType();
		this.applyFriction(broomType);
		if (this.isBeingRidden()) {
			this.updateFromRider(broomType);
		} else {
			if (!this.collidedVertically) {
				this.motionY -= 0.009;
				if (this.motionY < -0.5) {
					this.motionY = -0.5;
				}
			}
		}
		this.handleCollisions();
		this.setSizeAndMove();
	}

	private void setSizeAndMove() {
		if (this.isBeingRidden()) {
			this.setSize(1f, 2f);// If a player is riding, account for the height of the player
		}
		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		if (this.isBeingRidden()) {
			this.setSize(1f, 1f);
		}
	}

	private void handleCollisions() {
		if (this.collidedHorizontally) {
			if (this.prevPosX == this.posX) {
				this.motionX = 0;
			}
			if (this.prevPosZ == this.posZ) {
				this.motionZ = 0;
			}
		}
		if (this.collidedVertically && (this.prevPosY == this.posY)) {
			this.motionY = 0;
		}
	}

	private void applyFriction(int broomType) {
		float friction = broomType == 0 ? 0.99f : 0.98f;
		if (this.onGround) {
			friction = 0.8f;
		}
		this.motionX *= friction;
		this.motionY *= friction;
		this.motionZ *= friction;
	}

	private void updateFromRider(int broomType) {
		EntityPlayer rider = (EntityPlayer) this.getControllingPassenger();
		if (rider == null) {
			Covens.logger.warn(this + " is being ridden by a null rider!");
			return;
		}
		if (this.getDataManager().get(FUEL) < 5) {
			this.refuel(rider);
		}
		float front = rider.moveForward, strafe = rider.moveStrafing, up = 0;
		try {
			up = this.isJumping.getBoolean(rider) ? 1 : 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vec3d look = rider.getLookVec();
		if (broomType == 1) {
			this.handleElderMovement(front, up, strafe, look);
			this.motionY -= 0.005;
		} else if (broomType == 2) {
			this.handleJuniperMovement(front, up, strafe, look);
		} else if (broomType == 3) {
			this.handleYewMovement(front, look);
		} else if (broomType == 4) {
			this.handleCypressMovement(front, look);
		}
		this.setRotationYawHead(rider.rotationYaw);
	}

	private void refuel(EntityPlayer rider) {
		MPContainer pmp = rider.getCapability(MPContainer.CAPABILITY, null);
		if (pmp.drain(30)) {
			this.getDataManager().set(FUEL, MAX_FUEL);
			this.getDataManager().setDirty(FUEL);
		}
	}

	private void handleCypressMovement(float front, Vec3d look) {
		this.handleMundaneMovement(front, look);
	}

	private void handleYewMovement(float front, Vec3d look) {
		if (this.getDataManager().get(FUEL) > 1) {
			this.getDataManager().set(FUEL, this.getDataManager().get(FUEL) - 2);
			if (this.getDataManager().get(FUEL) < 5) {
				this.getDataManager().setDirty(FUEL);
			}
			this.handleMundaneMovement(front, look);
		}
	}

	private void handleJuniperMovement(float front, float up, float strafe, Vec3d look) {
		if (this.getDataManager().get(FUEL) > 0) {
			this.getDataManager().set(FUEL, this.getDataManager().get(FUEL) - 1);
			if (this.getDataManager().get(FUEL) < 5) {
				this.getDataManager().setDirty(FUEL);
			}
			if (front >= 0) {
				Vec3d horAxis = look.crossProduct(new Vec3d(0, 1, 0)).normalize().scale(-strafe / 10);
				this.motionX += (front * (horAxis.x + look.x)) / 80;
				this.motionZ += (front * (horAxis.z + look.z)) / 80;
				this.motionY += ((up / 80) + ((front * (horAxis.y + look.y)) / 80));

				if (((this.motionX * this.motionX) + (this.motionY * this.motionY) + (this.motionZ * this.motionZ)) > 1) {
					Vec3d limit = new Vec3d(this.motionX, this.motionY, this.motionZ).normalize().scale(2);
					this.motionX = limit.x;
					this.motionY = limit.y;
					this.motionZ = limit.z;
				}
			} else {
				this.motionX /= 1.1;
				this.motionY /= 1.1;
				this.motionZ /= 1.1;
			}
		} else {
			this.motionY -= 0.002;
		}
	}

	private void handleElderMovement(float front, float up, float strafe, Vec3d look) {
		if (this.getDataManager().get(FUEL) > 0) {
			this.getDataManager().set(FUEL, this.getDataManager().get(FUEL) - 1);
			if (this.getDataManager().get(FUEL) < 5) {
				this.getDataManager().setDirty(FUEL);
			}
			Vec3d horAxis = look.crossProduct(new Vec3d(0, 1, 0)).normalize().scale(-strafe / 10);
			this.motionX += (front * (horAxis.x + look.x)) / 20;
			this.motionZ += (front * (horAxis.z + look.z)) / 20;
			this.motionY += (up / 60) - 0.005;

			if (((this.motionX * this.motionX) + (this.motionY * this.motionY) + (this.motionZ * this.motionZ)) > 8) {
				Vec3d limit = new Vec3d(this.motionX, this.motionY, this.motionZ).normalize().scale(2);
				this.motionX = limit.x;
				this.motionY = limit.y;
				this.motionZ = limit.z;
			}
		}
	}

	@Override
	public void setRotationYawHead(float rotation) {
		this.prevRotationYaw = this.rotationYaw;
		this.rotationYaw = rotation;
	}

	private void handleMundaneMovement(float front, Vec3d look) {
		if (front >= 0) {
			this.motionX += ((0.1) * look.x) / 8;
			this.motionY += ((0.1) * look.y) / 8;
			this.motionZ += ((0.1) * look.z) / 8;
		}

		if (((this.motionX * this.motionX) + (this.motionZ * this.motionZ)) > 1) {
			Vec3d limit = new Vec3d(this.motionX, 0, this.motionZ).normalize();
			this.motionX = limit.x;
			this.motionZ = limit.z;
		}
	}

	private void setType(int type) {
		this.getDataManager().set(TYPE, type);
		this.getDataManager().setDirty(TYPE);
	}

	public int getType() {
		return this.getDataManager().get(TYPE);
	}

	@Nullable
	@Override
	public Entity getControllingPassenger() {
		if (this.getPassengers().size() == 0) {
			return null;
		}
		return this.getPassengers().get(0);
	}

	@Override
	public boolean canPassengerSteer() {
		return true;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		this.setLocationAndAngles(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"), tag.getFloat("yaw"), tag.getFloat("pitch"));
		this.setType(tag.getInteger("type"));
		if (tag.hasKey("orig")) {
			this.orig_position = new DimensionalPosition(tag.getCompoundTag("orig"));
		}
		this.getDataManager().set(FUEL, tag.getInteger("fuel"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setDouble("x", this.posX);
		compound.setDouble("y", this.posY);
		compound.setDouble("z", this.posZ);
		compound.setFloat("yaw", this.rotationYaw);
		compound.setFloat("pitch", this.rotationPitch);
		compound.setInteger("type", this.getType());
		if (this.orig_position != null) {
			compound.setTag("orig", this.orig_position.writeToNBT());
		}
		compound.setInteger("fuel", this.getDataManager().get(FUEL));
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		}
		if (this.getControllingPassenger() != null) {
			return false;
		}
		if (!this.world.isRemote && (source.getTrueSource() instanceof EntityPlayer)) {
			EntityItem ei = new EntityItem(this.world, source.getTrueSource().posX, source.getTrueSource().posY, source.getTrueSource().posZ, new ItemStack(ModItems.broom, 1, this.getType()));
			this.world.spawnEntity(ei);
			this.setDead();
			ei.onCollideWithPlayer((EntityPlayer) source.getTrueSource());
			return true;
		}
		return false;
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		if (!player.isRiding() && !player.isSneaking()) {
			player.rotationYaw = this.rotationYaw;
			player.rotationPitch = this.rotationPitch;
			player.startRiding(this);
			if (this.getType() == 3) {
				this.setMountPos(player.getPosition(), player.world.provider.getDimension());
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return Arrays.asList(ItemStack.EMPTY);
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
		// NO-OP
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
		// No fall
	}
}
