package com.vnazarov.presentation.cards.pager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vnazarov.presentation.cards.pager.FragmentCardsList

class CardsPagerAdapter(
    controlFragment: Fragment
): FragmentStateAdapter(controlFragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentCardsList.newInstance(LIST_TYPE_ALL)
            1 -> FragmentCardsList.newInstance(LIST_TYPE_FAVOURITE)
            else -> FragmentCardsList.newInstance(LIST_TYPE_ALL)
        }
    }

    companion object {
        private const val LIST_TYPE_ALL = "list_type_all"
        private const val LIST_TYPE_FAVOURITE = "list_type_favourite"
    }
}