package com.example.task.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.task.R
import com.example.task.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.initToolbar(toolbar: Toolbar) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}

fun Fragment.showBottomSheet(
    titleDialog: Int? = null,
    titleButton: Int? = null,
    message: Int,
    onClick: () -> Unit = {}
) {

    val bottomSheet = BottomSheetDialog(requireContext(),R.style.BottomSheetDialog)
    val binding: BottomSheetBinding =
        BottomSheetBinding.inflate(layoutInflater, null, false)

    binding.textViewTitle.text = getString(titleDialog?: R.string.atencao)
    binding.textViewMessage.text = getString(message)
    binding.buttonEntendi.text = getString(titleButton?: R.string.entendi)
    binding.buttonEntendi.setOnClickListener{
        onClick()
        bottomSheet.dismiss()
    }
    bottomSheet.setContentView(binding.root)
    bottomSheet.show()

}