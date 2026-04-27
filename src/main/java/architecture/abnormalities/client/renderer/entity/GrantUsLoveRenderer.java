package architecture.abnormalities.client.renderer.entity;

import architecture.abnormalities.common.entity.ordeals.violet.GrantUsLove;
import architecture.goldenboughs_lib.client.model.entity.ModGeoEntityModel;
import architecture.goldenboughs_lib.client.renderlayer.AutoGlowingRenderLayer;
import architecture.goldenboughs_lib.util.LibUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

// TODO 死亡之后光芒变暗
public class GrantUsLoveRenderer extends GeoEntityRenderer<GrantUsLove> {
	private static final ResourceLocation GLOWMASK_TEXTURE = ModGeoEntityModel.getTexturePath("grant_us_love_glowmask");
	private final Float[] glowmaskValue = {1f};

	public GrantUsLoveRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new ModGeoEntityModel<>("fruit_of_understanding"));
		addRenderLayer(new AutoGlowingRenderLayer<>(this, glowmaskValue));
	}

	@Override
	public void defaultRender(PoseStack poseStack, GrantUsLove animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
		super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
		this.glowmaskValue[0] = LibUtils.calculateSineCycle(0.8f, 1.2f, 1f);
	}
}
