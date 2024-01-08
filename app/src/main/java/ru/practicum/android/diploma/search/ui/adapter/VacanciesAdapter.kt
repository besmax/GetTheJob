package ru.practicum.android.diploma.search.ui.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import ru.practicum.android.diploma.databinding.VacancyCardBinding
//import ru.practicum.android.diploma.search.domain.model.Vacancy
//
//class VacanciesAdapter(
//    private val onClick: (String) -> Unit
//) : RecyclerView.Adapter<VacancyViewHolder>() {
//
//    private val vacanciesList: MutableList<Vacancy> = ArrayList()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        return VacancyViewHolder(VacancyCardBinding.inflate(inflater, parent, false))
//    }
//
//    override fun getItemCount(): Int = vacanciesList.size
//
//    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
//        holder.itemView.setOnClickListener {
//            onClick(vacanciesList[position].id)
//        }
//        holder.bind(vacanciesList[position])
//    }
//
//    fun setContent(newList: List<Vacancy>) {
//        val diffCallback = VacanciesDiffCallback(vacanciesList, newList)
//        val diffVacancies = DiffUtil.calculateDiff(diffCallback)
//        vacanciesList.clear()
//        vacanciesList.addAll(newList)
//        diffVacancies.dispatchUpdatesTo(this)
//    }
//
//    fun getItemByPosition(position: Int) =
//        vacanciesList[position]
//
//}
