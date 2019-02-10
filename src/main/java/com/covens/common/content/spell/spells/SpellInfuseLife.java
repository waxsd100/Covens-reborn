

package com.covens.common.content.spell.spells;

import com.covens.common.content.spell.Spell;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class SpellInfuseLife extends Spell {

	public SpellInfuseLife(int cost, int color, EnumSpellType type, String name, String mod_id) {
		super(cost, color, type, name, mod_id);
	}

	@Override
	public void performEffect(RayTraceResult rtrace, EntityLivingBase caster, World world) {
		if ((caster != null) && (rtrace.typeOfHit == Type.ENTITY) && (rtrace.entityHit instanceof EntityLivingBase)) {
			caster.attackEntityFrom(DamageSource.causeIndirectMagicDamage(caster, null), 1f);
			((EntityLivingBase) rtrace.entityHit).heal(1f);
		}
	}

	@Override
	public boolean canBeUsed(World world, BlockPos pos, EntityLivingBase caster) {
		return caster != null;
	}

}
