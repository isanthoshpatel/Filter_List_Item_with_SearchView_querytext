package com.example.filter_list_item_with_searchview_querytext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil as DiffUtil1

class RVAdaptor(var c: Context, var list: MutableList<CardviewData>) :
    RecyclerView.Adapter<RVAdaptor.RVViewHolder>(), Filterable {
    var elist = mutableListOf<CardviewData>()

    init {
        elist = this.list
    }

    /* constructor(c: Context) : super(object : DiffUtil1.ItemCallback<CardviewData>() {
         override fun areItemsTheSame(oldItem: CardviewData, newItem: CardviewData): Boolean {
             return oldItem.title == newItem.title &&
                     oldItem.desc == newItem.desc
         }

         override fun areContentsTheSame(oldItem: CardviewData, newItem: CardviewData): Boolean {
             return oldItem.title == newItem.title
         }

     }) {
         elist = mutableListOf()
         elist.addAll(currentList)
     } */


    class RVViewHolder : RecyclerView.ViewHolder {
        lateinit var tv1: TextView
        lateinit var tv2: TextView

        constructor(v: View) : super(v) {
            tv1 = v.findViewById(R.id.tv1)
            tv2 = v.findViewById(R.id.tv2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        LayoutInflater.from(c).inflate(R.layout.cardview, parent, false).let {
            return RVViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.apply {
            list.get(position).run {
                tv1.text = title
                tv2.text = desc

            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(querytext: CharSequence?): FilterResults {

                var flist = mutableListOf<CardviewData>()

                if (querytext.isNullOrEmpty()) {
                    flist.addAll(elist)
                } else {
                    elist.forEach {
                        var qtext = querytext.toString().trim()
                        if (it.title.contains(qtext)) {
                            flist.add(it)
                        }
                    }
                }

                var r = FilterResults()
                r.values = flist
                return r
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list.clear()
                var r = results?.values as MutableList<CardviewData>
                list.addAll(r)
                notifyDataSetChanged()
            }
        }


    }
}