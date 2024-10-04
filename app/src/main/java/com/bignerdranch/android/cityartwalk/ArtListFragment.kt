package com.bignerdranch.android.cityartwalk

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.cityartwalk.databinding.FragmentArtListBinding
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class ArtListFragment : Fragment() {

    private var _binding: FragmentArtListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val artListViewModel: ArtListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtListBinding.inflate(inflater, container, false)

        binding.artRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    val helpPageUri: Uri
        get() =
            Uri.parse("https://www.queensland.com/au/en/places-to-see/destinations/brisbane/where-to-find-street-art-in-brisbane")
            //Uri.parse("https://www.google.com")
            .buildUpon()
            .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                artListViewModel.arts.collect { arts ->
                    binding.artRecyclerView.adapter =
                        ArtListAdapter(arts) { artId ->
                            findNavController().navigate(
                                ArtListFragmentDirections.showArtDetail(artId)
                            )
                        }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_art_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_art -> {
                showNewArt()
                true
            }

            R.id.help -> {
                findNavController().navigate(
                    ArtListFragmentDirections.showHelp(
                        helpPageUri
                    )
                )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNewArt() {
        viewLifecycleOwner.lifecycleScope.launch {
            val newArt = Art(
                id = UUID.randomUUID(),
                title = "",
                address = "",
                date = Date(),
                latitude = 0.0,
                longitude = 0.0
            )
            artListViewModel.addArt(newArt)
            findNavController().navigate(
                ArtListFragmentDirections.showArtDetail(newArt.id)
            )
        }
    }
}