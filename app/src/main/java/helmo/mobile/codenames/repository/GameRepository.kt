package helmo.mobile.codenames.repository

import android.media.MediaScannerConnection
import com.google.gson.Gson
import helmo.mobile.codenames.infrastructures.dto.GameDto
import helmo.mobile.codenames.model.Game
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import helmo.mobile.codenames.infrastructures.Mapper
import java.io.FileWriter

class GameRepository(
    private val filePath: String,
    private val fileName: String
    ) : helmo.mobile.codenames.infrastructures.GameRepository {

    override fun getCurrentGame(): Game? {
        val file = File("$filePath/$fileName")
        if (!file.exists()) {
            return null
        }
        val fr = FileReader(file.absolutePath)
        var dto: GameDto?
        fr.use  {
            dto = Gson().fromJson(BufferedReader(fr), GameDto::class.java)
        }
        return if (dto == null) null else Mapper.fromDTO(dto!!)
    }

    override fun saveGame(game: Game) {
        val file = File("$filePath/$fileName")
        if(!file.exists()) {
            file.createNewFile()
        }
        val fw = FileWriter(file.absolutePath)
        fw.use {
            fw.write(Gson().toJson(Mapper.toDTO(game), GameDto::class.java))
        }
    }

    override fun deleteGame() {
        var file = File("$filePath/$fileName")
        if (file.exists()) {
            file.delete()
        }
        file = File("$filePath")
        if (file.isDirectory && file.exists()) {
            file.delete()
        }
    }

}