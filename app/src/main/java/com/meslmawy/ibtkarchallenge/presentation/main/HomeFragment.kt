package com.meslmawy.ibtkarchallenge.presentation.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.meslmawy.ibtkarchallenge.R
import com.meslmawy.ibtkarchallenge.State
import com.meslmawy.ibtkarchallenge.databinding.FragmentHomeBinding
import com.meslmawy.ibtkarchallenge.presentation.adapters.PeopleClick
import com.meslmawy.ibtkarchallenge.presentation.adapters.PopularPeopleAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    private var peopelAdapter: PopularPeopleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setExitToFullScreenTransition()
        setReturnFromFullScreenTransition()
    }

    private fun setupAdapter(){
        peopelAdapter = PopularPeopleAdapter(PeopleClick { it,view ->
            val extraInfoForSharedElement = FragmentNavigatorExtras(
                (view to it.realProfilePath)
            )
            val toDetailsFragment = it.realProfilePath.let { it1 ->
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it,
                    it1
                )
            }
            navigate(toDetailsFragment, extraInfoForSharedElement)
        })
        // Sets the adapter of the RecyclerView
        binding.peoplesItems.adapter = peopelAdapter
        postponeEnterTransition()
        binding.peoplesItems.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
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

    private fun setExitToFullScreenTransition() {
        exitTransition = TransitionInflater.from(context).inflateTransition(R.transition.doggo_list_exit_transition)
    }

    private fun setReturnFromFullScreenTransition() {
        reenterTransition = TransitionInflater.from(context).inflateTransition(R.transition.doggo_list_return_transition)
    }

    private fun navigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination, extraInfo) }
    }
}