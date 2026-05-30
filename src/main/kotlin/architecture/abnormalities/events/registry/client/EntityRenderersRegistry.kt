package architecture.abnormalities.events.registry.client

import architecture.abnormalities.client.renderer.entity.FruitOfUnderstandingRenderer
import architecture.abnormalities.client.renderer.entity.GrantUsLoveRenderer
import architecture.abnormalities.core.AbnormalitiesConstants
import architecture.abnormalities.init.entity.OrdealsEntityTypes
import architecture.abnormalities.init.entity.ProjectileEntityTypes
import architecture.goldenboughs_lib.client.renderer.EmptyLivingEntityRenderer
import architecture.goldenboughs_lib.client.renderer.EmptyMobRenderer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.EntityRenderersEvent

@EventBusSubscriber(modid = AbnormalitiesConstants.ID, value = [Dist.CLIENT])
object EntityRenderersRegistry {
	@SubscribeEvent
	fun registry(event: EntityRenderersEvent.RegisterRenderers) {
		event.registerEntityRenderer(OrdealsEntityTypes.FRUIT_OF_UNDERSTANDING.get(), ::FruitOfUnderstandingRenderer)
		event.registerEntityRenderer(
			ProjectileEntityTypes.FRUIT_OF_UNDERSTANDING_BULLET.get(),
			FruitOfUnderstandingRenderer::FruitBulletRenderer
		)
		event.registerEntityRenderer(OrdealsEntityTypes.GRANT_US_LOVE.get(), ::GrantUsLoveRenderer)
	}

	private fun registerEmptyMobRenderer(
		event: EntityRenderersEvent.RegisterRenderers,
		entityType: EntityType<out Mob>,
		shadowRadius: Float,
		texture: ResourceLocation
	) {
		event.registerEntityRenderer(entityType) { context -> EmptyMobRenderer(context, shadowRadius, texture) }
	}

	private fun registerEmptyLivingEntityRenderer(
		event: EntityRenderersEvent.RegisterRenderers,
		entityType: EntityType<out LivingEntity>,
		shadowRadius: Float,
		texture: ResourceLocation
	) {
		event.registerEntityRenderer(entityType) { context -> EmptyLivingEntityRenderer(context, shadowRadius, texture) }
	}
}
