package com.bignerdranch.android.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintent.databinding.ListItemArtBinding
import java.util.UUID

class ArtHolder(
    private val binding: ListItemArtBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(art: Art, onArtClicked: (artId: UUID) -> Unit) {
        binding.artTitle.text = art.title
        binding.artDate.text = art.date.toString()

        binding.root.setOnClickListener {
            onArtClicked(art.id)
        }
    }
}

class ArtListAdapter(
    private val arts: List<Art>,
    private val onArtClicked: (artId: UUID) -> Unit
) : RecyclerView.Adapter<ArtHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemArtBinding.inflate(inflater, parent, false)
        return ArtHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtHolder, position: Int) {
        val art = arts[position]
        holder.bind(art, onArtClicked)
    }

    override fun getItemCount() = arts.size

}