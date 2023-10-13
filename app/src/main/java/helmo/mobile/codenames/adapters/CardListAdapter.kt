package helmo.mobile.codenames.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import helmo.mobile.codenames.R
import helmo.mobile.codenames.adapters.viewHolders.CardHolder
import helmo.mobile.codenames.contracts.BuzzerPlayer
import helmo.mobile.codenames.contracts.PlayContract
import helmo.mobile.codenames.presenters.CardViewInfo

class CardListAdapter(
    private val presenter: PlayContract.Presenter,
    private val cards: List<CardViewInfo>,
    private val buzzerPlayer: BuzzerPlayer) : Adapter<CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.card_item, parent, false)

        return CardHolder(view)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.info = cards[position]
        holder.cardView.setOnClickListener {
            if(!holder.info!!.isFlipped) {
                buzzerPlayer.playBuzzzer()
                presenter.onCardSelected(holder.info!!.word)
            }
        }
    }

    override fun getItemCount(): Int = cards.size
}