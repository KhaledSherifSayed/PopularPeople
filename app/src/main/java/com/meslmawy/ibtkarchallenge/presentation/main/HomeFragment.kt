package com.meslmawy.ibtkarchallenge.presentation.main

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.meslmawy.ibtkarchallenge.R
import com.meslmawy.ibtkarchallenge.State
import com.meslmawy.ibtkarchallenge.databinding.HomeFragmentBinding
import com.meslmawy.ibtkarchallenge.presentation.adapters.PeopleClick
import com.meslmawy.ibtkarchallenge.presentation.adapters.PopularPeopleAdapter
import com.meslmawy.ibtkarchallenge.ui.main.HomeFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() =
            HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: HomeFragmentBinding
    private var peopelAdapter: PopularPeopleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        binding.swipeRefreshLayout.setColorSchemeResources(
            R.color.colorPrimary
        )
        setupAdapter()
        setupLiveData()
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshAllPeople()
        }
        return binding.root
    }

    private fun setupAdapter(){
        peopelAdapter = PopularPeopleAdapter(PeopleClick {
            this.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    it
                )
            )
        })
        // Sets the adapter of the RecyclerView
        binding.peoplesItems.adapter = peopelAdapter
    }

    private fun setupLiveData(){
        viewModel.peopleLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is State.Success -> {
                    if (state.data.results?.isNotEmpty()!!)
                            peopelAdapter?.submitList(state.data.results)
                    else
                        Toast.makeText(activity, "Nodata", Toast.LENGTH_SHORT).show()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is State.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(activity, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        refreshAllPeople()
    }

    private fun refreshAllPeople(){
        viewModel.getPopularPeople()
    }


    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }

}