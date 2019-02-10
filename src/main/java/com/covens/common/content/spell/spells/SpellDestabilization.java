

package com.covens.common.content.spell.spells;

import com.covens.common.content.spell.Spell;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SpellDestabilization extends Spell {

	public SpellDestabilization(int cost, int color, EnumSpellType type, String name, String mod_id) {
		super(cost, color, type, name, mod_id);
	}

	@Override
	public void performEffect(RayTraceResult rtrace, EntityLivingBase caster, World world) {
		world.newExplosion(caster, rtrace.hitVec.x, rtrace.hitVec.y + 0.5, rtrace.hitVec.z, 0.3f, false, true);
	}

	@Override
	public boolean canBeUsed(World world, BlockPos pos, EntityLivingBase caster) {
		return true;
	}

}
