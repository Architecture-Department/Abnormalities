package architecture.abnormalities.datagen.tag;

import architecture.abnormalities.common.entity.ordeals.IOrdealsEntity;
import architecture.abnormalities.core.Abnormalities;
import architecture.abnormalities.init.entity.OrdealsEntityTypes;
import architecture.abnormalities.init.tag.AbnormalitiesEntityTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@ApiStatus.Internal
@SuppressWarnings("unchecked")
public final class DatagenEntityTag extends EntityTypeTagsProvider {
	public DatagenEntityTag(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, completableFuture, Abnormalities.ID, existingFileHelper);
	}

	private static EntityType<?>[] getArray(Collection<DeferredHolder<EntityType<?>, ? extends EntityType<?>>> entries, Class<? extends IOrdealsEntity> clazz) {
		return entries.stream()
			.filter(entityType -> clazz.isInstance(entityType.get()))
			.map(DeferredHolder::get)
			.toArray(EntityType[]::new);
	}

	@Override
	protected void addTags(final HolderLookup.Provider provider) {
		tag(AbnormalitiesEntityTags.ORDEALS_VIOLET).add(
			OrdealsEntityTypes.GRANT_US_LOVE.get(),
			OrdealsEntityTypes.FRUIT_OF_UNDERSTANDING.get());
		tag(AbnormalitiesEntityTags.ORDEALS_AMBER);
		tag(AbnormalitiesEntityTags.ORDEALS_GREEN);
		tag(AbnormalitiesEntityTags.ORDEALS_CRIMSON);
		tag(AbnormalitiesEntityTags.ORDEALS)
			.addTags(AbnormalitiesEntityTags.ORDEALS_VIOLET,
				AbnormalitiesEntityTags.ORDEALS_AMBER,
				AbnormalitiesEntityTags.ORDEALS_GREEN,
				AbnormalitiesEntityTags.ORDEALS_CRIMSON);
	}
}
