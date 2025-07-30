package io.github.mayachen350.chesnaybot

import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.ReactionEmoji
import io.github.mayachen350.chesnaybot.features.system.roleChannel.RoleChannelDispenser.Companion.findRoleFromEmoji
import me.jakejmattson.discordkt.util.toSnowflake
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/** Soon™ */
class RoleChannelSysTests {

    private val messageContent1: String = "## Ping roles\n" +
            "✒\uFE0F -> <@&1373485924779819106>\n" +
            "✉\uFE0F  -> <@&1373485924779819105>\n" +
            "\uD83D\uDCBB -> <@&1373485924779819104>\n" +
            "\uD83D\uDCF1 -> <@&1373485924779819103>\n" +
            "\uD83E\uDD16 -> <@&1373485924779819102>\n" +
            "\uD83D\uDCE3 -> <@&1373485924779819100>\n" +
            "\uD83C\uDFEB  -> <@&1398140336357773463>"

    @Test
    fun findRoleFromEmojiTest_Correct_Normal() {
        val emoji1: ReactionEmoji = ReactionEmoji.Unicode("\uD83D\uDCBB")
        val emoji2: ReactionEmoji = ReactionEmoji.Unicode("\uD83C\uDFEB")
        val emoji3: ReactionEmoji = ReactionEmoji.Unicode("✒\uFE0F")

        val role1: Snowflake = 1373485924779819104.toSnowflake()
        val role2: Snowflake = 1398140336357773463.toSnowflake()
        val role3: Snowflake = 1373485924779819106.toSnowflake()


        assertEquals(role1, findRoleFromEmoji(messageContent1, emoji1))
        assertEquals(role2, findRoleFromEmoji(messageContent1, emoji2))
        assertEquals(role3, findRoleFromEmoji(messageContent1, emoji3))
    }

    @Test
    fun findRoleFromEmojiTest_Incorrect_Normal() {
        val emoji1: ReactionEmoji = ReactionEmoji.Unicode("\uD83C\uDF89")
        val emoji2: ReactionEmoji = ReactionEmoji.Unicode("\uD83E\uDD40")

        assertNull(findRoleFromEmoji(messageContent1, emoji1))
        assertNull(findRoleFromEmoji(messageContent1,emoji2))
    }

    private val messageContent2: String = "<:00_furinaloading_DNS:1335413211545206906>   -> <@&1317660304292712474> \n" +
            "\n" +
            "<:furina_true:1376246758006456360>   -> <@&1317660304292712476> \n" +
            "\n" +
            "<a:emascientificallygifing:1089367224445309089>   -> <@&1317660304292712472>"

    @Test
    fun findRoleFromEmojiTest_Correct_Custom() {
        val emoji1: ReactionEmoji = ReactionEmoji.Custom(1335413211545206906.toSnowflake(), "00_furinaloading_DNS", false)
        val emoji2: ReactionEmoji = ReactionEmoji.Custom(1376246758006456360.toSnowflake(), "furina_true", false)
        val emoji3: ReactionEmoji = ReactionEmoji.Custom(1089367224445309089.toSnowflake(), "emascientificallygifing", true)

        val role1: Snowflake = 1317660304292712474.toSnowflake()
        val role2: Snowflake = 1317660304292712476.toSnowflake()
        val role3: Snowflake = 1317660304292712472.toSnowflake()

        assertEquals(role1, findRoleFromEmoji(messageContent1, emoji1))
        assertEquals(role2, findRoleFromEmoji(messageContent1, emoji2))
        assertEquals(role3, findRoleFromEmoji(messageContent1, emoji3))
    }

    @Test
    fun findRoleFromEmojiTest_Incorrect_Custom() {
        val emoji1: ReactionEmoji = ReactionEmoji.Custom(278804487173570560.toSnowflake(), "heartBreak", false)
        val emoji2: ReactionEmoji = ReactionEmoji.Custom(7777777L.toSnowflake(), "police_chief", true)

        assertNull(findRoleFromEmoji(messageContent1, emoji1))
        assertNull(findRoleFromEmoji(messageContent1,emoji2))
    }
}