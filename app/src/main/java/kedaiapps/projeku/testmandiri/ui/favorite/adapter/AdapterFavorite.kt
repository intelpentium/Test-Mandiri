package kedaiapps.projeku.testmandiri.ui.favorite.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kedaiapps.projeku.testmandiri.BuildConfig
import kedaiapps.projeku.testmandiri.R
import kedaiapps.projeku.testmandiri.databinding.ItemMovieBinding
import kedaiapps.projeku.testmandiri.db.table.FavoriteTable
import kedaiapps.projeku.testmandiri.ext.inflate

class AdapterFavorite (
    private val onClick: (FavoriteTable) -> Unit
) : RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {

    var items: MutableList<FavoriteTable> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_movie))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as? ViewHolder)?.bind(items.getOrNull(position)!!)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView){
        private var binding = ItemMovieBinding.bind(containerView)

        fun bind(data: FavoriteTable){
            with(binding){
                Glide.with(itemView.rootView).load(BuildConfig.API_BASE_URL_IMAGE+""+data.poster_path)
                    .apply(
                        RequestOptions()
                            .transform(RoundedCorners(16))
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .dontAnimate()
                    ).into(binding.posterPath)

                binding.title.text = data.title
                binding.releaseDate.text = data.release_date
                binding.voteCount.text = "Total Rating : "+data.vote_average
                binding.voteAverage.text = "Reviews :"+data.vote_count
                binding.overview.text = data.overview

                line.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }

    fun insertData(data : List<FavoriteTable>){
        data.forEach {
            items.add(it)
            notifyDataSetChanged()
        }
    }

    fun clearData() {
        if (items.isNotEmpty()) {
            items.clear()
            notifyDataSetChanged()
        }
    }
}