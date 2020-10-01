package krist.car.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import krist.car.R

class CustomAlertDialog(
       val title: String,
       val desc: String,
       val listener: CustomDialogListener?
): DialogFragment() {

    lateinit var titleView: TextView
    lateinit var descView: TextView
    lateinit var confirmButton: TextView
    lateinit var dismissButton: TextView
    lateinit var lis: CustomDialogListener

    init {
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.custom_dialog_layout, container, false)
        titleView = view.findViewById(R.id.dialog_title)
        descView = view.findViewById(R.id.dialog_desc)
        confirmButton = view.findViewById(R.id.dialog_confirm)
        dismissButton = view.findViewById(R.id.dialog_dismiss)

        confirmButton.setOnClickListener { lis.onConfirm() }
        dismissButton.setOnClickListener {
            dismiss()
            lis.onDismiss() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleView.text = title
        descView.text = desc
        listener?.let {
            lis = it
        }
    }


}