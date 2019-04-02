package com.fintech.denispok.fintechproject.profile

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.fintech.denispok.fintechproject.R

class ConfirmDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(R.string.confirm_dialog_message)
        builder.setPositiveButton(R.string.confirm_dialog_leave) { _, _ ->
            dialog.cancel()
            fragmentManager!!.popBackStack()
        }
        builder.setNegativeButton(R.string.confirm_dialog_stay) { _, _ ->
            dialog.cancel()
        }
        return builder.create()
    }

}