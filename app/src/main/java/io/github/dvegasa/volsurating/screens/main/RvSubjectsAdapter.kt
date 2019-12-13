package io.github.dvegasa.volsurating.screens.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.dvegasa.volsurating.R
import io.github.dvegasa.volsurating.models.SubjectRich
import kotlinx.android.synthetic.main.item_subject.view.*

/**
 * 11.12.2019
 */
class RvSubjectsAdapter(var list: ArrayList<SubjectRich>) : RecyclerView.Adapter<RvSubjectsAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return VH(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(position)
    }

    inner class VH(val v: View) : RecyclerView.ViewHolder(v) {
        @SuppressLint("SetTextI18n")
        fun bind(pos: Int) {
            v.tvSubjectName.setText(list[pos].name)
            v.tvRating.setText(list[pos].userRate.toString())
        }
    }
}