package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFTinyFirefly;
import twilightforest.entity.passive.EntityTFMobileFirefly;

import java.nio.FloatBuffer;

public class RenderTFMobileFirefly<T extends EntityTFMobileFirefly> extends EntityRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("firefly-tiny.png");
	private final ModelTFTinyFirefly fireflyModel = new ModelTFTinyFirefly();

	public RenderTFMobileFirefly(EntityRendererManager manager) {
		super(manager);
	}

	private void doRenderTinyFirefly(T firefly, double x, double y, double z, float brightness, float size) {
		GlStateManager.pushMatrix();

		GlStateManager.translatef((float) x, (float) y + 0.5F, (float) z);

		// undo rotations so we can draw a billboarded firefly
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);

		GlStateManager.getFloat(GL11.GL_MODELVIEW_MATRIX, modelview);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int index = i * 4 + j;
				if (i == j) {
					modelview.put(index, 1.0f);
				} else {
					modelview.put(index, 0.0f);
				}
			}
		}

		GL11.glLoadMatrix(modelview);

		bindEntityTexture(firefly);

		GlStateManager.colorMask(true, true, true, true);

		// render the firefly glow
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableAlpha();
		GlStateManager.disableLighting();


//		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, brightness);
		fireflyModel.glow1.render(0.0625f * size);
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();

		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}


	@Override
	public void doRender(T firefly, double x, double y, double z, float yaw, float partialTicks) {
		doRenderTinyFirefly(firefly, x, y, z, firefly.getGlowBrightness(), 1.0F);
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}
