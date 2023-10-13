package helmo.mobile.codenames

import helmo.mobile.codenames.model.WordList
import org.junit.Test

import org.junit.Assert.*

class WordListUnitTest {
    private val list = WordList("liste", arrayListOf("un", "deux", "trois", "quatre", "cinq"))
    private val listSameNameDifferentContent = WordList("liste", arrayListOf("uno", "dos", "tres", "quatro", "cinquo"))
    private val listDifferentNameSameContent = WordList("liste nombres", arrayListOf("un", "deux", "trois", "quatre", "cinq"))

    @Test
    fun wordList_same_key_are_equals() {
        assertTrue(list == listSameNameDifferentContent)
    }

    @Test
    fun wordList_different_key_are_not_equals() {
        assertFalse(list == listDifferentNameSameContent)
    }
}