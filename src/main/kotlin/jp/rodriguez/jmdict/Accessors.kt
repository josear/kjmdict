package jp.rodriguez.jmdict

import nl.adaptivity.xmlutil.deserialize
import java.io.Reader

fun String.toJMDict() = deserialize<JMDict>(this)
fun Reader.toJMDict() = deserialize<JMDict>(this)

fun Reader.toJMDictEntrySequence() = toXmlRecordSequence().map {
    deserialize<Entry>("$jmDictDtd\n${it.joinToString("\n")}")
}

private fun Reader.toXmlRecordSequence(): Sequence<List<String>> = sequence<List<String>> {
    var inEntry = false
    val currentEntryLines = mutableListOf<String>()

    useLines { lines ->
        lines.forEach { line ->
            if (inEntry) currentEntryLines.add(line)
            when {
                line.startsWith("<entry>") -> {
                    inEntry = true
                    currentEntryLines.add(line)
                }
                line.startsWith("</entry>") -> {
                    inEntry = false
                    yield(currentEntryLines)
                    currentEntryLines.clear()
                }
            }
        }
    }
}

private val jmDictDtd = object {}.javaClass.getResource("/jmdict.dtd")!!.readText()