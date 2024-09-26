package com.vnazarov.presentation.cards.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vnazarov.domain.model.Card
import com.vnazarov.presentation.MainActivity
import com.vnazarov.presentation.R
import com.vnazarov.presentation.cards.adapter.CardsAdapter
import com.vnazarov.presentation.databinding.FragmentCardsListBinding

class FragmentCardsList: Fragment() {

    private lateinit var binding: FragmentCardsListBinding
    private lateinit var adapter: CardsAdapter

    private var listType: String? = null

    private val cardsListViewModel: CardsListViewModel by viewModels {
        CardsListViewModel.Factory(
            (requireActivity() as MainActivity).provideViewModel()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listType = arguments?.getString(LIST_TYPE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardsListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        if (listType == LIST_TYPE_FAVOURITE)
            binding.allCardsProgressHolder.visibility = View.GONE
        cardsListViewModel.observeAllCards(listType!!, viewLifecycleOwner, adapter, binding.allCardsProgressHolder)
    }

    private fun initAdapter() {
        val cards = cardsListViewModel.getListByType(listType!!)

        adapter = CardsAdapter(cards, ::moveNext)

        binding.allCardsRv.adapter = adapter
        binding.allCardsRv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun moveNext(card: Card) {
        val bundle = bundleOf(
            Pair(CARD_NAME, card.name),
            Pair(LIST_TYPE, listType)
        )

        findNavController().navigate(R.id.action_fragmentCards_to_fragmentDescription, bundle)
    }

    companion object {
        private const val LIST_TYPE = "list_type"
        private const val LIST_TYPE_FAVOURITE = "list_type_favourite"
        private const val CARD_NAME = "card_name"

        fun newInstance(listType: String) = FragmentCardsList().apply {
            arguments = Bundle().apply {
                putString(LIST_TYPE, listType)
            }
        }
    }
}