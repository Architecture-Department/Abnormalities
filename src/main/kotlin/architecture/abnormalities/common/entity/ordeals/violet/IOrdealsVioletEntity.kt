package architecture.abnormalities.common.entity.ordeals.violet

import architecture.abnormalities.common.entity.ordeals.IOrdealsEntity
import architecture.abnormalities.init.tag.AbnormalitiesEntityTags
import architecture.goldenboughs_lib.api.world.entity.ai.CampHurtByTargetGoal
import net.minecraft.world.entity.Entity

interface IOrdealsVioletEntity : IOrdealsEntity {
	override fun isCamp(entity: Entity): Boolean {
		return super.isCamp(entity) || entity.type.`is`(AbnormalitiesEntityTags.ORDEALS_VIOLET)
	}

	override fun registerGoals() {
		super.registerGoals()
		targetSelector.addGoal(2, CampHurtByTargetGoal(mob, IOrdealsVioletEntity::class.java))
	}
}
