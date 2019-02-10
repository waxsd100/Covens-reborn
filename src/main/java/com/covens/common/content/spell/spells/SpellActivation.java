

package com.covens.common.content.spell.spells;

import com.covens.common.content.spell.Spell;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class SpellActivation extends Spell {

	public SpellActivation(int cost, int color, EnumSpellType type, String name, String mod_id) {
		super(cost, color, type, name, mod_id);
	}

	@Override
	public void performEffect(RayTraceResult rtrace, EntityLivingBase caster, World world) {
		if (rtrace.typeOfHit == Type.BLOCK) {
			Block block = world.getBlockState(rtrace.getBlockPos()).getBlock();
			if (((caster == null) || (caster instanceof EntityPlayer)) && ((block instanceof BlockButton) || (block instanceof BlockLever) || (block instanceof BlockDoor) || (block instanceof BlockTrapDoor))) {
				block.onBlockActivated(world, rtrace.getBlockPos(), world.getBlockState(rtrace.getBlockPos()), (EntityPlayer) caster, EnumHand.MAIN_HAND, rtrace.sideHit, 0.5f, 0.5f, 0.5f);
			}
		}
	}

	@Override
	public boolean canBeUsed(World world, BlockPos pos, EntityLivingBase caster) {
		return true;
	}

}
