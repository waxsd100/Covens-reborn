/**
 * This class was created by <ZaBi94> on Dec 10th, 2017.
 * It's distributed as part of Covens under
 * the MIT license.
 */

package com.covens.common.content.spell;

import com.covens.api.spell.ISpell;
import com.covens.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public abstract class Spell implements ISpell {

	public static final IForgeRegistry<ISpell> SPELL_REGISTRY = new RegistryBuilder<ISpell>().setName(new ResourceLocation(LibMod.MOD_ID, "spells")).setType(ISpell.class).setIDRange(0, 255).create();

	private int color, cost;
	private String name;
	private ISpell.EnumSpellType type;
	private ResourceLocation regName;

	public Spell(int cost, int color, ISpell.EnumSpellType type, String name, String mod_id) {
		this.color = color;
		this.name = name;
		this.type = type;
		this.cost = cost;
		this.setRegistryName(new ResourceLocation(mod_id, name));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public EnumSpellType getType() {
		return type;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return regName;
	}

	@Override
	public Class<ISpell> getRegistryType() {
		return ISpell.class;
	}

	@Override
	public ISpell setRegistryName(ResourceLocation nameIn) {
		regName = nameIn;
		return this;
	}

}
