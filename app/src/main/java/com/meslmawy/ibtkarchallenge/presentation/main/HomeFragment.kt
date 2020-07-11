/*
 *
 * Copyright (c) 2020 Khaled  Sherif
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 */

package com.meslmawy.ibtkarchallenge.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import com.meslmawy.ibtkarchallenge.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
import com.meslmawy.ibtkarchallenge.R
import com.meslmawy.ibtkarchallenge.State
import com.meslmawy.ibtkarchallenge.databinding.FragmentHomeBinding
import com.meslmawy.ibtkarchallenge.presentation.adapters.PeopleClick
import com.meslmawy.ibtkarchallenge.presentation.adapters.PopularPeopleAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File


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
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        setupAdapter()
        setupLiveData()
        checkPermission()
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

    private fun checkPermission(){
        val permissionCheck = activity?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE) }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            activity?.let {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            else
                Snackbar.make(binding.root,
                    R.string.storage_perm_msg,
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.request) {
                        checkPermission()
                    }
                    .show()
        }
    }

}