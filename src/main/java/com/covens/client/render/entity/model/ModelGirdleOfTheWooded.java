package com.covens.client.render.entity.model;

import com.covens.common.item.equipment.baubles.ItemGirdleOfTheWooded;
import com.covens.common.lib.LibMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGirdleOfTheWooded extends ModelBase {

	public static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/models/girdle_of_the_wooded.png");

    public ModelRenderer belt;
    public ModelRenderer barkFront01;
    public ModelRenderer barkFront02;
    public ModelRenderer barkBack02;
    public ModelRenderer barkBack01;
    public ModelRenderer root01;
    public ModelRenderer root02;
    public ModelRenderer leaf02;
    public ModelRenderer leaf01;
    public ModelRenderer vine;
    public ModelRenderer barkFront03;
    public ModelRenderer barkFront04;
    public ModelRenderer leaf04;
    public ModelRenderer leaf03;
    public ModelRenderer barkBack04;
    public ModelRenderer barkBack03;

    public ModelGirdleOfTheWooded() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.leaf01 = new ModelRenderer(this, 24, 42);
        this.leaf01.setRotationPoint(3.0F, 6.0F, 2.200000047683716F);
        this.leaf01.addBox(0.0F, 0.0F, 0.0F, 0, 3, 2, 0.0F);
        this.setRotateAngle(leaf01, 0.3490658503988659F, 0.06981317007977318F, -0.15707963267948966F);
        this.barkBack04 = new ModelRenderer(this, 12, 22);
        this.barkBack04.setRotationPoint(0.12009649723768234F, 6.0041937828063965F, 1.8617606163024902F);
        this.barkBack04.addBox(0.0F, -4.0F, 0.0F, 4, 4, 1, 0.0F);
        this.setRotateAngle(barkBack04, -0.10428736594955018F, 0.20939650018771477F, 0.03739541726948492F);
        this.barkBack02 = new ModelRenderer(this, 2, 28);
        this.barkBack02.setRotationPoint(0.0F, 10.0F, 2.0F);
        this.barkBack02.addBox(0.0F, -4.0F, 0.0F, 4, 4, 1, 0.0F);
        this.setRotateAngle(barkBack02, 0.03490658503988659F, 0.13962634015954636F, 0.03490658503988659F);
        this.barkFront02 = new ModelRenderer(this, 13, 28);
        this.barkFront02.setRotationPoint(0.0F, 10.0F, -3.0F);
        this.barkFront02.addBox(-4.0F, -4.0F, 0.0F, 4, 4, 1, 0.0F);
        this.setRotateAngle(barkFront02, -0.03490658503988659F, 0.17453292519943295F, 0.0F);
        this.belt = new ModelRenderer(this, 2, 3);
        this.belt.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.belt.addBox(-4.0F, 0.0F, -2.0F, 8, 2, 4, 0.01F);
        this.root02 = new ModelRenderer(this, 13, 34);
        this.root02.setRotationPoint(-3.0F, 4.0F, 2.0F);
        this.root02.addBox(0.0F, -2.0F, 0.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(root02, 0.3490658503988659F, -0.4363323129985824F, 0.0F);
        this.barkBack01 = new ModelRenderer(this, 13, 16);
        this.barkBack01.setRotationPoint(0.0F, 10.0F, 2.0F);
        this.barkBack01.addBox(-4.0F, -4.0F, 0.0F, 4, 4, 1, 0.0F);
        this.setRotateAngle(barkBack01, 0.06981317007977318F, -0.05235987755982988F, 0.03490658503988659F);
        this.barkFront01 = new ModelRenderer(this, 2, 10);
        this.barkFront01.setRotationPoint(0.0F, 10.0F, -3.0F);
        this.barkFront01.addBox(0.0F, -4.0F, 0.0F, 4, 4, 1, 0.0F);
        this.setRotateAngle(barkFront01, -0.017453292519943295F, -0.17453292519943295F, -0.03490658503988659F);
        this.leaf02 = new ModelRenderer(this, 1, 48);
        this.leaf02.setRotationPoint(-2.0F, 7.0F, 2.5999999046325684F);
        this.leaf02.addBox(0.0F, 0.0F, 0.0F, 0, 3, 2, 0.0F);
        this.setRotateAngle(leaf02, -0.08726646259971647F, 0.2617993877991494F, 0.3490658503988659F);
        this.leaf04 = new ModelRenderer(this, 6, 48);
        this.leaf04.setRotationPoint(-3.0971789360046387F, 1.752339243888855F, 4.574602127075195F);
        this.leaf04.addBox(0.0F, -1.399999976158142F, 0.0F, 0, 2, 3, 0.0F);
        this.setRotateAngle(leaf04, -0.785398096818421F, -0.43633234628809603F, 0.0F);
        this.barkFront03 = new ModelRenderer(this, 13, 10);
        this.barkFront03.setRotationPoint(-0.15169166028499603F, 6.0034685134887695F, -2.931251049041748F);
        this.barkFront03.addBox(0.0F, -4.0F, 0.0F, 4, 4, 1, 0.0F);
        this.setRotateAngle(barkFront03, 0.05563898619815241F, -0.052650028960594016F, -0.05438139575833576F);
        this.leaf03 = new ModelRenderer(this, 17, 42);
        this.leaf03.setRotationPoint(2.953157663345337F, 2.0342469215393066F, 3.8223583698272705F);
        this.leaf03.addBox(0.0F, 0.0F, 0.0F, 0, 2, 3, 0.0F);
        this.setRotateAngle(leaf03, 0.1662546886880034F, 0.7276389930435918F, 0.1420108014415488F);
        this.vine = new ModelRenderer(this, 2, 40);
        this.vine.setRotationPoint(-3.5F, 3.0999999046325684F, -3.0F);
        this.vine.addBox(0.0F, 0.0F, 0.0F, 7, 7, 0, 0.0F);
        this.root01 = new ModelRenderer(this, 2, 34);
        this.root01.setRotationPoint(1.0F, 4.0F, 2.0F);
        this.root01.addBox(0.0F, -2.0F, 0.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(root01, 0.3490658503988659F, 0.4363323129985824F, 0.0F);
        this.barkFront04 = new ModelRenderer(this, 2, 16);
        this.barkFront04.setRotationPoint(-0.02782178483903408F, 5.991966724395752F, -3.157785177230835F);
        this.barkFront04.addBox(-4.0F, -4.0F, 0.0F, 4, 4, 1, 0.0F);
        this.setRotateAngle(barkFront04, 0.12217304763960307F, 0.17453292519943295F, 0.0F);
        this.barkBack03 = new ModelRenderer(this, 2, 22);
        this.barkBack03.setRotationPoint(0.15385213494300842F, 6.012684345245361F, 1.7213565111160278F);
        this.barkBack03.addBox(-4.0F, -4.0F, 0.0F, 4, 4, 1, 0.0F);
        this.setRotateAngle(barkBack03, -0.033176090011282924F, -0.03248503091732639F, 0.001290759669067236F);

	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float f5) {
		if (!(entity instanceof EntityPlayer)) {
			return;
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

		int barkPieces = ItemGirdleOfTheWooded.getBarkPieces((EntityPlayer) entity);

		// FIXME belt is still rendering weirdly
		this.belt.render(f5);

		
		if (barkPieces > 0) {
			this.barkBack01.render(1);
			this.barkFront01.render(1);
		}

		if (barkPieces > 1) {
			this.barkFront02.render(1);
			this.barkBack02.render(1);
			this.barkFront03.render(1);
			this.barkBack03.render(1);
			this.leaf02.render(1);
		}

		if (barkPieces > 2) {
			this.barkFront04.render(1);
			this.barkBack04.render(1);
			this.leaf01.render(1);

		}

		if (barkPieces > 3) {
			this.root01.render(1);
			this.root02.render(1);
			this.leaf03.render(1);
		
		}
		if(barkPieces > 4) {
		this.vine.render(1);
		this.leaf04.render(1);
		}
	}

	protected void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
