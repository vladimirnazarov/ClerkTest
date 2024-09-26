package com.vnazarov.presentation.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.vnazarov.domain.model.Card
import com.vnazarov.presentation.MainActivity
import com.vnazarov.presentation.R
import com.vnazarov.presentation.cards.pager.adapter.CardsPagerAdapter
import com.vnazarov.presentation.databinding.FragmentCardsBinding
import com.vnazarov.presentation.util.extensions.makeToast
import com.vnazarov.presentation.util.sealed.DialogType

class FragmentCards: Fragment() {

    private lateinit var binding: FragmentCardsBinding
    private lateinit var pagerAdapter: CardsPagerAdapter

    private val cardsViewModel: CardsViewModel by viewModels {
        CardsViewModel.Factory(
            (requireActivity() as MainActivity).provideViewModel()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPager()

        binding.cardsToolbarSort.setOnClickListener {
            cardsViewModel.createDialog(requireContext(), DialogType.SortDialog)
        }

        binding.cardsToolbarSearch.setOnClickListener {
            cardsViewModel.createDialog(requireContext(), DialogType.SearchDialog) { query ->
                if (query.isNotEmpty()) {
                    val card = cardsViewModel.searchCardByName(query)

                    if (card != null) moveToDescription(card)
                    else makeToast(getString(R.string.card_not_found))
                }
            }
        }
    }

    private fun moveToDescription(card: Card) {
        val bundle = bundleOf(
            Pair(CARD_NAME, card.name),
            Pair(LIST_TYPE, LIST_TYPE_ALL)
        )

        findNavController().navigate(
            R.id.action_fragmentCards_to_fragmentDescription,
            bundle
        )
    }

    private fun setupPager() {
        pagerAdapter = CardsPagerAdapter(this)

        binding.cardsToolbarPager.apply {
            adapter = pagerAdapter
            layoutDirection = ViewPager2.LAYOUT_DIRECTION_LTR
        }

        TabLayoutMediator(
            binding.cardsToolbarTabs,
            binding.cardsToolbarPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.toolbar_all_cards_title)
                1 -> tab.text = resources.getString(R.string.toolbar_favourite_title)
            }
        }.attach()
    }

    companion object {
        private const val LIST_TYPE = "list_type"
        private const val LIST_TYPE_ALL = "list_type_all"
        private const val CARD_NAME = "card_name"
    }
}