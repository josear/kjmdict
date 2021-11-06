package jp.rodriguez.jmdict

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileReader

internal class JMDictKtTest {
    @Test
    fun toSequence() {
        val jmDict = FileReader(File("JMdict_e_examp")).toJMDictEntrySequence()
        val count = jmDict.count()
        assertEquals(193408, count)
    }

    @Test
    fun fromString() {
        val someEntry = """
            <?xml version="1.0" encoding="UTF-8"?>
            <!DOCTYPE JMdict [
            <!ENTITY adj-i "adjective (keiyoushi)">
            <!ENTITY uk "word usually written using kana alone">
            <!ENTITY n "noun (common) (futsuumeishi)">
            ]>
            <JMdict>
            <entry>
            <ent_seq>1000260</ent_seq>
            <k_ele>
            <keb>悪どい</keb>
            </k_ele>
            <r_ele>
            <reb>あくどい</reb>
            </r_ele>
            <sense>
            <pos>&adj-i;</pos>
            <xref>あくが強い・あくがつよい・2</xref>
            <misc>&uk;</misc>
            <s_inf>あく from 灰汁</s_inf>
            <gloss>gaudy</gloss>
            <gloss>showy</gloss>
            <gloss>garish</gloss>
            <gloss>loud</gloss>
            <example>
            <ex_srce exsrc_type="tat">76325</ex_srce>
            <ex_text>あくどい</ex_text>
            <ex_sent xml:lang="jpn">王女があくどい化粧をしていた。</ex_sent>
            <ex_sent xml:lang="eng">The princess was wearing too much makeup.</ex_sent>
            </example>
            </sense>
            <sense>
            <pos>&adj-i;</pos>
            <misc>&uk;</misc>
            <gloss>crooked</gloss>
            <gloss>vicious</gloss>
            <gloss>wicked</gloss>
            <gloss>nasty</gloss>
            <gloss>unscrupulous</gloss>
            <gloss>dishonest</gloss>
            <example>
            <ex_srce exsrc_type="tat">234503</ex_srce>
            <ex_text>あくどい</ex_text>
            <ex_sent xml:lang="jpn">あくどい事をするな。</ex_sent>
            <ex_sent xml:lang="eng">Don't do wicked things.</ex_sent>
            </example>
            </sense>
            </entry>
            <entry>
            <ent_seq>1323080</ent_seq>
            <k_ele>
            <keb>車</keb>
            <ke_pri>ichi1</ke_pri>
            <ke_pri>news1</ke_pri>
            <ke_pri>nf01</ke_pri>
            </k_ele>
            <r_ele>
            <reb>くるま</reb>
            <re_pri>ichi1</re_pri>
            <re_pri>news1</re_pri>
            <re_pri>nf01</re_pri>
            </r_ele>
            <sense>
            <pos>&n;</pos>
            <gloss>car</gloss>
            <gloss>automobile</gloss>
            <gloss>vehicle</gloss>
            <example>
            <ex_srce exsrc_type="tat">148966</ex_srce>
            <ex_text>車</ex_text>
            <ex_sent xml:lang="jpn">車を運転する時はいくら注意してもしすぎることはない。</ex_sent>
            <ex_sent xml:lang="eng">You cannot be too careful when you drive a car.</ex_sent>
            </example>
            </sense>
            <sense>
            <pos>&n;</pos>
            <gloss>wheel</gloss>
            </sense>
            </entry>            
            </JMdict>
            """.trimIndent()

        val jmDictFromXml = someEntry.toJMDict()
        assertEquals(2, jmDictFromXml.entries.count())
        assertEquals("悪どい", jmDictFromXml.entries[0].kanjiElements[0].kanji[0])
        assertEquals("王女があくどい化粧をしていた。", jmDictFromXml.entries[0].senses[0].examples?.get(0)?.sentences?.first()?.text)

        val json = Json {
            encodeDefaults = false
        }

        val jmDictJson = json.encodeToString(jmDictFromXml)
        println(jmDictJson)

        val jmDictFromJson = Json.decodeFromString<JMDict>(jmDictJson)
        assertEquals(jmDictFromXml, jmDictFromJson)
    }
}