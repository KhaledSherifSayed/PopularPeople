package com.meslmawy.ibtkarchallenge.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meslmawy.ibtkarchallenge.databinding.DetailsFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.navArgs
import com.meslmawy.ibtkarchallenge.domain.dto.People
import com.meslmawy.ibtkarchallenge.ui.details.DetailsFragmentArgs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel()
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: DetailsFragmentBinding
    var peopleVal: People? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        // recieved argums
        peopleVal = args.people
        binding.apply {
            this.people = peopleVal
        }
        return binding.root
    }
}