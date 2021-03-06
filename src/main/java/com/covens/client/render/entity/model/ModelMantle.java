package com.covens.client.render.entity.model;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import com.covens.common.lib.LibMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Mantle - Ingoleth Created using Tabula 5.1.0
 */
public class ModelMantle extends ModelBiped {

	public static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/models/mantle.png");

	public ModelRenderer hood;
	public ModelRenderer cape;
	public ModelRenderer hoodBack;
	public ModelRenderer hoodRight1;
	public ModelRenderer hoodTop2;
	public ModelRenderer hoodLeft1;
	public ModelRenderer hoodRight2;
	public ModelRenderer hoodLeft2;
	public ModelRenderer hoodTop1;
	public ModelRenderer armLeft;
	public ModelRenderer armRight;
	public ModelRenderer capeBack1;
	public ModelRenderer shoulderLeft;
	public ModelRenderer shoulderRight;
	public ModelRenderer CapeBack2;
	public ModelRenderer capeRight1;
	public ModelRenderer capeLeft1;
	public ModelRenderer CapeBackRag1;
	public ModelRenderer CapeBackRag2;
	public ModelRenderer CapeRightDown1;
	public ModelRenderer capeRight2;
	public ModelRenderer CapeRightDownRag1;
	public ModelRenderer CapeRightDownRag2;
	public ModelRenderer CapeRightDown2;
	public ModelRenderer capeRightFront1;
	public ModelRenderer CapeRightDown2Rag1;
	public ModelRenderer CapeRightDown2Rag2;
	public ModelRenderer CapeLeftDown1;
	public ModelRenderer capeLeft2;
	public ModelRenderer CapeLeftDownRag1;
	public ModelRenderer CapeLeftDownRag2;
	public ModelRenderer capeLeftDown2;
	public ModelRenderer capeLeftFront1;
	public ModelRenderer capeLeftDown2Rag2;
	public ModelRenderer capeLeftDown2Rag3;

	public ModelPlayer playerModel;
	private HashMap<String, Float> valuesMap = new HashMap<>();

