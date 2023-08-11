package kedaiapps.projeku.testmandiri.ui.home.adapter

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
import kedaiapps.projeku.testmandiri.ext.inflate
import kedaiapps.projeku.testmandiri.services.entity.ResponseMovieItem
import kedaiapps.projeku.testmandiri.services.entity.ResponseReviewDetail

class AdapterMovie (
    private val onClick: (ResponseMovieItem) -> Unit,
    private val onClickReview: (ResponseMovieItem) -> Unit
) : RecyclerView.Adapter<AdapterMovie.ViewHolder>() {

    var items: MutableList<ResponseMovieItem> = arrayListOf()

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

        fun bind(data: ResponseMovieItem){
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
                binding.voteCount.text = "Total Rating : "+(data.vote_average ?: "-")
                binding.voteAverage.text = "Reviews : "+(data.vote_count ?: "-")
                binding.overview.text = data.overview

                line.setOnClickListener {
                    onClick(data)
                }

                lineReview.setOnClickListener {
                    onClickReview(data)
                }
            }
        }
    }

    fun insertData(data : List<ResponseMovieItem>){
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