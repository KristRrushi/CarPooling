package krist.car.search_trips.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import krist.car.R

class SearchSuggestionAdapter(val listener: SuggestionSelectionListener? = null) : RecyclerView.Adapter<SearchSuggestionAdapter.ViewHolder>() {
    private var dataSet : List<String> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_suggestion_item, parent, false)
        return ViewHolder(view) {
            listener?.onSuggestionClicked(dataSet.get(it))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet.get(position))
    }

    override fun getItemCount(): Int = dataSet.size

    fun setSuggestionList(suggestion: List<String>) {
        dataSet = suggestion
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, callBack: (Int) -> Unit): RecyclerView.ViewHolder(itemView) {
        private var title: TextView? = null

        init {
            title = itemView.findViewById(R.id.serach_title)
            itemView.setOnClickListener { callBack.invoke(adapterPosition) }
        }

        fun bind(searchSuggestion: String) {
            title?.text = searchSuggestion
        }
    }
}

interface SuggestionSelectionListener{
    fun onSuggestionClicked(query: String)
}