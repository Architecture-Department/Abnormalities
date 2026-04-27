package architecture.abnormalities.core.registry.client;

import architecture.abnormalities.client.renderer.entity.FruitOfUnderstandingRenderer;
import architecture.abnormalities.client.renderer.entity.GrantUsLoveRenderer;
import architecture.abnormalities.core.Abnormalities;
import architecture.abnormalities.init.entity.OrdealsEntityTypes;
import architecture.abnormalities.init.entity.ProjectileEntityTypes;
import architecture.goldenboughs_lib.client.renderer.EmptyLivingEntityRenderer;
import architecture.goldenboughs_lib.client.renderer.EmptyMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Abnormalities.ID, value = Dist.CLIENT)
public final class EntityRenderersRegistry {
	@SubscribeEvent
	public static void registry(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(OrdealsEntityTypes.FRUIT_OF_UNDERSTANDING.get(), FruitOfUnderstandingRenderer::new);
		event.registerEntityRenderer(ProjectileEntityTypes.FRUIT_OF_UNDERSTANDING_BULLET.get(), FruitOfUnderstandingRenderer.FruitBulletRenderer::new);
		event.registerEntityRenderer(OrdealsEntityTypes.GRANT_US_LOVE.get(), GrantUsLoveRenderer::new);
	}

	private static void registerEmptyMobRenderer(EntityRenderersEvent.RegisterRenderers event, EntityType<? extends Mob> entityType, float shadowRadius, ResourceLocation texture) {
		event.registerEntityRenderer(entityType, (context) -> new EmptyMobRenderer<>(context, shadowRadius, texture));
	}

	private static void registerEmptyLivingEntityRenderer(EntityRenderersEvent.RegisterRenderers event, EntityType<? extends LivingEntity> entityType, float shadowRadius, ResourceLocation texture) {
		event.registerEntityRenderer(entityType, (context) -> new EmptyLivingEntityRenderer<>(context, shadowRadius, texture));
	}
}
