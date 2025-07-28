@file:Suppress("NOTHING_TO_INLINE")

package io.github.mayachen350.chesnaybot.utils

import dev.kord.common.entity.Snowflake

inline fun ULong.toSnowflake() = Snowflake(this)