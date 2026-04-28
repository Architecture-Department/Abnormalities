package architecture.abnormalities.datagen.i18n;

import architecture.abnormalities.core.Abnormalities;
import architecture.abnormalities.init.tag.AbnormalitiesEntityTags;
import architecture.goldenboughs_lib.datagen.i18n.DatagenI18n;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@ApiStatus.Internal
public final class ZhCn extends DatagenI18n {
	private static final Map<Supplier<? extends Item>, String> ITEMS = new HashMap<>();
	private static final Map<Supplier<? extends SoundEvent>, String> SOUND_EVENT = new HashMap<>();
	private static final Map<Supplier<? extends EntityType<?>>, String> ENTITY = new HashMap<>();
	private static final Map<String, String> MAP = new HashMap<>();

	public ZhCn(final PackOutput output) {
		super(output, Abnormalities.ID, "zh_cn");
	}

	public static void addI18nText(String zhCn, String key) {
		if (!FMLEnvironment.production) {
			MAP.put(key, zhCn);
		}
	}

	public static void addI18nEntityTypeText(String zhName, Supplier<? extends EntityType<?>> supplier) {
		if (!FMLEnvironment.production) {
			ENTITY.put(supplier, zhName);
		}
	}

	public static void addI18nItemText(String zhName, Supplier<? extends Item> deferredItem) {
		if (!FMLEnvironment.production) {
			ITEMS.put(deferredItem, zhName);
		}
	}

	public static void addI18nSoundEventText(String zhName, Supplier<? extends SoundEvent> supplier) {
		if (!FMLEnvironment.production) {
			SOUND_EVENT.put(supplier, zhName);
		}
	}

	@Override
	protected void addTranslations() {
		addPackDescription(Abnormalities.ID, "异想体");
		addItemList(ITEMS);
		addEntityList(ENTITY);
		addSoundEventList(SOUND_EVENT);
		MAP.forEach(this::add);

		add(AbnormalitiesEntityTags.ABNORMALITIES, "异想体");
		add(AbnormalitiesEntityTags.ORDEALS, "考验");
		add(AbnormalitiesEntityTags.ORDEALS_VIOLET, "紫罗兰色的考验");
		add(AbnormalitiesEntityTags.ORDEALS_AMBER, "琥珀色的考验");
		add(AbnormalitiesEntityTags.ORDEALS_GREEN, "绿色的考验");
		add(AbnormalitiesEntityTags.ORDEALS_CRIMSON, "血色的考验");
		add(AbnormalitiesEntityTags.THE_SWEEPERS, "清道夫");
	}
}
