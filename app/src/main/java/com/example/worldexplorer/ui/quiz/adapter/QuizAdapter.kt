package com.example.worldexplorer.ui.quiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.R
import com.example.worldexplorer.domain.models.quiz.QuizModel

/*
    Inicializamos el adapter a una emptyList y le pasamos la lista de info en una función. Lo hacemos así y no le pasamos
    la lista por parámetro para que el recyclerview se cree de primeras pero no se vea obligado a recibir los datos en el
    momento de crearse, sino que le es indiferente cuando le pasamos los datos
*/
class QuizAdapter(
    private var quizList: List<QuizModel> = emptyList(),
    private val onItemSelected: (QuizModel) -> Unit) :
    RecyclerView.Adapter<QuizViewHolder>() {


    /*
        Esto no es optimo en el caso de que vayamos modificando el recyclerView poco a poco, pero como en nuestro caso
        solo lo modificamos una vez y le pasamos los datos todos de golpe esta es la mejor forma
    */
    fun updateList(list:List<QuizModel>) {
        quizList = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        return QuizViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quiz_topic, parent, false)
        )
    }

    override fun getItemCount(): Int = quizList.size

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.render(quizList[position], onItemSelected)
    }

}