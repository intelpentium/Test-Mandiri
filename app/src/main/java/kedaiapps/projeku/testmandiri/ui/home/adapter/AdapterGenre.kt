package kedaiapps.projeku.testmandiri.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kedaiapps.projeku.testmandiri.R
import kedaiapps.projeku.testmandiri.databinding.ItemGenreBinding
import kedaiapps.projeku.testmandiri.ext.inflate
import kedaiapps.projeku.testmandiri.services.entity.ResponseGenre

class AdapterGenre (
    private val onClick: (ResponseGenre) -> Unit
) : RecyclerView.Adapter<AdapterGenre.ViewHolder>() {

    var items: MutableList<ResponseGenre> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_genre))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as? ViewHolder)?.bind(items.getOrNull(position)!!)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView){
        private var binding = ItemGenreBinding.bind(containerView)

        fun bind(data: ResponseGenre){
            with(binding){

                binding.name.text = data.name

                line.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }

    fun insertData(data : List<ResponseGenre>){
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