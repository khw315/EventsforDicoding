package khw15.eventsdicoding.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import khw15.eventsdicoding.R
import khw15.eventsdicoding.data.local.entity.EventEntity
import khw15.eventsdicoding.databinding.ItemNormalVerticalBinding
import khw15.eventsdicoding.databinding.ItemShimmerVerticalBinding
import khw15.eventsdicoding.ui.activities.DetailActivity

class VerticalAdapter(
    private val onFavoriteClick: (EventEntity) -> Unit
) : BaseShimmerAdapter<EventEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SHIMMER) {
            val binding = ItemShimmerVerticalBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ShimmerViewHolder(binding)
        } else {
            val binding = ItemNormalVerticalBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            MyViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder && !isLoading) {
            val event = getItem(position)
            holder.bind(event)
            holder.binding.favoriteImageView.setImageResource(
                if (event.isFavorite == true) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border
            )
            holder.binding.favoriteImageView.setOnClickListener {
                onFavoriteClick(event)
            }
        }
    }

    class ShimmerViewHolder(binding: ItemShimmerVerticalBinding) :
        RecyclerView.ViewHolder(binding.root)

    class MyViewHolder(val binding: ItemNormalVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            binding.titleTextView.text = event.name
            binding.ownerTextView.text = event.ownerName
            binding.chip.text = event.category
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .transform(RoundedCorners(16))
                .into(binding.imageView)
            itemView.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_EVENT, event)
                binding.root.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}