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
import com.example.worldexplorer.domain.models.detailquiz.QuizOptionModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class QuizDetailAdapter(
    private val context: Context,
    private val dataList: List<QuizOptionModel>,
    private val onItemSelected: (Boolean) -> Unit,
) : BaseAdapter() {

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemQuizOptionBinding

        if (convertView == null) {
            binding = ItemQuizOptionBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemQuizOptionBinding
        }

        initGridOption(binding, position, parent)
        initListeners(binding, position, parent)

        return binding.root
    }

    private fun initGridOption(
        binding: ItemQuizOptionBinding,
        position: Int,
        parent: ViewGroup?
    ) {
        val countryName = dataList[position].name

        val parentHeight = parent?.height ?: 0
        val itemsPerRow = 2
        val totalVerticalSpace = 32
        val itemHeight = parentHeight / (dataList.size / itemsPerRow) - totalVerticalSpace

        binding.tvGridOption.text = countryName
        binding.tvGridOption.layoutParams.height = itemHeight
    }

    private fun initListeners(
        binding: ItemQuizOptionBinding,
        position: Int,
        parent: ViewGroup?
    ) {
        val isCorrectAnswer = dataList[position].isCorrect

        binding.apply {
            cvGridOption.setOnClickListener {
                setAnswerFeedbackColors(isCorrectAnswer)
                postMarkedOptionGridBehaviour(parent, isCorrectAnswer)
                delayedCallbackOnMain(isCorrectAnswer)
            }
        }
    }

    private fun ItemQuizOptionBinding.setAnswerFeedbackColors(isCorrectAnswer: Boolean) {
        cvGridOption.setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                if (isCorrectAnswer) R.color.correct else R.color.error
            )
        )
        tvGridOption.setTextColor(Color.WHITE)
    }

    private fun postMarkedOptionGridBehaviour(parent: ViewGroup?, isCorrectAnswer: Boolean) {
        parent?.childCount.let {
            for (i in 0..<it!!) {
                parent?.getChildAt(i)?.isEnabled = false

                if (!isCorrectAnswer) {
                    if (dataList[i].isCorrect) {
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

    private fun delayedCallbackOnMain(isCorrectAnswer: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            onItemSelected(isCorrectAnswer)
        }
    }

}