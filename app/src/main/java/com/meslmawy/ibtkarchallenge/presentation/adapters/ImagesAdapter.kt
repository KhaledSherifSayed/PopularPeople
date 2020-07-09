package com.meslmawy.ibtkarchallenge.presentation.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meslmawy.ibtkarchallenge.databinding.ItemImageBinding
import com.meslmawy.ibtkarchallenge.domain.dto.PersonImage


class ImagesAdapter(val callback: ImageClick) : ListAdapter<PersonImage, ImagesAdapter.ImagesViewHolder>(DiffCallback) {

    /**
     * Callback for calculating the diff between two non-null items in a list.
     *
     * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
     * list that's been passed to `submitList`.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<PersonImage>() {
        override fun areItemsTheSame(oldItem: PersonImage, newItem: PersonImage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PersonImage, newItem: PersonImage): Boolean {
            return newItem.file_path == oldItem.file_path
        }
    }

    /**
     * ViewHolder for Groups items. All work is done by data binding.
     */
    class ImagesViewHolder(val viewDataBinding: ItemImageBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(listener: ImageClick,image: PersonImage) {
            viewDataBinding.personImage = image
            viewDataBinding.image = viewDataBinding.imageConatiner
            viewDataBinding.image?.transitionName = image.file_path
            viewDataBinding.imageclick = listener
            viewDataBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ImagesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemImageBinding.inflate(layoutInflater, parent, false)
                return ImagesViewHolder(binding)
            }
        }
    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs a new [ViewHolder].
     *
     * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
     * to the RecyclerView such as where on the screen it was last drawn during scrolling.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        return ImagesViewHolder.from(parent)
    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs to show an item.
     *
     * The ViewHolder passed may be recycled, so make sure that this sets any properties that
     * may have been set previously.
     */

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.viewDataBinding.also {
            holder.bind(callback,getItem(position))
        }
    }
}


/**
 * Click listener for Groups. By giving the block a name it helps a reader understand what it does.
 */
class ImageClick(val block: (PersonImage, View) -> Unit) {
    /**
     * Called when a video is clicked
     * @param video the video that was clicked
     */
    fun onClick(personImage: PersonImage, view: View) = block(personImage,view)
}