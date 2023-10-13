package helmo.mobile.codenames.adapters.viewHolders

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import helmo.mobile.codenames.R
import helmo.mobile.codenames.presenters.CardViewInfo

class CardHolder(
    private val view: View,
    var infos: CardViewInfo? = null) : RecyclerView.ViewHolder(view) {

    val cardView: CardView = view.findViewById(R.id.card_item)
    private val cardText: TextView = view.findViewById(R.id.card_text)

    var info
        get() = infos
        set(value) {
            infos = value
            cardText.text = value!!.word
            if(value.isFlipped) {
                cardText.text = value.word
                when(info!!.color) {
                    "BLACK" -> {
                        view.findViewById<CardView>(R.id.card_item).setCardBackgroundColor(Color.parseColor("#33312c"))
                        view.findViewById<TextView>(R.id.card_text).setTextColor(Color.parseColor("#f2f0eb"))
                    }
                    "BLUE" -> {
                        view.findViewById<CardView>(R.id.card_item).setCardBackgroundColor(Color.parseColor("#3a8ee8"))
                    }
                    "RED" -> {
                        view.findViewById<CardView>(R.id.card_item).setCardBackgroundColor(Color.parseColor("#db3543"))
                    }
                    "WHITE" -> {
                        view.findViewById<CardView>(R.id.card_item).setCardBackgroundColor(Color.parseColor("#f0cb93"))
                    }
                }
            }
        }
}
