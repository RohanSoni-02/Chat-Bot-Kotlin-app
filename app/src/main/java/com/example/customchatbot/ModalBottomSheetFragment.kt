package com.example.customchatbot

import android.content.Context
import android.os.Bundle
import android.os.Message
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ModalBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(obj: Message): ModalBottomSheetFragment {
            return ModalBottomSheetFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_modal_bottom_sheet, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}