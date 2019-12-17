package io.github.dvegasa.volsurating.screens.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import io.github.dvegasa.volsurating.R
import io.github.dvegasa.volsurating.data_processing.Statistics
import io.github.dvegasa.volsurating.models.SubjectRich
import kotlinx.android.synthetic.main.item_subject.view.*

/**
 * 11.12.2019
 */
class RvSubjectsAdapter(private var list: ArrayList<SubjectRich>) :
    RecyclerView.Adapter<RvSubjectsAdapter.VH>() {

    private val hiddenSubjects = ArrayList<SubjectRich>()

    fun updateList(list: ArrayList<SubjectRich>) {
        this.list.clear()
        hiddenSubjects.clear()
        for (i in list.indices) {
            if (list[i].userRate != 0) {
                this.list.add(list[i])
            } else {
                hiddenSubjects.add(list[i])
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return VH(v)
    }

    override fun getItemCount() = list.size + if (hiddenSubjects.isEmpty()) 0 else 1

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(position)
    }

    inner class VH(val v: View) : RecyclerView.ViewHolder(v) {
        @SuppressLint("SetTextI18n")
        fun bind(pos: Int) {

            if (pos == list.size) {
                if (hiddenSubjects.size != 0) {
                    v.tvSubjectName.setText("Предметы без рейтинга: ${hiddenSubjects.size}")
                    v.tvRating.setText("")
                    v.tvSubjectName.setTextColor(
                        ResourcesCompat.getColor(
                            v.resources,
                            R.color.colorPrimaryDark,
                            null
                        )
                    )
                }
            } else {
                val subj = list[pos]
                v.tvSubjectName.setText(subj.name)
                v.tvEmoji.setText(Statistics.getEmojiForSubject(subj))
                if (subj.userRate <= 0) {
                    v.tvRating.setText("")
                } else {
                    v.tvRating.setText(subj.userRate.toString())
                }
            }
        }
    }
}