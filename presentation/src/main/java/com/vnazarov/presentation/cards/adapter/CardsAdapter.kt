package com.vnazarov.presentation.cards.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vnazarov.domain.model.Card
import com.vnazarov.presentation.R
import com.vnazarov.presentation.databinding.ItemCardBinding

class CardsAdapter(
    private var entitiesList: List<Card>,
    private val onClick: (Card) -> Unit
): RecyclerView.Adapter<CardsAdapter.CardsViewHolder>() {

    inner class CardsViewHolder(val binding: ItemCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCardBinding.inflate(inflater, parent, false)
        return CardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val item = entitiesList[position]

        holder.binding.apply {
            itemCardAction.setOnClickListener { onClick(item) }

            itemCardDescriptionNameValue.text = item.name
            itemCardDescriptionClassValue.text = item.classType
            itemCardDescriptionDescriptionValue.text = item.description
            itemCardDescriptionCostValue.text = item.cost.toString()

            itemCardFavourite.visibility =
                if (item.favourite) View.VISIBLE
                else View.GONE

            if (item.image != null)
                itemCardImage.load(item.image)
            else itemCardImage.setImageResource(R.drawable.ic_no_image)
        }
    }

    override fun getItemCount() = entitiesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCards(list: List<Card>) {
        entitiesList = list
        notifyDataSetChanged()
    }
}