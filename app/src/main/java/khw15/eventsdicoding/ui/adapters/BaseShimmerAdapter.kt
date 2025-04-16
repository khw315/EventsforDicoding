package khw15.eventsdicoding.ui.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseShimmerAdapter<T, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    protected var isLoading = true

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) VIEW_TYPE_SHIMMER else VIEW_TYPE_DATA
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setLoadingState(isLoading: Boolean) {
        this.isLoading = isLoading
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (isLoading) shimmerItemCount else super.getItemCount()
    }

    protected open val shimmerItemCount = 5

    companion object {
        const val VIEW_TYPE_SHIMMER = 0
        const val VIEW_TYPE_DATA = 1
    }
}