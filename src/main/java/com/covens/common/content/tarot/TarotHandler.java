package com.covens.common.content.tarot;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.covens.api.divination.ITarot;
import com.covens.common.lib.LibMod;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class TarotHandler {

	public static final IForgeRegistry<ITarot> REGISTRY = new RegistryBuilder<ITarot>().setName(new ResourceLocation(LibMod.MOD_ID, "tarots")).setType(ITarot.class).setIDRange(0, 200).create();
	private static final int MAX_CARDS_PER_READING = 5;

	private TarotHandler() {
		// NO-OP
	}

	public static void registerTarot(ITarot tarot) {
		REGISTRY.register(tarot);
	}

	public static ArrayList<TarotInfo> getTarotsForPlayer(EntityPlayer player) {

		ArrayList<TarotInfo> res = new ArrayList<TarotInfo>(5);

		// Can't instantiate since the collected List<> might not support remove
		// operations,
		// depending on the jre, so this needs to be addAll for safety
		res.addAll(REGISTRY.getValuesCollection().parallelStream() //
				.filter(it -> it.isApplicableToPlayer(player)) //
				.map(it -> new TarotInfo(it, player)) //
				.collect(Collectors.toList()));

		// Remove random cards until we have less or equal than max per reading
		while (res.size() > MAX_CARDS_PER_READING) {
			res.remove(player.getRNG().nextInt(res.size()));
		}

		return res;
	}

	public static class TarotInfo {

		private ITarot tarot;
		private boolean reversed;
		private int number = -1;

		private TarotInfo(ITarot tarot, boolean reversed, int number) {
			this.tarot = tarot;
			this.reversed = reversed;
			if (number >= 0) {
				this.number = number;
			}
		}

		TarotInfo(ITarot tarot, EntityPlayer player) {
			this(tarot, tarot.isReversed(player), tarot.hasNumber(player) ? tarot.getNumber(player) : -1);
		}

		public static ArrayList<TarotInfo> fromBuffer(ByteBuf buf) {
			ArrayList<TarotInfo> res = new ArrayList<TarotInfo>(5);
			int size = buf.readInt();
			for (int i = 0; i < size; i++) {
				boolean reversed = buf.readBoolean();
				int num = buf.readInt();
				String name = ByteBufUtils.readUTF8String(buf);
				ITarot tarot = TarotHandler.REGISTRY.getValue(new ResourceLocation(name));
				TarotInfo ti = new TarotInfo(tarot, reversed, num);
				res.add(ti);
			}
			return res;
		}

		public boolean isReversed() {
			return this.reversed;
		}

		public boolean hasNumber() {
			return this.number >= 0;
		}

		public int getNumber() {
			return this.number;
		}

		public ResourceLocation getTexture() {
			return this.tarot.getTexture();
		}

		public String getTranslationKey() {
			return this.tarot.getTranslationKey();
		}

		public String getRegistryName() {
			return this.tarot.getRegistryName().toString();
		}

		@Override
		public String toString() {
			return this.getTranslationKey() + ", " + this.number + (this.reversed ? ", reversed" : "");
		}
	}

}
