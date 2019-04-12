package com.covens.common.content.cauldron.brews;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.covens.common.content.cauldron.BrewMod;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class PotionArrowDeflection extends BrewMod {

	private final Field fieldInGround;
	private final Method methodGetArrowStack;

	public PotionArrowDeflection() {
		super("arrow_deflection", false, 0xFFFACD, false, 2400, 20);
		try {
			this.fieldInGround = ReflectionHelper.findField(EntityArrow.class, "inGround", "field_70254_i");
			this.methodGetArrowStack = ReflectionHelper.findMethod(EntityArrow.class, "getArrowStack", "func_184550_j");
		} catch (Exception e) {
			throw new LoaderException("Covens cannot find some essential forge data, please report this to the Covens Github page", e);
		}
		this.setIconIndex(4, 0);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		entity.world.getEntitiesWithinAABB(EntityArrow.class, entity.getEntityBoundingBox().grow(amplifier, amplifier, amplifier)).parallelStream().filter(a -> a.shootingEntity != entity).filter(a -> !this.isInGround(a)).forEach(a -> {
			if (!entity.world.isRemote && (a.pickupStatus == PickupStatus.ALLOWED)) {
				ItemStack arrow;
				try {
					arrow = (ItemStack) this.methodGetArrowStack.invoke(a);
					EntityItem ei = new EntityItem(entity.world, a.posX, a.posY, a.posZ, arrow);
					entity.world.spawnEntity(ei);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}

			}
			a.setDead();
		});
	}

	private boolean isInGround(EntityArrow a) {
		try {
			return (boolean) this.fieldInGround.get(a);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

}
