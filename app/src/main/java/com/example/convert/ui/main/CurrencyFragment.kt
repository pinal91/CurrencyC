package com.example.convert.ui.main

import android.app.Activity
import android.graphics.RectF
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.convert.R
import com.example.convert.ui.extensions.inflate
import com.example.domain.Currency
import kotlinx.android.synthetic.main.fragment_currency.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class CurrencyFragment : Fragment(){

    private val mOnValueSelectedRectF: RectF = RectF()

    private lateinit var amount: String
    private lateinit var adapter: CurrencyAdapter
    private var currenciesList: MutableList<Currency> = mutableListOf()

    private val viewModel: CurrencyViewModel by currentScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_currency)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        currencyList.layoutManager = mLayoutManager
        currencyList.setHasFixedSize(true)

        adapter = CurrencyAdapter()
        currencyList.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: CurrencyViewModel.UiModel) {

        containerProgressIndicator.visibility =
            if (model is CurrencyViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is CurrencyViewModel.UiModel.Content -> {
                currenciesList = adapter.currencies
                adapter.currencies = model.currencies

                amount = amountEditText.text.toString()

                amountEditText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                    override fun onEditorAction(
                        text: TextView,
                        actionId: Int,
                        event: KeyEvent?
                    ): Boolean {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || actionId == EditorInfo.IME_ACTION_SEND
                            || event?.action == KeyEvent.ACTION_DOWN
                            && event.keyCode == KeyEvent.KEYCODE_ENTER
                        ) {

                            amount = text.text.toString()

                            if (amount.isEmpty()) {
                                return true
                            }


                            adapter.setAmount(adapter.currencies[0], amount.toInt())
                            hideKeyboard(requireActivity())
                            return true
                        }
                        return true
                    }
                })


            }
            CurrencyViewModel.UiModel.showUI -> {
                viewModel.showUi()
            }
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }




}