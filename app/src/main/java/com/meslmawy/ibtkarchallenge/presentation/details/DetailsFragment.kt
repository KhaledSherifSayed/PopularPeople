package com.meslmawy.ibtkarchallenge.presentation.details

import android.app.Person
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import com.meslmawy.ibtkarchallenge.R
import com.meslmawy.ibtkarchallenge.State
import com.meslmawy.ibtkarchallenge.databinding.DetailsFragmentBinding
import com.meslmawy.ibtkarchallenge.domain.dto.ActorDetails
import com.meslmawy.ibtkarchallenge.domain.dto.People
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


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
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.detailToolbar)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.apply {
            this.people = peopleVal
        }
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(peopleVal?.name)
        setupLiveData()
        getPersonInfo(peopleVal!!.id)
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_about, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)
    }

   fun getPersonInfo(id : Int){
     viewModel.getPersonInfo(id)
    }

    fun setupLiveData(){
        viewModel.personInfo.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Success -> {
                    val PersonInfo  = state.data
                    updateDesign(PersonInfo)
                }
                is State.Error -> {
                    Toast.makeText(activity, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun updateDesign(person: ActorDetails){
        binding.detailDescription.text =  person.biography
    }

}