package architecture.abnormalities.init;

import architecture.abnormalities.core.Abnormalities;
import architecture.abnormalities.datagen.i18n.ZhCn;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class AbnormalitiesSoundEvents {
	public static final DeferredRegister<SoundEvent> REGISTRY = Abnormalities.modRegister(BuiltInRegistries.SOUND_EVENT);

	public static final DeferredHolder<SoundEvent, SoundEvent> VIOLET_NOON_DOWN = registerForHolder(
		"violet_noon_down", "“请给我们爱！！！”：下砸", "entity.violet.grant_us_love.down");
	public static final DeferredHolder<SoundEvent, SoundEvent> VIOLET_NOON_ATK = registerForHolder(
		"violet_noon_atk", "“请给我们爱！！！”：攻击", "entity.violet.grant_us_love.atk");
	public static final DeferredHolder<SoundEvent, SoundEvent> VIOLET_NOON_idle = registerForHolder(
		"violet_noon_idle", "“请给我们爱！！！”：触手蠕动", "entity.violet.grant_us_love.idle");
	public static final DeferredHolder<SoundEvent, SoundEvent> VIOLET_NOON_DEATH = registerForHolder(
		"violet_noon_death", "“请给我们爱！！！”：死亡", "entity.violet.grant_us_love.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> VIOLET_DAWN_WALK = registerForHolder(
		"violet_dawn_walk", "“紫罗兰黎明”：蠕动", "entity.violet.fruit_of_understanding.walk");
	public static final DeferredHolder<SoundEvent, SoundEvent> VIOLET_DAWN_SUICIDE = registerForHolder(
		"violet_dawn_suicide", "“紫罗兰黎明”：自爆", "entity.violet.fruit_of_understanding.suicide");
	public static final DeferredHolder<SoundEvent, SoundEvent> VIOLET_DAWN_DEAD = registerForHolder(
		"violet_dawn_dead", "“紫罗兰黎明”：死亡", "entity.violet.fruit_of_understanding.dead");

	private static DeferredHolder<SoundEvent, SoundEvent> registerForHolder(String id, String zhName, String location) {
		DeferredHolder<SoundEvent, SoundEvent> register = AbnormalitiesSoundEvents.REGISTRY.register(id, () -> SoundEvent.createVariableRangeEvent(Abnormalities.modRl(location)));
		ZhCn.addI18nSoundEventText(zhName, register);
		return register;
	}
}
