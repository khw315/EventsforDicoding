package khw15.eventsdicoding.ui.fragments

import khw15.eventsdicoding.utils.Result

class FinishedFragment : BaseEventListFragment() {

    override fun observeEvents() {
        viewModel.getFinishedEvents().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    verticalAdapter.setLoadingState(true)
                    updateUI(isLoading = true)
                }
                is Result.Success -> {
                    verticalAdapter.setLoadingState(false)
                    verticalAdapter.submitList(result.data)
                    updateUI(isLoading = false, isEmpty = result.data.isEmpty())
                }
                is Result.Error -> updateUI(isLoading = false, isEmpty = true)
            }
        }
    }

    override fun performSearch(query: String) {
        viewModel.searchFinishedEvents(query).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> updateUI(isLoading = true)
                is Result.Success -> {
                    verticalAdapter.submitList(result.data)
                    updateUI(isLoading = false, isEmpty = result.data.isEmpty())
                }
                is Result.Error -> updateUI(isLoading = false, isEmpty = true)
            }
        }
    }
}
