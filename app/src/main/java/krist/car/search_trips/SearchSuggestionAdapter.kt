package krist.car.search_trips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import krist.car.R

class SearchSuggestionAdapter : RecyclerView.Adapter<SearchSuggestionAdapter.ViewHolder>() {
    private var dataSet : List<String> = listOf()

    fun setSuggestionList(suggestion: List<String>) {
        dataSet = suggestion
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_suggestion_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet.get(position))
    }

    override fun getItemCount(): Int = dataSet.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var title: TextView? = null

        init {
            title = itemView.findViewById(R.id.serach_title)
        }

        fun bind(searchSuggestion: String) {
            title?.text = searchSuggestion
        }
    }
}