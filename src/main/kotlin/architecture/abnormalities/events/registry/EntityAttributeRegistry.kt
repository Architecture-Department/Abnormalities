package architecture.abnormalities.events.registry

import architecture.abnormalities.common.entity.abnormalities.TrainingRabbits
import architecture.abnormalities.common.entity.ordeals.violet.FruitOfUnderstanding
import architecture.abnormalities.common.entity.ordeals.violet.GrantUsLove
import architecture.abnormalities.init.entity.AbnormalitiesEntityTypes
import architecture.abnormalities.init.entity.OrdealsEntityTypes
import architecture.abnormalities.util.AbnormalitiesUtil
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent

@EventBusSubscriber(modid = AbnormalitiesUtil.ID)
object EntityAttributeRegistry {

	/**
	 * 注册实体属性
	 */
	@SubscribeEvent
	fun registry(event: EntityAttributeCreationEvent) {
		event.put(OrdealsEntityTypes.GRANT_US_LOVE.get(), GrantUsLove.createAttributes().build())
		event.put(OrdealsEntityTypes.FRUIT_OF_UNDERSTANDING.get(), FruitOfUnderstanding.createAttributes().build())
		event.put(AbnormalitiesEntityTypes.TRAINING_RABBITS.get(), TrainingRabbits.createAttributes().build())
	}
}
