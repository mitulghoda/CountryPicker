package com.dev.countrypicker.bottomsheet.ui

import android.app.Dialog
import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.content.DialogInterface.OnShowListener
import android.os.Bundle
import android.view.WindowManager
import android.content.DialogInterface
import android.content.res.Resources
import android.view.View
import com.dev.countrypicker.bottomsheet.listner.IObjectCallback
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseBottomSheet : BottomSheetDialogFragment(), OnShowListener {
    private var expanded = false
    private var showKeyboard = false
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private var dismissIObjectCallback: IObjectCallback<Boolean>? = null
    private var bottomSheet: View? = null
    fun setDismissIObjectCallback(dismissIObjectCallback: IObjectCallback<Boolean>?) {
        this.dismissIObjectCallback = dismissIObjectCallback
    }

    private val bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener(this)
        val window = dialog.window
        if (window != null) {
            window.setDimAmount(0.25f)
            if (showKeyboard) window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
        return dialog
    }

    fun setExpanded(expanded: Boolean) {
        this.expanded = expanded
    }

    fun setKeyboard(isVisible: Boolean) {
        showKeyboard = isVisible
    }

    fun hideBottomSheet() {
        if (bottomSheetBehavior == null) return
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        dismiss()
    }

    override fun onShow(dialogInterface: DialogInterface) {
        bottomSheetBehavior = getBottomSheetBehaviour(dialogInterface as Dialog)
        if (bottomSheetBehavior == null) return
        bottomSheetBehavior!!.addBottomSheetCallback(bottomSheetCallback)
        if (!expanded) return
        bottomSheetBehavior!!.peekHeight =
            Resources.getSystem().displayMetrics.heightPixels
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetBehaviour(dialog: Dialog?): BottomSheetBehavior<View>? {
        if (dialog == null) return null
        bottomSheet = dialog.findViewById(R.id.design_bottom_sheet)
        return if (bottomSheet == null) null else BottomSheetBehavior.from(bottomSheet!!)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        updateDismissedListener()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        updateDismissedListener()
    }

    override fun dismiss() {
        updateDismissedListener()
        super.dismiss()
    }

    fun dismissIntended() {
        dismissIObjectCallback = null
        dismiss()
    }

    private fun updateDismissedListener() {
        if (dismissIObjectCallback == null) return
        dismissIObjectCallback!!.response(true)
        dismissIObjectCallback = null
    }
}