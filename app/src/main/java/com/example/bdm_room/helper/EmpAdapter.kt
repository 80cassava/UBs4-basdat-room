package com.example.bdm_room.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bdm_room.MhsEntity
import com.example.bdm_room.R

class MhsAdapter(
    private var itemsCells: ArrayList<MhsEntity>,
    private val clickListener: (MhsEntity) -> Unit) :
    RecyclerView.Adapter<MhsAdapter.ViewHolder>() {

    //insialisasi untuk list yang akan dibuat menggunakan RecylerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewId = itemView.findViewById(R.id.textViewId) as TextView
        val textViewName = itemView.findViewById(R.id.textViewName) as TextView
        val textViewAddress = itemView.findViewById(R.id.textViewAddress) as TextView
        //fun untuk memanggil data ketika item diklik
        fun bind(part: MhsEntity, clickListener: (MhsEntity) -> Unit) {
            itemView.setOnClickListener { clickListener(part) }
        }
    }

    //fun menampilkan data ke tampilan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_list, parent, false)
        return ViewHolder(itemView)
    }

    //fun untuk menghitung keseluruhan data
    override fun getItemCount(): Int {
        return itemsCells.size
    }

    //fun menampilkan data yang diklik
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsCells[position], clickListener)
        val user: MhsEntity = itemsCells[position]
        holder.textViewId.text = user.nim.toString()
        holder.textViewName.text = user.nama
        holder.textViewAddress.text = user.alamat
    }
}