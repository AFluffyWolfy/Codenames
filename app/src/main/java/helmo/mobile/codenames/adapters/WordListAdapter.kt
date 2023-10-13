package helmo.mobile.codenames.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import helmo.mobile.codenames.R
import helmo.mobile.codenames.activities.NewGameActivity
import helmo.mobile.codenames.model.WordList

class WordListAdapter(
    val context: NewGameActivity,
    private val WordListContainer: List<WordList>,
    private val layoutId: Int) : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    private var selectedPos: Int = 0
    private var lastPos: Int = 0
    var currentlySelected: String = "animaux"


    // Boîte pour ranger tout les composants à contrôler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.word_list_name)
        val radioButton: RadioButton = view.findViewById(R.id.word_list_selection)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWordList = WordListContainer[position]

        holder.name.text = currentWordList.listName

        holder.radioButton.isChecked = position == selectedPos

        holder.radioButton.setOnClickListener {
            selectedPos = holder.adapterPosition

            notifyItemChanged(lastPos)
            lastPos = selectedPos
            notifyItemChanged(selectedPos)
            currentlySelected = currentWordList.listName
        }
    }

    override fun getItemCount(): Int = WordListContainer.size
}