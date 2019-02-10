

package com.covens.common.content.spell.spells;

import com.covens.common.content.spell.Spell;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SpellLesserBlinking extends Spell {

	public SpellLesserBlinking(int cost, int color, EnumSpellType type, String name, String mod_id) {
		super(cost, color, type, name, mod_id);
	}

	@Override
	public void performEffect(RayTraceResult rtrace, EntityLivingBase caster, World world) {
		if (caster != null) {
			BlockPos dest = new BlockPos(caster.getPositionVector().add(caster.getLookVec().scale(2).add(0, 1, 0)));
			if (!world.getBlockState(dest).causesSuffocation()) {
				caster.setPositionAndUpdate(dest.getX(), dest.getY(), dest.getZ());
			}
		}
	}

	@Override
	public boolean canBeUsed(World world, BlockPos pos, EntityLivingBase caster) {
		return true;
	}

}
