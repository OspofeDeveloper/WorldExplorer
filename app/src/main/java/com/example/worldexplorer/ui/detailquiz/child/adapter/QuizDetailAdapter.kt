package com.example.worldexplorer.ui.detailquiz.child.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.ItemQuizOptionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class QuizDetailAdapter(
    private val context: Context,
    private val dataList: List<Pair<String, Boolean>>,
    private val onItemSelected: (Boolean) -> Unit,
) :
    BaseAdapter() {

    override fun getCount(): Int = dataList.size
    override fun getItem(position: Int): Any? = null
    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemQuizOptionBinding

        if (convertView == null) {
            /** Si convertView es nulo, inflamos la vista utilizando ViewBinding */
            binding = ItemQuizOptionBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
            /** Guardamos la referencia al ViewBinding en la etiqueta de la vista */
        } else {
            /** Si convertView no es nulo, recuperamos el ViewBinding de la etiqueta */
            binding = convertView.tag as ItemQuizOptionBinding
        }

        initGridOption(binding, position, parent)
        initListeners(binding, position, parent)

        return binding.root
    }

    private fun initListeners(
        binding: ItemQuizOptionBinding,
        position: Int,
        parent: ViewGroup?
    ) {
        val isCorrectAnswer = dataList[position].second

        binding.apply {
            cvGridOption.setOnClickListener {
                setAnswerFeedbackColors(isCorrectAnswer)
                postMarkedOptionGridBehaviour(parent, isCorrectAnswer)
                delayedCallbackOnMain(isCorrectAnswer)
            }
        }
    }

    /** Cambiamos el color de la respuesta marcada para ver si es correcta o no */
    private fun ItemQuizOptionBinding.setAnswerFeedbackColors(isCorrectAnswer: Boolean) {
        cvGridOption.setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                if (isCorrectAnswer) R.color.correct else R.color.error
            )
        )
        tvGridOption.setTextColor(Color.WHITE)
    }

    /** Desactivamos interaccion cvGridOption para no poder pulsar 2 veces y haga crash
     *  y marcamos la opcion correcta en caso de que la haya fallado */
    private fun postMarkedOptionGridBehaviour(parent: ViewGroup?, isCorrectAnswer: Boolean) {
        parent?.childCount.let {
            for (i in 0..<it!!) {
                parent?.getChildAt(i)?.isEnabled = false

                if (!isCorrectAnswer) {
                    if (dataList[i].second) {
                        val childBinding = parent?.getChildAt(i)?.tag as ItemQuizOptionBinding
                        childBinding.apply {
                            cvGridOption.setCardBackgroundColor(
                                ContextCompat.getColor(context, R.color.correct)
                            )
                            tvGridOption.setTextColor(Color.WHITE)
                        }
                    }
                }
            }
        }
    }

    /** Esperamos 1seg para que el usuario vea la respuesta y cargamos la siguiente pregunta */
    private fun delayedCallbackOnMain(isCorrectAnswer: Boolean) {
        /** Usamos Dispatchers.Main porque lo ejecutamos en el hilo principal */
        CoroutineScope(Dispatchers.Main).launch {
            delay(0)
            onItemSelected(isCorrectAnswer)
        }
    }

    /**  Iniciamos cada opcion con su medida y texto correspondiente*/
    private fun initGridOption(
        binding: ItemQuizOptionBinding,
        position: Int,
        parent: ViewGroup?
    ) {
        val countryName = dataList[position].first

        val parentHeight = parent?.height ?: 0
        val itemsPerRow = 2
        val totalVerticalSpace = 32
        val itemHeight = parentHeight / (dataList.size / itemsPerRow) - totalVerticalSpace

        binding.tvGridOption.text = countryName
        binding.tvGridOption.layoutParams.height = itemHeight
    }
}