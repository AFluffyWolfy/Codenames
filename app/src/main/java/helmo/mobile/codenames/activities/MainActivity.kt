package helmo.mobile.codenames.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import helmo.mobile.codenames.R
import helmo.mobile.codenames.contracts.MainActivityContract
import helmo.mobile.codenames.databinding.ActivityMainBinding
import helmo.mobile.codenames.presenters.MainActivityPresenter
import helmo.mobile.codenames.repository.GameRepository

class MainActivity : AppCompatActivity(), MainActivityContract.View {
    private lateinit var presenter: MainActivityContract.Presenter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setPresenter(presenter = MainActivityPresenter(this,
            GameRepository(filesDir.absolutePath, "save.json")))

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#8f776b")

        findViewById<Button>(R.id.how_to_play_btn).setOnClickListener {
            startActivity(Intent(this, GameRulesActivity::class.java))
        }

        binding.continueGameBtn.setOnClickListener {
            val intent = Intent(this, PlayGameActivity::class.java)
            startActivity(intent)
        }

        val optionsBtn = findViewById<Button>(R.id.options_btn)
        optionsBtn.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }

        val newGameBtn = findViewById<Button>(R.id.new_game_button)
        newGameBtn.setOnClickListener {
            val intent = Intent(this, NewGameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun hideContinueButton() {
        binding.continueGameBtn.visibility = View.GONE
    }

    override fun showContinueButton() {
        binding.continueGameBtn.visibility = View.VISIBLE
    }

    override fun setPresenter(presenter: MainActivityContract.Presenter) {
        this.presenter = presenter
        presenter.onViewCreated()
    }

    override fun onResume() {
        super.onResume()

        presenter.onViewCreated()
    }

    override fun goHome() {

    }

    override fun goBack() {

    }
}