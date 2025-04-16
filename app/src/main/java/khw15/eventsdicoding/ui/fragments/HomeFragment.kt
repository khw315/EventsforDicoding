package khw15.eventsdicoding.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import khw15.eventsdicoding.databinding.FragmentHomeBinding
import khw15.eventsdicoding.ui.adapters.HorizontalAdapter
import khw15.eventsdicoding.ui.adapters.VerticalAdapter
import khw15.eventsdicoding.ui.viewmodels.MainViewModel
import khw15.eventsdicoding.ui.viewmodels.ViewModelFactory
import khw15.eventsdicoding.utils.Result

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var verticalAdapter: VerticalAdapter
    private lateinit var horizontalAdapter: HorizontalAdapter

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupRecyclerViews()
        observeUpcomingEvents()
        observeFinishedEvents()
    }

    private fun setupAdapters() {
        horizontalAdapter = HorizontalAdapter { event ->
            if (event.isFavorite == true) viewModel.deleteEvents(event)
            else viewModel.saveEvents(event)
        }.apply { setLoadingState(true) }

        verticalAdapter = VerticalAdapter { event ->
            if (event.isFavorite == true) viewModel.deleteEvents(event)
            else viewModel.saveEvents(event)
        }.apply { setLoadingState(true) }
    }

    private fun setupRecyclerViews() = with(binding) {
        upcomingRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = horizontalAdapter
        }

        finishedRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = verticalAdapter
        }
    }

    private fun observeUpcomingEvents() {
        viewModel.getUpcomingEvents().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    updateLoadingState(true)
                    horizontalAdapter.setLoadingState(true)
                }

                is Result.Success -> {
                    updateLoadingState(false)
                    horizontalAdapter.setLoadingState(false)
                    horizontalAdapter.submitList(result.data.take(5))
                }

                is Result.Error -> {
                    updateLoadingState(false)
                }
            }
        }
    }

    private fun observeFinishedEvents() {
        viewModel.getFinishedEvents().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    updateLoadingState(true)
                    verticalAdapter.setLoadingState(true)
                }

                is Result.Success -> {
                    updateLoadingState(false)
                    verticalAdapter.setLoadingState(false)
                    verticalAdapter.submitList(result.data.take(5))
                }

                is Result.Error -> {
                    updateLoadingState(false)
                }
            }
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
