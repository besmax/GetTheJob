package ru.practicum.android.diploma.search.ui.adapter
//
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.resource.bitmap.FitCenter
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners
//import ru.practicum.android.diploma.R
//import ru.practicum.android.diploma.databinding.VacancyCardBinding
//import ru.practicum.android.diploma.search.domain.model.Vacancy
//import ru.practicum.android.diploma.util.getSalaryDescription
//
//class VacancyViewHolder(private val binding: VacancyCardBinding) : RecyclerView.ViewHolder(binding.root) {
//
//    fun bind(vacancy: Vacancy) {
//        with(binding) {
//            setImage(vacancy.logo)
//            val name = vacancy.name + ", " + vacancy.areaName
//            vacancyNameTextView.text = name
//            employerNameTextView.text = vacancy.employerName
//            salaryInfoTextView.text = getSalaryDescription(
//                itemView.resources,
//                vacancy.salaryFrom,
//                vacancy.salaryTo,
//                vacancy.salaryCurrency
//            )
//        }
//    }
//
//    private fun setImage(imageUrl: String?) {
//        val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.search_field_corner_radius)
//        Glide.with(itemView)
//            .load(imageUrl)
//            .transform(FitCenter(), RoundedCorners(cornerRadius))
//            .placeholder(R.drawable.ic_vacancy_placeholder)
//            .into(binding.vacancyIconImageView)
//    }
//}
