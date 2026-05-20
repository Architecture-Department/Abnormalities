package architecture.abnormalities.common.entity.ordeals.crimson

import architecture.abnormalities.common.entity.ordeals.IOrdealsEntity
import architecture.abnormalities.init.tag.AbnormalitiesEntityTags
import architecture.goldenboughs_lib.api.world.entity.ai.CampHurtByTargetGoal
import net.minecraft.world.entity.Entity

interface IOrdealsCrimsonEntity : IOrdealsEntity {
	override fun isCamp(entity: Entity): Boolean {
		return super.isCamp(entity) || mob.type.`is`(AbnormalitiesEntityTags.ORDEALS_CRIMSON)
	}

	override fun registerGoals() {
		super.registerGoals()
		targetSelector.addGoal(1, CampHurtByTargetGoal(mob, IOrdealsCrimsonEntity::class.java))
	}
}
