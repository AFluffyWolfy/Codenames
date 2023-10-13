package helmo.mobile.codenames.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import helmo.mobile.codenames.R
import helmo.mobile.codenames.adapters.WordListAdapter
import helmo.mobile.codenames.adapters.WordListItemDecoration
import helmo.mobile.codenames.contracts.NewGameContract
import helmo.mobile.codenames.databinding.ActivityNewGameBinding
import helmo.mobile.codenames.model.WordList
import helmo.mobile.codenames.presenters.NewGamePresenter
import helmo.mobile.codenames.repository.GameRepository
import helmo.mobile.codenames.repository.WordListRepository


class NewGameActivity : AppCompatActivity(), NewGameContract.View {
    private lateinit var presenter: NewGameContract.Presenter
    private lateinit var binding: ActivityNewGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewGameBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setPresenter(presenter =
        NewGamePresenter(this,
            WordListRepository(resources.openRawResource(R.raw.wordslist)),
            GameRepository(filesDir.absolutePath, "save.json")
        ))

        initializeComponents()

        presenter.onViewCreated()
    }

    private fun initializeComponents() {
        val validationButton: Button = findViewById(R.id.new_game_button)
        validationButton.setOnClickListener {
            presenter.onCreateGameFor((binding.wordListRecycler.adapter as WordListAdapter).currentlySelected)
        }
        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }
        setStatusBarColor()
    }

    private fun setStatusBarColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#8f776b")
    }

    override fun setWordLists(wordLists: Collection<WordList>) {
        val wordListRecyclerView = findViewById<RecyclerView>(R.id.word_list_recycler)
        wordListRecyclerView.adapter = WordListAdapter(this, wordLists.toList(), R.layout.item_word_list)
        wordListRecyclerView.addItemDecoration(WordListItemDecoration())
    }

    override fun startGame() {
        startActivity(Intent(this, PlayGameActivity::class.java))
        finish()
    }

    override fun setPresenter(presenter: NewGameContract.Presenter) {
        this.presenter = presenter
    }

    override fun goHome() {
        TODO("Not yet implemented")
    }

    override fun goBack() = finish()
}