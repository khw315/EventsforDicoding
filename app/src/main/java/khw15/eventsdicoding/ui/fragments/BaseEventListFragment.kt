package khw15.eventsdicoding.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import khw15.eventsdicoding.databinding.FragmentEventListBinding
import khw15.eventsdicoding.ui.adapters.VerticalAdapter
import khw15.eventsdicoding.ui.viewmodels.MainViewModel
import khw15.eventsdicoding.ui.viewmodels.ViewModelFactory

abstract class BaseEventListFragment : Fragment() {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!

    protected lateinit var verticalAdapter: VerticalAdapter
    protected val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeEvents()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        verticalAdapter = VerticalAdapter { event ->
            if (event.isFavorite == true) {
                viewModel.deleteEvents(event)
            } else {
                viewModel.saveEvents(event)
            }
        }.apply { setLoadingState(true) }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = verticalAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupSearchView() = with(binding) {
        searchView.setupWithSearchBar(searchBar)
        searchView.editText.setOnEditorActionListener { _, _, _ ->
            val query = searchView.text.toString()
            searchBar.setText(query)
            searchView.hide()
            performSearch(query)
            false
        }
    }

    protected fun updateUI(isLoading: Boolean, isEmpty: Boolean = false) = with(binding) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        noDataFoundLottie.visibility = if (isEmpty && !isLoading) View.VISIBLE else View.GONE
        recyclerView.visibility = if (!isEmpty && !isLoading) View.VISIBLE else View.VISIBLE
    }

    abstract fun observeEvents()
    abstract fun performSearch(query: String)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}