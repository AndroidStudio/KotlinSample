package sample.android

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_list.view.*
import sample.android.holder.BaseHolder
import sample.android.model.Model
import java.util.*
import kotlin.sample.R

class RecyclerListAdapter(private val context: Context) : RecyclerView.Adapter<BaseHolder<Model>>() {

    private val list = object : ArrayList<Model>() {
        init {
            add(Model())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Model>? {
        return when (viewType) {
            1 -> Holder(context, parent)
            else -> null
        }
    }

    override fun onBindViewHolder(holder: BaseHolder<Model>, position: Int) {
        holder.onBind(list[position]);
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getViewType()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    internal class Holder(context: Context, parent: ViewGroup) : BaseHolder<Model>
    (LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)) {

        override fun onBind(model: Model?) {
            itemView.nameTextView.text = model?.name
        }
    }
}
