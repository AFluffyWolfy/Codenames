package helmo.mobile.codenames.activities

import android.content.Intent
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import helmo.mobile.codenames.R
import helmo.mobile.codenames.contracts.EndGameContract
import helmo.mobile.codenames.presenters.EndGamePresenter
import helmo.mobile.codenames.repository.GameRepository
import java.io.File

class EndGameActivity : AppCompatActivity(), EndGameContract.View {
    private lateinit var presenter: EndGameContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        setPresenter(presenter = EndGamePresenter(this,
            GameRepository(filesDir.absolutePath, "save.json"))
        )

        initalizeComponents()
        setTeamInfos()
        setStatusBarColor()
    }

    private fun initalizeComponents() {
        findViewById<Button>(R.id.new_game_button).setOnClickListener {
            val intent = Intent(this, NewGameActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.home_button).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.share_button).setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Mon équipe \"" + intent.getStringExtra("winnerName") + "\" a gagner une partie de Codenames sur la superbe application créer dans le cadre du labo de Dev Mobile par Auquier Cyril et Lakeye Brian !")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun setStatusBarColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#8f776b")
    }

    private fun setTeamInfos() {
        findViewById<ImageView>(R.id.team_picture).setImageURI(Uri.fromFile(File(intent.getStringExtra("winnerPath"))))
        findViewById<TextView>(R.id.team_name).text = intent.getStringExtra("winnerName")
    }

    override fun setPresenter(presenter: EndGameContract.Presenter) {
        this.presenter = presenter
        presenter.onViewCreated()
    }

    override fun goHome() {

    }

    override fun goBack() {

    }


}