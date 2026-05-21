package architecture.abnormalities.client.renderer.entity

import architecture.abnormalities.common.entity.ordeals.violet.FruitOfUnderstanding
import architecture.abnormalities.core.Abnormalities
import architecture.goldenboughs_lib.client.model.entity.GeoEntityModel
import architecture.goldenboughs_lib.client.renderlayer.AutoGlowingRenderLayer
import architecture.goldenboughs_lib.util.calculateSineCycle
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.phys.Vec3
import software.bernie.geckolib.cache.`object`.BakedGeoModel
import software.bernie.geckolib.renderer.GeoEntityRenderer
import kotlin.math.atan2

class FruitOfUnderstandingRenderer(renderManager: EntityRendererProvider.Context) :
	GeoEntityRenderer<FruitOfUnderstanding>(
		renderManager,
		GeoEntityModel(Abnormalities.modRl("fruit_of_understanding"))
	) {

	private val glowmaskValue = arrayOf(1f)

	init {
		addRenderLayer(AutoGlowingRenderLayer(this, glowmaskValue))
	}

	override fun defaultRender(
		poseStack: PoseStack,
		animatable: FruitOfUnderstanding,
		bufferSource: MultiBufferSource,
		renderType: RenderType?,
		buffer: VertexConsumer?,
		yaw: Float,
		partialTick: Float,
		packedLight: Int
	) {
		super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight)
		this.glowmaskValue[0] = calculateSineCycle(0.8f, 1.2f, 1f)
	}

	class FruitBulletRenderer(renderManager: EntityRendererProvider.Context) :
		GeoEntityRenderer<FruitOfUnderstanding.FruitBullet>(
			renderManager,
			GeoEntityModel(Abnormalities.modRl("fruit_bullet"))
		) {

		private val glowmaskValue = arrayOf(1f)

		init {
			addRenderLayer(AutoGlowingRenderLayer(this, glowmaskValue))
		}

		override fun getRenderType(
			animatable: FruitOfUnderstanding.FruitBullet,
			texture: ResourceLocation,
			bufferSource: MultiBufferSource?,
			partialTick: Float
		): RenderType? {
			return RenderType.entityTranslucentEmissive(texture)
		}

		override fun preRender(
			poseStack: PoseStack,
			animatable: FruitOfUnderstanding.FruitBullet,
			model: BakedGeoModel,
			bufferSource: MultiBufferSource?,
			buffer: VertexConsumer?,
			isReRender: Boolean,
			partialTick: Float,
			packedLight: Int,
			packedOverlay: Int,
			colour: Int
		) {
			val movement: Vec3 = animatable.deltaMovement
			if (movement.length() > 0.0001) {
				val yaw = (atan2(movement.z, movement.x) * 180.0 / Math.PI).toFloat()
				val pitch = (atan2(movement.y, movement.horizontalDistance()) * 180.0 / Math.PI).toFloat()

				poseStack.mulPose(Axis.YP.rotationDegrees(-yaw))
				poseStack.mulPose(Axis.XP.rotationDegrees(pitch))
			} else {
				poseStack.mulPose(Axis.YP.rotationDegrees(-animatable.yRot))
				poseStack.mulPose(Axis.XP.rotationDegrees(animatable.xRot))
			}
			super.preRender(
				poseStack,
				animatable,
				model,
				bufferSource,
				buffer,
				isReRender,
				partialTick,
				packedLight,
				packedOverlay,
				colour
			)
		}
	}
}
