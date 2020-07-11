/*
 *
 * Copyright (c) 2020 Khaled  Sherif
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 */

package com.meslmawy.ibtkarchallenge.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meslmawy.ibtkarchallenge.databinding.ItemPeopleBinding
import com.meslmawy.ibtkarchallenge.domain.dto.People


class PopularPeopleAdapter(val callback: PeopleClick) :
    ListAdapter<People, PopularPeopleAdapter.PeopleViewHolder>(DiffCallback) {


    /**
     * Callback for calculating the diff between two non-null items in a list.
     *
     * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
     * list that's been passed to `submitList`.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<People>() {
        override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
            return newItem.id == oldItem.id
        }
    }

    /**
     * ViewHolder for Groups items. All work is done by data binding.
     */
    class PeopleViewHolder(val viewDataBinding: ItemPeopleBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(listener: PeopleClick, people: People) {
            viewDataBinding.people = people
            viewDataBinding.cardview = viewDataBinding.itemContainer
            viewDataBinding.cardview?.transitionName = people.realProfilePath
            viewDataBinding.peopleclick = listener
            viewDataBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PeopleViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPeopleBinding.inflate(layoutInflater, parent, false)
                return PeopleViewHolder(binding)
            }
        }
    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs a new [ViewHolder].
     *
     * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
     * to the RecyclerView such as where on the screen it was last drawn during scrolling.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder.from(parent)
    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs to show an item.
     *
     * The ViewHolder passed may be recycled, so make sure that this sets any properties that
     * may have been set previously.
     */

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.viewDataBinding.also {
            holder.bind(callback, getItem(position))
        }
    }
}

/**
 * Click listener for Groups. By giving the block a name it helps a reader understand what it does.
 */
class PeopleClick(val block: (People,View) -> Unit) {
    /**
     * Called when a video is clicked
     * @param video the video that was clicked
     */
    fun onClick(people: People, view: View) = block(people,view)
}