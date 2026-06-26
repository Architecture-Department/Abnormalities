package architecture.abnormalities.common.item

import architecture.goldenboughs_lib.api.world.entity.ISpawnByEgg
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.entity.SpawnerBlockEntity
import net.minecraft.world.level.gameevent.GameEvent
import net.neoforged.neoforge.common.DeferredSpawnEggItem
import java.util.function.Supplier

class ModEggItem(
	entityType: Supplier<out EntityType<out Mob>>,
	properties: Properties
) : DeferredSpawnEggItem(entityType, -1, -1, properties) {

	override fun useOn(context: UseOnContext): InteractionResult {
		val level = context.level
		if (level !is ServerLevel) {
			return InteractionResult.SUCCESS
		}

		val stack = context.itemInHand
		val pos = context.clickedPos
		val direction = context.clickedFace
		val blockState = level.getBlockState(pos)
		val blockEntity = level.getBlockEntity(pos)

		// 处理刷怪笼
		if (blockEntity is SpawnerBlockEntity) {
			val entityType = this.getType(stack)
			blockEntity.setEntityId(entityType, level.random)
			level.sendBlockUpdated(pos, blockState, blockState, 3)
			level.gameEvent(context.player, GameEvent.BLOCK_CHANGE, pos)
			stack.shrink(1)
			return InteractionResult.CONSUME
		}

		// 确定生成位置
		val spawnPos: BlockPos = if (blockState.getCollisionShape(level, pos).isEmpty) {
			pos
		} else {
			pos.relative(direction)
		}

		val entityType = this.getType(stack)
		val entity = entityType.spawn(
			level, stack, context.player, spawnPos,
			MobSpawnType.SPAWN_EGG, true, pos != spawnPos && direction == Direction.UP
		)
		if (entity != null) {
			if (entity is ISpawnByEgg) {
				entity.onSpawnByEgg()
			}

			stack.shrink(1)
			level.gameEvent(context.player, GameEvent.ENTITY_PLACE, pos)
			return InteractionResult.CONSUME
		}

		return InteractionResult.FAIL
	}

	override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
		val stack = player.getItemInHand(hand)
		val hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY)
		if (hitResult.type == net.minecraft.world.phys.HitResult.Type.MISS) {
			return InteractionResultHolder.pass(stack)
		}

		if (level !is ServerLevel) {
			return InteractionResultHolder.success(stack)
		}

		val pos = hitResult.blockPos
		if (level.getBlockState(pos).block !is LiquidBlock) {
			return InteractionResultHolder.pass(stack)
		}

		if (!level.mayInteract(player, pos) || !player.mayUseItemAt(pos, hitResult.direction, stack)) {
			return InteractionResultHolder.fail(stack)
		}

		val entityType = this.getType(stack)
		val entity = entityType.spawn(level, stack, player, pos, MobSpawnType.SPAWN_EGG, false, false)
			?: return InteractionResultHolder.pass(stack)

		if (entity is ISpawnByEgg) {
			entity.onSpawnByEgg()
		}

		stack.consume(1, player)
		player.awardStat(Stats.ITEM_USED.get(this))
		level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.position())
		return InteractionResultHolder.consume(stack)
	}
}
