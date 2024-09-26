package com.vnazarov.presentation.description.pager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vnazarov.domain.model.Card
import com.vnazarov.presentation.description.pager.FragmentDescriptionCard

class DescriptionPagerAdapter(
    private val entitiesList: List<Card>,
    private val listType: String,
    fragment: Fragment
): FragmentStateAdapter(fragment) {

    override fun getItemCount() = entitiesList.size

    override fun createFragment(position: Int): Fragment {
        val cardName = entitiesList[position].name!!
        return FragmentDescriptionCard.newInstance(cardName, listType)
    }
}