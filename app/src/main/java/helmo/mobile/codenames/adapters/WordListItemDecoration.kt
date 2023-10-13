package helmo.mobile.codenames.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class WordListItemDecoration : RecyclerView.ItemDecoration() {
    private val verticalMargin: Int = 30
    private val horizontalMargin: Int = 100

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = verticalMargin
        outRect.right = horizontalMargin
    }
}