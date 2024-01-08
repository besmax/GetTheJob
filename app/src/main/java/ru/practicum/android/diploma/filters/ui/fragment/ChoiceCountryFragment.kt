package ru.practicum.android.diploma.filters.ui.fragment
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.content.res.AppCompatResources
//import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.findNavController
//import dagger.hilt.android.AndroidEntryPoint
//import ru.practicum.android.diploma.R
//import ru.practicum.android.diploma.core.domain.models.ErrorType
//import ru.practicum.android.diploma.core.ui.RootActivity
//import ru.practicum.android.diploma.databinding.FragmentChoiceCountryBinding
//import ru.practicum.android.diploma.filters.presentation.state.ChoiceCountryScreenState
//import ru.practicum.android.diploma.filters.presentation.viewmodel.ChoiceCountryViewModel
//import ru.practicum.android.diploma.filters.ui.adapter.CountryAdapter
//import ru.practicum.android.diploma.util.BindingFragment
//import ru.practicum.android.diploma.util.ToolbarUtils
//
//@AndroidEntryPoint
//class ChoiceCountryFragment : BindingFragment<FragmentChoiceCountryBinding>() {
//
//    private val viewModel: ChoiceCountryViewModel by viewModels()
//
//    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentChoiceCountryBinding =
//        FragmentChoiceCountryBinding.inflate(inflater, container, false)
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        configureToolbar()
//
//        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->
//            when (screenState) {
//                is ChoiceCountryScreenState.Loading -> showLoading()
//                is ChoiceCountryScreenState.Content -> showContent(screenState)
//                is ChoiceCountryScreenState.Error -> showError(screenState.message)
//            }
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        //(requireActivity() as RootActivity).toolbar.navigationIcon = null
//    }
//
//    override fun onResume() {
//        super.onResume()
//        //(requireActivity() as RootActivity).toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
//    }
//
//    private fun configureToolbar() {
//        ToolbarUtils.configureToolbar(
//            activity = requireActivity(),
//            navController = findNavController(),
//            title = getString(R.string.header_country)
//        )
//    }
//
//    private fun showError(error: ErrorType?) {
//        when (error) {
//            ErrorType.NO_INTERNET -> {
//                with(binding) {
//                    placeholder.visibility = View.VISIBLE
//                    progressBar.visibility = View.GONE
//                    countries.visibility = View.GONE
//                    placeholderImage.setImageDrawable(
//                        AppCompatResources.getDrawable(requireContext(), R.drawable.ph_no_internet)
//                    )
//                    placeholderMessage.text = getString(R.string.error_no_internet)
//                }
//            }
//
//            else -> {
//                with(binding) {
//                    placeholder.visibility = View.VISIBLE
//                    progressBar.visibility = View.GONE
//                    countries.visibility = View.GONE
//                    placeholderImage.setImageDrawable(
//                        AppCompatResources.getDrawable(requireContext(), R.drawable.ph_error_get_list)
//                    )
//                    placeholderMessage.text = getString(R.string.error_getting_list)
//                }
//            }
//        }
//    }
//
//    private fun showLoading() {
//        with(binding) {
//            placeholder.visibility = View.GONE
//            progressBar.visibility = View.VISIBLE
//            countries.visibility = View.GONE
//        }
//    }
//
//    private fun showContent(screenState: ChoiceCountryScreenState.Content) {
//        binding.placeholder.visibility = View.GONE
//        binding.progressBar.visibility = View.GONE
//        binding.countries.visibility = View.VISIBLE
//        val adapter = CountryAdapter(
//            onCountryClick = { country ->
//                viewModel.selectCountry(country)
//                findNavController().navigateUp()
//            }
//        )
//        binding.countries.adapter = adapter
//        adapter.submitList(screenState.countries)
//    }
//
//}
