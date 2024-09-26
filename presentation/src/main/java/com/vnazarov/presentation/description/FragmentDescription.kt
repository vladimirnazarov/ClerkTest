package com.vnazarov.presentation.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vnazarov.presentation.MainActivity
import com.vnazarov.presentation.databinding.FragmentDescriptionBinding
import com.vnazarov.presentation.description.pager.adapter.DescriptionPagerAdapter

class FragmentDescription: Fragment() {

    private lateinit var binding: FragmentDescriptionBinding
    private lateinit var pagerAdapter: DescriptionPagerAdapter

    private var listType: String? = null
    private var cardName: String? = null

    private val descriptionViewModel: DescriptionViewModel by viewModels {
        DescriptionViewModel.Factory(
            (requireActivity() as MainActivity).provideViewModel()
        )
    }

    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listType = arguments?.getString(LIST_TYPE)
        cardName = arguments?.getString(CARD_NAME)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addBackCallback()
        setupPager()
    }

    override fun onDestroy() {
        super.onDestroy()

        removeBackCallback()
    }

    private fun setupPager() {
        val list = descriptionViewModel.getListByType(listType!!)

        pagerAdapter = DescriptionPagerAdapter(list, listType!!, this)

        binding.descriptionPager.apply {
            adapter = pagerAdapter
            layoutDirection = View.LAYOUT_DIRECTION_LTR
            setCurrentItem(list.indexOf(list.find { it.name == cardName }), false)
        }
    }

    private fun addBackCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(backCallback)

        binding.descriptionToolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun removeBackCallback() {
        backCallback.remove()
    }

    companion object {
        private const val LIST_TYPE = "list_type"
        private const val CARD_NAME = "card_name"
    }
}