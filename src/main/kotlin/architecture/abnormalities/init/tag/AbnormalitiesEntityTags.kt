package architecture.abnormalities.init.tag

import architecture.abnormalities.core.Abnormalities
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.EntityType

object AbnormalitiesEntityTags {
	/**
	 * 异想体
	 */
	@JvmField
	val ABNORMALITIES: TagKey<EntityType<*>> = createTag("abnormalities")

	//region 考验
	/**
	 * 考验
	 */
	@JvmField
	val ORDEALS: TagKey<EntityType<*>> = createTag("ordeals")

	/**
	 * 考验/紫罗兰
	 */
	@JvmField
	val ORDEALS_VIOLET: TagKey<EntityType<*>> = createTag("ordeals/violet")

	/**
	 * 考验/琥珀色
	 */
	@JvmField
	val ORDEALS_AMBER: TagKey<EntityType<*>> = createTag("ordeals/amber")

	/**
	 * 考验/绿色
	 */
	@JvmField
	val ORDEALS_GREEN: TagKey<EntityType<*>> = createTag("ordeals/green")

	/**
	 * 考验/血色
	 */
	@JvmField
	val ORDEALS_CRIMSON: TagKey<EntityType<*>> = createTag("ordeals/crimson")
	//endregion

	/**
	 * 清道夫
	 */
	@JvmField
	val THE_SWEEPERS: TagKey<EntityType<*>> = createTag("the_sweepers")

	private fun createTag(name: String): TagKey<EntityType<*>> {
		return createTag(Abnormalities.modRl(name))
	}

	private fun createTag(location: ResourceLocation): TagKey<EntityType<*>> {
		return TagKey.create(Registries.ENTITY_TYPE, location)
	}

	private fun createCTag(name: String): TagKey<EntityType<*>> {
		return createTag(ResourceLocation.fromNamespaceAndPath("c", name))
	}

	private fun createMcTag(name: String): TagKey<EntityType<*>> {
		return createTag(ResourceLocation.withDefaultNamespace(name))
	}
}
