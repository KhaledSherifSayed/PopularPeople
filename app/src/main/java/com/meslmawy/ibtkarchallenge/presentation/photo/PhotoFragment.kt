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

package com.meslmawy.ibtkarchallenge.presentation.photo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import com.meslmawy.ibtkarchallenge.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
import com.meslmawy.ibtkarchallenge.R
import com.meslmawy.ibtkarchallenge.databinding.FragmentPhotoBinding
import com.meslmawy.ibtkarchallenge.domain.dto.PersonImage
import com.meslmawy.ibtkarchallenge.presentation.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


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
        setupLiveData()
        checkImageFound()
        binding.fab.setOnClickListener {
            if (checkPermission()) {
                if (viewModel.imageFound.value!!) {
                    ImageRecieved!!.file_path?.let { it1 -> viewModel.deleteImage(it1) }
                    Toast.makeText(context,getString(R.string.image_deleted),Toast.LENGTH_LONG).show()
                }
                else {
                    binding.personImageView.buildDrawingCache()
                    val bmp: Bitmap = binding.personImageView.getDrawingCache()
                    saveImage(bmp, ImageRecieved!!.file_path)
                }
            } else
                Snackbar.make(
                    binding.root,
                    R.string.storage_perm_msg,
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.request) {
                    requestPermission()
                }
                    .show()
        }
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
        if(requireActivity() is MainActivity){
            (activity as AppCompatActivity?)!!.setSupportActionBar(binding.photoToolbar)
            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(args.name)
        }
    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }

    private fun checkImageFound() {
        ImageRecieved?.file_path?.let { viewModel.checkImageFound(it) }
    }

    private fun setupLiveData() {
        viewModel.imageFound.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                true -> {
                    binding.fab.setImageResource(R.drawable.ic_delete)
                    binding.fab.tag = R.drawable.ic_delete
                }
                false -> {
                    binding.fab.tag = R.drawable.ic_add
                    binding.fab.setImageResource(R.drawable.ic_add)
                }
            }
        })
    }

    private fun checkPermission(): Boolean {
        val permissionCheck = activity?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true
    }

    private fun requestPermission() {
        activity?.let {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        }
    }

    fun saveImage(bmp: Bitmap, filePath: String?) {
        val direct = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
                    + File.separator +
                    "PopularImagesWallper"
                    + File.separator + filePath )
        try {
            val fos = FileOutputStream(direct)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()
            Toast.makeText(context,getString(R.string.image_saved),Toast.LENGTH_LONG).show()
            viewModel.image_saved()
            context?.let { scanFile(it, Uri.fromFile(direct)) }
        } catch (e: FileNotFoundException) {
            Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        } catch (e: IOException) {
            Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun scanFile(context: Context, imageUri: Uri) {
        val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        scanIntent.data = imageUri
        context.sendBroadcast(scanIntent)
    }
}