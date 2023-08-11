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
import kedaiapps.projeku.testmandiri.ext.getformatDateTime
import kedaiapps.projeku.testmandiri.ext.inflate
import kedaiapps.projeku.testmandiri.services.entity.ResponseReviewDetail

class AdapterReview (
    private val onClick: (ResponseReviewDetail) -> Unit
) : RecyclerView.Adapter<AdapterReview.ViewHolder>() {

    var items: MutableList<ResponseReviewDetail> = arrayListOf()
    var image = ""

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

        fun bind(data: ResponseReviewDetail){
            with(binding){

                Glide.with(itemView.rootView).load(BuildConfig.API_BASE_URL_IMAGE+""+image)
                    .apply(
                        RequestOptions()
                            .transform(RoundedCorners(16))
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .dontAnimate()
                    ).into(binding.posterPath)

                binding.title.text = data.author
                binding.releaseDate.text = getformatDateTime(data.updated_at, "dd-MM-yyyy")
                binding.voteCount.text = "Username : "+(data.author_details.username ?: "-")
                binding.voteAverage.text = "Rating : "+ (data.author_details.rating ?: "-")+"/10"
                binding.overview.text = data.content

                line.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }

    fun insertData(data : List<ResponseReviewDetail>){
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