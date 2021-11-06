package jp.rodriguez.jmdict

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue


@Serializable
@SerialName("JMdict")
data class JMDict(
    val entries: List<Entry>
)

@Serializable
@SerialName("entry")
data class Entry(
    @XmlElement(true)
    @SerialName("ent_seq")
    val id: Int,

    val kanjiElements: List<KanjiElement>,
    val readingElements: List<ReadingElement>,
    val senses: List<Sense>,
)

@Serializable
@SerialName("k_ele")
data class KanjiElement(
    @SerialName("keb")
    val kanji: List<String>,

    @SerialName("ke_inf")
    val info: List<String>? = null,

    @SerialName("ke_pri")
    val priority: List<String>? = null,

    @SerialName("re_nokanji")
    val noKanji: String? = null,
)

@Serializable
@SerialName("r_ele")
data class ReadingElement(
    @SerialName("reb")
    @XmlElement(true)
    val reading: String,

    @SerialName("re_restr")
    val restrictedReadings: List<String>? = null,

    @SerialName("re_pri")
    val priority: List<String>? = null,

    @SerialName("re_nokanji")
    val noKanji: List<String>? = null,

    @SerialName("re_inf")
    val info: List<String>? = null,
)

@Serializable
@SerialName("sense")
data class Sense(
    @SerialName("stagk")
    val restrictedToKanji: List<String>? = null,

    @SerialName("stagr")
    val restrictedToReading: List<String>? = null,

    @SerialName("pos")
    val partOfSpeech: List<String>,

    @SerialName("xref")
    val xref: List<String>? = null,

    @SerialName("ant")
    val antonyms: List<String>? = null,

    @SerialName("field")
    val fields: List<String>? = null,

    @SerialName("misc")
    val misc: List<String>? = null,

    @SerialName("s_inf")
    val info: List<String>? = null,

    @SerialName("lsource")
    val languageSources: List<LanguageSource>? = null,

    @SerialName("dial")
    val dialects: List<String>? = null,

    val glosses: List<Gloss>,

    val examples: List<Example>? = null,
)

@Serializable
@SerialName("lsource")
data class LanguageSource(
    @XmlSerialName("lang", prefix = "", namespace = "http://www.w3.org/XML/1998/namespace")
    val lang: String = "eng",

    @SerialName("xml:lang")
    private val xmllang: String? = "eng",

    @SerialName("ls_wasei")
    val wasei: String? = null,

    @SerialName("ls_type")
    val type: String? = null,

    @XmlValue(true)
    val word: String? = null,
)

@Serializable
@SerialName("gloss")
data class Gloss(
    @SerialName("g_type")
    @XmlElement(false)
    val type: String? = null,

    @SerialName("xml:lang")
    @XmlElement(false)
    val lang: String = "eng",

    @SerialName("g_gend")
    @XmlElement(false)
    val gender: String? = null,

    @XmlValue(true)
    val text: String,
)

@Serializable
@SerialName("example")
data class Example(
    @SerialName("ex_srce")
    val source: Source,

    @SerialName("ex_text")
    val text: List<String>,

    val sentences: Collection<Sentence>,
) {
    @Serializable
    @SerialName("ex_srce")
    data class Source(
        @SerialName("exsrc_type")
        val type: String,

        @XmlValue(true)
        val id: String
    )

    @Serializable
    @SerialName("ex_sent")
    data class Sentence(
        @XmlSerialName("lang", prefix = "", namespace = "http://www.w3.org/XML/1998/namespace")
        val lang: String = "eng",

        @XmlValue(true)
        val text: String
    )
}
