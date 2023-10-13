package helmo.mobile.codenames.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import helmo.mobile.codenames.infrastructures.dto.GameDto
import helmo.mobile.codenames.model.WordList
import java.io.*

class WordListRepository(
    private val inputStream: InputStream
) : Repository {
    var lists: List<WordList> = listOf()

    fun getListsOfWords(): List<WordList> {
        if(lists.isEmpty()) {
            BufferedReader(inputStream.bufferedReader()).use {
                lists = Gson().fromJson(it, object : TypeToken<List<WordList>>() {}.type)
            }
        }
        return lists
    }
}