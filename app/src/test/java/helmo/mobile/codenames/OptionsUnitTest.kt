package helmo.mobile.codenames

import helmo.mobile.codenames.model.Options
import org.junit.Test

import org.junit.Assert.*

class OptionsUnitTest {
    @Test
    fun option_builds_as_intended() {
        val options: Options = Options("bleu", "rouge", "path", "path", "path")
        assertTrue(options.blueTeamName == "bleu")
        assertTrue(options.redTeamName == "rouge")
        assertTrue(options.blueTeamPicture == "path")
        assertTrue(options.redTeamPicture == "path")
        assertTrue(options.chosenBuzz == "path")

    }
}