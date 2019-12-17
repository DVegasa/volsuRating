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

    private var hiddenSubjects = ArrayList<SubjectRich>()

    fun updateList(list: ArrayList<SubjectRich>) {
        this.list.clear()
        this.hiddenSubjects.clear()
        notifyDataSetChanged()

        val tempList = arrayListOf<SubjectRich>()
        val tempHidden = arrayListOf<SubjectRich>()
        for (i in list.indices) {
            if (list[i].userRate == 0) {
                tempHidden.add(list[i])
            } else {
                tempList.add(list[i])
            }
        }
        this.list = ArrayList(tempList)
        this.hiddenSubjects = ArrayList(tempHidden)
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
                initHiddentTitle(v, pos)
            } else {
                initSubjectTitle(v, pos)
            }
        }

        private fun initHiddentTitle(v: View, pos: Int) {
            if (hiddenSubjects.isEmpty()) return

            v.apply {
                tvSubjectName.setText("Предметы без рейтинга: ${hiddenSubjects.size}")
                tvRating.setText("")
                tvSubjectName.setTextColor(
                    ResourcesCompat.getColor(
                        v.resources,
                        R.color.colorPrimaryDark,
                        null
                    )
                )

                setOnClickListener {
                    list.addAll(hiddenSubjects)
                    hiddenSubjects.clear()
                    notifyDataSetChanged()
                }
            }
        }

        private fun initSubjectTitle(v: View, pos: Int) {
            val subj = list[pos]

            v.apply {
                tvSubjectName.setText(subj.name)
                if (subj.userRate != 0) {
                    tvEmoji.setText(Statistics.getEmojiForSubject(subj))
                }
                tvRating.setText(subj.userRate.toString())
                tvSubjectName.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        android.R.color.black,
                        null
                    )
                )
            }
        }
    }
}