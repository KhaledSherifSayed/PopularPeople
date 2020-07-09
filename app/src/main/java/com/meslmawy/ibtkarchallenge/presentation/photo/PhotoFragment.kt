package com.meslmawy.ibtkarchallenge.presentation.photo

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import androidx.transition.TransitionInflater
import com.meslmawy.ibtkarchallenge.R
import com.meslmawy.ibtkarchallenge.databinding.FragmentPhotoBinding
import com.meslmawy.ibtkarchallenge.domain.dto.PersonImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PhotoFragment : Fragment() {


    private lateinit var binding: FragmentPhotoBinding
    private val viewModel: PhotoViewModel by viewModel()
    private val args: PhotoFragmentArgs by navArgs()
    private var ImageRecieved: PersonImage? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // recieved argums
        ImageRecieved = args.image
        setupInitialValues()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSharedElementTransitionOnEnter()
        binding.personImageView.apply {
            transitionName = args.picture
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_about, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(
            item
        )
    }

    private fun setupInitialValues() {
        binding.apply {
            this.image = ImageRecieved
        }
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.photoToolbar)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(args.name)
    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }
}