package com.covens.api.ritual;

import net.minecraft.util.IStringSerializable;

public enum EnumGlyphType implements IStringSerializable {

	NORMAL(0), GOLDEN(-1), ENDER(2), NETHER(3), ANY(1);

	private int meta;
	
	EnumGlyphType(int metadata) {
		this.meta = metadata;
	}

	public static EnumGlyphType fromMeta(int meta) {
		if ((meta == 1) || (meta == 5)) {
			return ANY;
		}
		return values()[meta];
	}

	public int meta() {
		return this.meta;
	}

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

}
