package packagename.todolist

import android.annotation.SuppressLint
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import packagename.todolist.databinding.ItemTodoBinding

class PurposeAdapter: RecyclerView.Adapter<PurposeAdapter.PurposeViewHolder>() {
    inner class PurposeViewHolder(private val binding: ItemTodoBinding): ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(purposes: Purposes){
            binding.apply {
                time.text = "${purposes.time}"
                purpose.text = "${purposes.purpose}"
                did1.isChecked = purposes.did != 0
                did1.setOnClickListener {
                    if(purposes.did == 0){
                        purposes.did = 1
                        did1.isChecked = true
                    } else {
                        purposes.did = 0
                        did1.isChecked = false
                    }
                    OnDidClick.invoke(purposes, this@PurposeViewHolder.adapterPosition)
                    onDeleteClick.invoke(purposes,this@PurposeViewHolder.adapterPosition)
                }
            }
        }
    }

    var models = mutableListOf<Purposes>()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurposeViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false)
        val binding = ItemTodoBinding.bind(v)
        return PurposeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurposeViewHolder, position: Int) {
        holder.bind(models[position])
    }

    override fun getItemCount() = models.size

    private var OnDidClick: (purpose:Purposes,position: Int) -> Unit = {_, _ -> }
    fun setOnDidClickListener(OnDidClick: (purpose:Purposes,position:Int) -> Unit){
        this.OnDidClick = OnDidClick
    }

    private var onDeleteClick: (purpose: Purposes, position:Int) -> Unit = {_, _ ->}
    fun setOnDeleteClickListener(onDeleteClick: (purpose:Purposes ,position:Int) -> Unit = {_, _ ->}) {
        this.onDeleteClick = onDeleteClick
    }
}