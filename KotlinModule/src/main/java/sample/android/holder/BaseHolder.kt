package sample.android.holder

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseHolder<in Model>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal abstract fun onBind(model: Model?)

}
