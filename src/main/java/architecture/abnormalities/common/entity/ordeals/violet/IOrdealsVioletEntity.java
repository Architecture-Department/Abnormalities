package architecture.abnormalities.common.entity.ordeals.violet;

import architecture.abnormalities.common.entity.ordeals.IOrdealsEntity;
import architecture.abnormalities.init.tag.AbnormalitiesEntityTags;
import architecture.goldenboughs_lib.api.world.entity.ai.CampHurtByTargetGoal;
import net.minecraft.world.entity.Entity;

public interface IOrdealsVioletEntity extends IOrdealsEntity {
	@Override
	default boolean isCamp(Entity entity) {
		return IOrdealsEntity.super.isCamp(entity) || entity.getType().is(AbnormalitiesEntityTags.ORDEALS_VIOLET);
	}

	@Override
	default void registerGoals() {
		IOrdealsEntity.super.registerGoals();
		getTargetSelector().addGoal(2, new CampHurtByTargetGoal(getMob(), IOrdealsVioletEntity.class));
	}
}
