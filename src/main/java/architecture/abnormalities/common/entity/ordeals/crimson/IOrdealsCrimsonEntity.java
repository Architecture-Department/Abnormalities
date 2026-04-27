package architecture.abnormalities.common.entity.ordeals.crimson;

import architecture.abnormalities.common.entity.ordeals.IOrdealsEntity;
import architecture.abnormalities.init.tag.AbnormalitiesEntityTags;
import architecture.goldenboughs_lib.api.world.entity.ai.CampHurtByTargetGoal;
import net.minecraft.world.entity.Entity;

public interface IOrdealsCrimsonEntity extends IOrdealsEntity {
	@Override
	default boolean isCamp(Entity entity) {
		return IOrdealsEntity.super.isCamp(entity) || getMob().getType().is(AbnormalitiesEntityTags.ORDEALS_CRIMSON);
	}

	@Override
	default void registerGoals() {
		IOrdealsEntity.super.registerGoals();
		getTargetSelector().addGoal(1, new CampHurtByTargetGoal(getMob(), IOrdealsCrimsonEntity.class));
	}
}