	public ModelMantle(ModelPlayer model) {
		this.playerModel = model;
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.CapeBackRag2 = new ModelRenderer(this, 11, 24);
		this.CapeBackRag2.setRotationPoint(-3.0F, 3.0F, 0.0F);
		this.CapeBackRag2.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(this.CapeBackRag2, 0.3839724354387525F, 0.0F, 0.0F);
		this.armLeft = new ModelRenderer(this, 40, 16);
		this.armLeft.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.armLeft.addBox(-3.0F, -2.0F, -2.0F, 0, 0, 0, 0.0F);
		this.CapeBackRag1 = new ModelRenderer(this, 1, 18);
		this.CapeBackRag1.setRotationPoint(-2.0F, 0.0F, 0.0F);
		this.CapeBackRag1.addBox(-4.0F, 0.0F, 0.0F, 5, 3, 1, 0.0F);
		this.setRotateAngle(this.CapeBackRag1, 0.10471975511965977F, 0.0F, 0.0F);
		this.CapeRightDown2Rag2 = new ModelRenderer(this, 26, 23);
		this.CapeRightDown2Rag2.setRotationPoint(1.0099999904632568F, 1.9984774589538574F, -0.009999609552323818F);
		this.CapeRightDown2Rag2.addBox(0.0F, 0.0F, -1.0F, 2, 3, 1, 0.0F);
		this.setRotateAngle(this.CapeRightDown2Rag2, 0.0F, 0.0F, -0.27925274689811996F);
		this.capeBack1 = new ModelRenderer(this, 1, 1);
		this.capeBack1.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.capeBack1.addBox(-4.0F, 0.0F, -1.0F, 8, 15, 1, 0.0F);
		this.setRotateAngle(this.capeBack1, 0.08726646259971647F, 0.0F, 0.0F);
		this.capeRight1 = new ModelRenderer(this, 48, 1);
		this.capeRight1.setRotationPoint(4.0F, 0.0F, 0.0F);
		this.capeRight1.addBox(-5.0F, 0.0F, 0.0F, 5, 15, 1, 0.0F);
		this.setRotateAngle(this.capeRight1, 0.06981317007977318F, 3.141592653589793F, 0.0F);
		this.capeRight2 = new ModelRenderer(this, 33, 1);
		this.capeRight2.setRotationPoint(-5.0F, 0.0F, 1.0F);
		this.capeRight2.addBox(-6.0F, 0.0F, -1.0F, 6, 10, 1, 0.0F);
		this.setRotateAngle(this.capeRight2, 0.03490658503988659F, 1.5707963267948966F, 0.0F);
		this.CapeLeftDownRag2 = new ModelRenderer(this, 45, 37);
		this.CapeLeftDownRag2.setRotationPoint(3.0F, 1.9996954202651978F, -0.009999999776482582F);
		this.CapeLeftDownRag2.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.setRotateAngle(this.CapeLeftDownRag2, 0.5235987755982988F, 0.0F, 0.0F);
		this.shoulderRight = new ModelRenderer(this, 1, 29);
		this.shoulderRight.mirror = true;
		this.shoulderRight.setRotationPoint(-1.0F, -3.0F, 0.1F);
		this.shoulderRight.addBox(0.0F, -1.0F, -3.0F, 6, 6, 6, 0.0F);
		this.setRotateAngle(this.shoulderRight, 0.0F, 0.0F, 0.12217304763960307F);
		this.capeLeftDown2 = new ModelRenderer(this, 33, 23);
		this.capeLeftDown2.setRotationPoint(-1.0F, 10.0F, 0.0F);
		this.capeLeftDown2.addBox(-5.0F, 0.0F, 0.0F, 6, 3, 1, 0.0F);
		this.setRotateAngle(this.capeLeftDown2, 0.19198621771937624F, 0.0F, 0.0F);
		this.CapeRightDown1 = new ModelRenderer(this, 48, 21);
		this.CapeRightDown1.setRotationPoint(0.0F, 15.0F, 1.0F);
		this.CapeRightDown1.addBox(-5.0F, 0.0F, -1.0F, 5, 1, 1, 0.0F);
		this.setRotateAngle(this.CapeRightDown1, -0.19198621771937624F, 0.0F, 0.0F);
		this.capeLeft2 = new ModelRenderer(this, 33, 1);
		this.capeLeft2.setRotationPoint(-5.0F, 0.0F, -1.0F);
		this.capeLeft2.addBox(-6.0F, 0.0F, 0.0F, 6, 10, 1, 0.0F);
		this.setRotateAngle(this.capeLeft2, -0.03490658503988659F, -1.5707963267948966F, 0.0F);
		this.armRight = new ModelRenderer(this, 40, 16);
		this.armRight.mirror = true;
		this.armRight.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.armRight.addBox(-1.0F, -2.0F, -2.0F, 0, 0, 0, 0.0F);
		this.capeLeftDown2Rag2 = new ModelRenderer(this, 48, 18);
		this.capeLeftDown2Rag2.setRotationPoint(-4.929999828338623F, 3.0199999809265137F, 0.010000316426157951F);
		this.capeLeftDown2Rag2.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
		this.setRotateAngle(this.capeLeftDown2Rag2, 0.12217304763960307F, 0.0F, -0.10471975511965977F);
		this.CapeRightDown2Rag1 = new ModelRenderer(this, 33, 19);
		this.CapeRightDown2Rag1.setRotationPoint(-5.010000228881836F, 2.0011839866638184F, 0.009999999776482582F);
		this.CapeRightDown2Rag1.addBox(0.0F, 0.0F, -1.0F, 4, 2, 1, 0.0F);
		this.setRotateAngle(this.CapeRightDown2Rag1, 0.0F, 0.0F, -0.2792526803190927F);
		this.CapeRightDownRag1 = new ModelRenderer(this, 52, 24);
		this.CapeRightDownRag1.setRotationPoint(1.0F, 1.004679560661316F, 0.009999999776482582F);
		this.CapeRightDownRag1.addBox(-5.0F, 0.0F, -1.0F, 3, 3, 1, 0.0F);
		this.setRotateAngle(this.CapeRightDownRag1, -0.22689280275926282F, 0.0F, 0.0F);
		this.CapeRightDown2 = new ModelRenderer(this, 33, 23);
		this.CapeRightDown2.setRotationPoint(-1.0F, 10.0F, 0.0F);
		this.CapeRightDown2.addBox(-5.0F, 0.0F, -1.0F, 6, 2, 1, 0.0F);
		this.setRotateAngle(this.CapeRightDown2, -0.19198621771937624F, 0.0F, 0.0F);
		this.capeLeftFront1 = new ModelRenderer(this, 48, 1);
		this.capeLeftFront1.setRotationPoint(-3.5F, -0.009999999776482582F, -1.5F);
		this.capeLeftFront1.addBox(-2.5F, 0.0F, 2.299999952316284F, 5, 5, 1, 0.0F);
		this.setRotateAngle(this.capeLeftFront1, 0.0F, -1.5707963267948966F, 0.0F);
		this.shoulderLeft = new ModelRenderer(this, 1, 29);
		this.shoulderLeft.mirror = true;
		this.shoulderLeft.setRotationPoint(1.0F, -3.0F, 0.1F);
		this.shoulderLeft.addBox(0.0F, -1.0F, -3.0F, 6, 6, 6, 0.0F);
		this.setRotateAngle(this.shoulderLeft, 3.141592653589793F, 0.0F, 3.01941960595019F);
		this.CapeLeftDownRag1 = new ModelRenderer(this, 52, 33);
		this.CapeLeftDownRag1.setRotationPoint(-3.5F, 2.0051915645599365F, 0.003066617762669921F);
		this.CapeLeftDownRag1.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1, 0.0F);
		this.setRotateAngle(this.CapeLeftDownRag1, 0.3490658503988659F, 0.0F, 0.0F);
		this.CapeLeftDown1 = new ModelRenderer(this, 48, 29);
		this.CapeLeftDown1.setRotationPoint(0.0F, 15.0F, -1.0F);
		this.CapeLeftDown1.addBox(-5.0F, 0.0F, 0.0F, 5, 2, 1, 0.0F);
		this.setRotateAngle(this.CapeLeftDown1, 0.19198621771937624F, 0.0F, 0.0F);
		this.capeRightFront1 = new ModelRenderer(this, 48, 1);
		this.capeRightFront1.setRotationPoint(-3.5F, 0.009999999776482582F, 1.4900000095367432F);
		this.capeRightFront1.addBox(-2.5F, 0.0F, -3.299999952316284F, 5, 5, 1, 0.0F);
		this.setRotateAngle(this.capeRightFront1, 0.0F, 1.5707963267948966F, 0.0F);
		this.capeLeftDown2Rag3 = new ModelRenderer(this, 19, 22);
		this.capeLeftDown2Rag3.setRotationPoint(1.9800000190734863F, 1.0F, 0.009999999776482582F);
		this.capeLeftDown2Rag3.addBox(0.0F, 0.0F, 0.0F, 2, 4, 1, 0.0F);
		this.setRotateAngle(this.capeLeftDown2Rag3, 0.0F, 0.0F, -0.3490658503988659F);
		this.cape = new ModelRenderer(this, 36, 42);
		this.cape.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.cape.addBox(-4.01F, -1.0F, 1.5F, 8, 6, 3, 0.0F);
		this.capeLeft1 = new ModelRenderer(this, 48, 1);
		this.capeLeft1.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.capeLeft1.addBox(-5.0F, 0.0F, -1.0F, 5, 15, 1, 0.0F);
		this.setRotateAngle(this.capeLeft1, -0.06981317007977318F, 0.0F, 0.0F);
		this.CapeRightDownRag2 = new ModelRenderer(this, 45, 34);
		this.CapeRightDownRag2.setRotationPoint(0.0F, 2.9995431900024414F, 0.0F);
		this.CapeRightDownRag2.addBox(-5.0F, 0.0F, -1.0F, 2, 1, 1, 0.0F);
		this.setRotateAngle(this.CapeRightDownRag2, -0.19198621771937624F, 0.0F, 0.0F);
		this.CapeBack2 = new ModelRenderer(this, 2, 23);
		this.CapeBack2.setRotationPoint(2.0F, 15.0F, -1.0F);
		this.CapeBack2.addBox(-1.0F, 0.0F, 0.0F, 3, 5, 1, 0.0F);
		this.setRotateAngle(this.CapeBack2, 0.19198621771937624F, 0.0F, 0.0F);
		this.CapeBackRag1.addChild(this.CapeBackRag2);
		this.cape.addChild(this.armLeft);
		this.CapeBack2.addChild(this.CapeBackRag1);
		this.CapeRightDown2Rag1.addChild(this.CapeRightDown2Rag2);
		this.cape.addChild(this.capeBack1);
		this.capeBack1.addChild(this.capeRight1);
		this.capeRight1.addChild(this.capeRight2);
		this.CapeLeftDownRag1.addChild(this.CapeLeftDownRag2);
		this.armRight.addChild(this.shoulderRight);
		this.capeLeft2.addChild(this.capeLeftDown2);
		this.capeRight1.addChild(this.CapeRightDown1);
		this.capeLeft1.addChild(this.capeLeft2);
		this.cape.addChild(this.armRight);
		this.capeLeftDown2.addChild(this.capeLeftDown2Rag2);
		this.CapeRightDown2.addChild(this.CapeRightDown2Rag1);
		this.CapeRightDown1.addChild(this.CapeRightDownRag1);
		this.capeRight2.addChild(this.CapeRightDown2);
		this.capeLeft2.addChild(this.capeLeftFront1);
		this.armLeft.addChild(this.shoulderLeft);
		this.CapeLeftDown1.addChild(this.CapeLeftDownRag1);
		this.capeLeft1.addChild(this.CapeLeftDown1);
		this.capeRight2.addChild(this.capeRightFront1);
		this.capeLeftDown2Rag2.addChild(this.capeLeftDown2Rag3);
		this.capeBack1.addChild(this.capeLeft1);
		this.CapeRightDownRag1.addChild(this.CapeRightDownRag2);
		this.capeBack1.addChild(this.CapeBack2);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		float rotationFactor = (limbSwingAmount < 0.05) ? -3 * this.bipedLeftArm.rotateAngleZ : (-3 * this.bipedLeftArm.rotateAngleZ) + (limbSwingAmount * 0.65F);
		if (limbSwingAmount > 0.05) {
			this.cape.rotateAngleX = this.getAndUpdateRotation((EntityPlayer) entityIn, limbSwingAmount);
			this.capeRightFront1.rotateAngleY = 1.57079632679F - (limbSwingAmount * 1.5F);
			this.capeLeftFront1.rotateAngleY = -this.capeRightFront1.rotateAngleY;
		} else {
			this.capeBack1.rotateAngleX = rotationFactor / 5;
			this.CapeBack2.rotateAngleX = rotationFactor / 5;
			this.CapeBackRag1.rotateAngleX = rotationFactor;
			this.CapeBackRag2.rotateAngleX = rotationFactor;
			this.CapeLeftDown1.rotateAngleX = rotationFactor;
			this.CapeLeftDownRag1.rotateAngleX = rotationFactor;
			this.CapeLeftDownRag2.rotateAngleX = rotationFactor;
			this.CapeRightDown1.rotateAngleX = -rotationFactor;
			this.CapeRightDownRag1.rotateAngleX = -rotationFactor;
			this.CapeRightDownRag2.rotateAngleX = -rotationFactor;
			this.capeLeftDown2.rotateAngleX = rotationFactor;
			this.capeLeftDown2Rag2.rotateAngleX = -rotationFactor;
			this.capeLeftDown2Rag3.rotateAngleX = rotationFactor;
			this.CapeRightDown2.rotateAngleX = -rotationFactor;
			this.CapeRightDown2Rag2.rotateAngleX = rotationFactor;
		}

		/*
		 * if (entityIn.isSneaking()) { this.capeLeftFront1.rotateAngleY = 0;
		 * this.capeRightFront1.rotateAngleY = this.capeLeftFront1.rotateAngleY; }
		 */
		
		float alpha = 0.8F - rotationFactor; //This should not depend on rotationFactor, but by the amount of ticks the player has been stationary
		//TODO create client-only capability that keeps track of that amount
		
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
		this.cape.render(1);
		GlStateManager.disableBlend();
	}

	private float getAndUpdateRotation(EntityPlayer entity, float limbSwingAmount) {
		String key = entity.getUniqueID().toString();
		if (!this.valuesMap.containsKey(key)) {
			this.valuesMap.put(key, 0f);
		}
		float currentRotation = this.valuesMap.get(key);
		if (entity.moveForward > 0) {
			currentRotation = limbSwingAmount;
		} else {
			currentRotation = limbSwingAmount / 1.65F;
		}
		this.valuesMap.put(key, currentRotation);
		return currentRotation;
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
