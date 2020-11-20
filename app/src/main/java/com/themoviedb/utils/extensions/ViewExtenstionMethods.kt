package com.themoviedb.utils.extensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author- Nitin Khanna
 * @date - 19-11-2020
 */

fun View.makeVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.makeVisibleWithAnimation(
    isVisible: Boolean
) {

    if (isVisible) {
        this.showWithAnimation()
    } else this.hideWithAnimation()
}

fun RecyclerView.validateNoDataView(llNoData: View?) {
    val counts = this.adapter?.itemCount ?: 0
    llNoData?.makeVisible(isVisible = counts == 0)
}

fun View.changeBackgroundColor(colorCode: Int) {
    val context = this.context
    this.setBackgroundColor(context.resources.getColor(colorCode))

}
