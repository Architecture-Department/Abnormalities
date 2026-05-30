package architecture.abnormalities.datagen.tag

import architecture.abnormalities.core.AbnormalitiesConstants
import architecture.abnormalities.init.entity.OrdealsEntityTypes
import architecture.abnormalities.init.tag.AbnormalitiesEntityTags
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.EntityTypeTagsProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

@Suppress("unchecked_cast")
class DatagenEntityTag(
	output: PackOutput,
	completableFuture: CompletableFuture<HolderLookup.Provider>,
	existingFileHelper: ExistingFileHelper?
) : EntityTypeTagsProvider(output, completableFuture, AbnormalitiesConstants.ID, existingFileHelper) {

	override fun addTags(provider: HolderLookup.Provider) {
		tag(AbnormalitiesEntityTags.ORDEALS_VIOLET).add(
			OrdealsEntityTypes.GRANT_US_LOVE.get(),
			OrdealsEntityTypes.FRUIT_OF_UNDERSTANDING.get()
		)
		tag(AbnormalitiesEntityTags.ORDEALS_AMBER)
		tag(AbnormalitiesEntityTags.ORDEALS_GREEN)
		tag(AbnormalitiesEntityTags.ORDEALS_CRIMSON)
		tag(AbnormalitiesEntityTags.ORDEALS)
			.addTags(
				AbnormalitiesEntityTags.ORDEALS_VIOLET,
				AbnormalitiesEntityTags.ORDEALS_AMBER,
				AbnormalitiesEntityTags.ORDEALS_GREEN,
				AbnormalitiesEntityTags.ORDEALS_CRIMSON
			)
	}
}
