package com.fintech.denispok.fintechproject

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import java.io.Serializable

class ConfirmDialogFragment : DialogFragment() {

    interface FinishListener : Serializable {
        fun onClick()
    }

    companion object {
        fun newInstance(finishListener: FinishListener): ConfirmDialogFragment {
            val args = Bundle()
            args.putSerializable("finish_listener", finishListener)
            val fragment = ConfirmDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var finishListener: FinishListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finishListener = arguments!!.getSerializable("finish_listener") as FinishListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(R.string.confirm_dialog_message)
        builder.setPositiveButton(R.string.confirm_dialog_leave) { _, _ ->
            finishListener.onClick()
        }
        builder.setNegativeButton(R.string.confirm_dialog_stay) { _, _ ->
            dialog.cancel()
        }
        return builder.create()
    }

}