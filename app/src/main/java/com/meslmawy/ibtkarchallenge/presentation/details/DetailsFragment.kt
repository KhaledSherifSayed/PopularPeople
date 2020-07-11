package com.meslmawy.ibtkarchallenge.presentation.details


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import androidx.transition.TransitionInflater
import com.meslmawy.ibtkarchallenge.R
import com.meslmawy.ibtkarchallenge.State
import com.meslmawy.ibtkarchallenge.databinding.FragmentDetailsBinding
import com.meslmawy.ibtkarchallenge.domain.dto.ActorDetails
import com.meslmawy.ibtkarchallenge.domain.dto.Movies
import com.meslmawy.ibtkarchallenge.domain.dto.People
import com.meslmawy.ibtkarchallenge.domain.dto.PersonImage
import com.meslmawy.ibtkarchallenge.presentation.MainActivity
import com.meslmawy.ibtkarchallenge.presentation.adapters.*
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModel()
    private val args: DetailsFragmentArgs by navArgs()
    private var peopleVal: People? = null
    private var moviesList: List<Movies>? = null
    private var moviesAdapter : MoviesAdapter? = null
    private var imagesAdapter : ImagesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        // recieved argums
        peopleVal = args.people
        moviesList = peopleVal?.known_for
        setupToolbar()
        setupLiveData()
        getPersonInfo(peopleVal!!.id)
        setupDetailsFragmentAdapters()
        displayMoviesList()
        getActorImages(peopleVal!!.id)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSharedElementTransitionOnEnter()
        binding.profileDetailBackground.apply {
            transitionName = args.picture
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_about, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)
    }



    private fun setupToolbar(){
        binding.apply {
            this.people = peopleVal
        }
        if(requireActivity() is MainActivity){
            (activity as AppCompatActivity?)!!.setSupportActionBar(binding.detailToolbar)
            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(peopleVal?.name)
        }

    }


    fun setupLiveData(){
        viewModel.personInfo.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Success -> {
                    val personInfo  = state.data
                    updateDesign(personInfo)
                }
                is State.Error -> {
                    Toast.makeText(activity, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.personImages.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Success -> {
                    if(state.data.profiles?.isNotEmpty()!!) {
                        val personImagesResponse = state.data.profiles
                        displayImagesList(personImagesResponse)
                    }
                }
                is State.Error -> {
                    Toast.makeText(activity, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setupDetailsFragmentAdapters(){
        moviesAdapter = MoviesAdapter()
        binding.moviesPicker.adapter = moviesAdapter
        binding.moviesPicker.setItemTransitionTimeMillis(500)
        binding.moviesPicker.setItemTransformer(ScaleTransformer.Builder().setMinScale(0.8f).build())
        imagesAdapter = ImagesAdapter(ImageClick{ it, view ->
            val extraInfoForSharedElement = FragmentNavigatorExtras(
                (view to it.real_path) as Pair<View, String>
            )
            val toPhotoFragment = it.real_path?.let { it1 ->
                peopleVal?.name?.let { it2 ->
                    DetailsFragmentDirections.actionDetailsFragmentToPhotoFragment(it,
                        it1, it2
                    )
                }
            }
            if (toPhotoFragment != null) {
                navigate(toPhotoFragment, extraInfoForSharedElement)
            }
        })
        binding.imagesPicker.adapter = imagesAdapter
        postponeEnterTransition()
        binding.imagesPicker.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }

    private fun getPersonInfo(id : Int){
        viewModel.getPersonInfo(id)
    }

    private fun updateDesign(person: ActorDetails){
        binding.expandTextView.text  =  person.biography
    }

    private fun displayMoviesList(){
        moviesAdapter?.submitList(moviesList)
    }

    private fun getActorImages(id: Int) {
        viewModel.getActorImages(id)
    }

    private fun displayImagesList(personImagesResponse: List<PersonImage>) {
        imagesAdapter?.submitList(personImagesResponse)
    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.shared_element_transition)
    }

    private fun navigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination, extraInfo) }
    }
}