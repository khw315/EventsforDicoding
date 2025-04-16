package khw15.eventsdicoding.ui.fragments

import khw15.eventsdicoding.utils.Result

class FavoriteFragment : BaseEventListFragment() {

    override fun observeEvents() {
        viewModel.getFavoriteEvents().observe(viewLifecycleOwner) { list ->
            verticalAdapter.setLoadingState(false)
            verticalAdapter.submitList(list)
            updateUI(isLoading = false, isEmpty = list.isEmpty())
        }
    }

    override fun performSearch(query: String) {
        viewModel.searchFavoriteEvents(query).observe(viewLifecycleOwner) { result ->
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