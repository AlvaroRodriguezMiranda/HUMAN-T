package com.humant.domain.model

import java.math.BigInteger

@JvmInline value class ContentId(val value: String) {
    init { require(value.matches(Regex("[a-z][a-z0-9_.]*"))) }
}

data class ItemDefinition(
    val id: ContentId,
    val name: String,
    val price: BigInteger,
    val permanentClickBonus: BigInteger,
    val slot: ContentId,
    val sprite: ContentId,
) {
    init { require(price.signum() >= 0 && permanentClickBonus.signum() >= 0) }
}

/** Mechanic IDs select registered rule implementations; names never serve as keys. */
data class PhaseDefinition(val id: ContentId, val mechanic: ContentId)
data class BossDefinition(val id: ContentId, val phases: List<PhaseDefinition>) {
    init {
        require(phases.isNotEmpty() && phases.first().mechanic.value == "manual_ph_trial")
        require(phases.map { it.id }.distinct().size == phases.size)
    }
}
data class PrestigeNodeDefinition(
    val id: ContentId, val branch: ContentId, val cost: BigInteger,
    val prerequisites: Set<ContentId>,
) {
    init { require(cost.signum() > 0 && id !in prerequisites) }
}
