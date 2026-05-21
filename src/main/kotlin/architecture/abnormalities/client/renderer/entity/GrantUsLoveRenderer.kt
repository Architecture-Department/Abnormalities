package architecture.abnormalities.client.renderer.entity

import architecture.abnormalities.common.entity.ordeals.violet.GrantUsLove
import architecture.abnormalities.core.Abnormalities
import architecture.goldenboughs_lib.client.model.entity.GeoEntityModel
import architecture.goldenboughs_lib.client.renderlayer.AutoGlowingRenderLayer
import architecture.goldenboughs_lib.util.LibUtils
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRendererProvider
import software.bernie.geckolib.renderer.GeoEntityRenderer

// TODO 死亡之后光芒变暗
class GrantUsLoveRenderer(renderManager: EntityRendererProvider.Context) :
	GeoEntityRenderer<GrantUsLove>(renderManager, GeoEntityModel(Abnormalities.modRl("fruit_of_understanding"))) {

	private val glowmaskValue = arrayOf(1f)

	init {
		addRenderLayer(AutoGlowingRenderLayer(this, glowmaskValue))
	}

	override fun defaultRender(
		poseStack: PoseStack,
		animatable: GrantUsLove,
		bufferSource: MultiBufferSource,
		renderType: RenderType?,
		buffer: VertexConsumer?,
		yaw: Float,
		partialTick: Float,
		packedLight: Int
	) {
		super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight)
		this.glowmaskValue[0] = LibUtils.calculateSineCycle(0.8f, 1.2f, 1f)
	}
}
