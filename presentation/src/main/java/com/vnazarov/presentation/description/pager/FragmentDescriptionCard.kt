package com.vnazarov.presentation.description.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.vnazarov.presentation.MainActivity
import com.vnazarov.presentation.R
import com.vnazarov.presentation.databinding.FragmentDescriptionCardBinding

class FragmentDescriptionCard: Fragment() {

    private lateinit var binding: FragmentDescriptionCardBinding

    private val descriptionCardViewModel: DescriptionCardViewModel by viewModels {
        DescriptionCardViewModel.Factory(
            (requireActivity() as MainActivity).provideViewModel()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_description_card, container, false)

        binding.apply {
            viewModel = descriptionCardViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = descriptionCardViewModel.getListByType(arguments?.getString(LIST_TYPE)!!)
        val card = list.find { it.name == arguments?.getString(CARD_NAME) }!!
        descriptionCardViewModel.insertCard(card)

        if (card.image != null) binding.descriptionCardImage.load(card.image)
        else binding.descriptionCardImage.setImageResource(R.drawable.ic_no_image)
    }

    companion object {
        private const val LIST_TYPE = "list_type"
        private const val CARD_NAME = "card_name"

        fun newInstance(cardName: String, listType: String) = FragmentDescriptionCard().apply {
            arguments = Bundle().apply {
                putString(CARD_NAME, cardName)
                putString(LIST_TYPE, listType)
            }
        }
    }
}