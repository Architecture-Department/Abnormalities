package architecture.abnormalities.common.entity.abnormalities

import architecture.abnormalities.core.Abnormalities
import architecture.goldenboughs_lib.client.model.GeoModelExpand
import architecture.goldenboughs_lib.client.model.entity.GeoEntityModel
import architecture.goldenboughs_lib.init.LibAttributes
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.level.Level
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.renderer.GeoEntityRenderer
import software.bernie.geckolib.util.GeckoLibUtil

class TrainingRabbits(entityType: EntityType<out TrainingRabbits>, level: Level) : Mob(entityType, level), GeoEntity {
	private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

	companion object {
		fun createAttributes(): AttributeSupplier.Builder {
			return createMobAttributes()
				.add(LibAttributes.THE_SOUL_VULNERABLE, 1.0)
				.add(LibAttributes.EROSION_VULNERABLE, 1.0)
		}
	}

	override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
	}

	override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
		return cache
	}

	class TrainingRabbitsRenderer(context: EntityRendererProvider.Context) :
		GeoEntityRenderer<TrainingRabbits>(context, GeoEntityModel(Abnormalities.modRl("training_rabbits"))) {

		override fun getTextureLocation(animatable: TrainingRabbits): ResourceLocation {
			return GeoModelExpand.texturePath(GeoEntityModel.getPath(Abnormalities.modRl("training_rabbits")))
		}
	}
}
