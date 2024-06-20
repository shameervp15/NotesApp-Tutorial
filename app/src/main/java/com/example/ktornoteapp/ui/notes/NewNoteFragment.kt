package com.example.ktornoteapp.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.ktornoteapp.R
import com.example.ktornoteapp.databinding.FragmentNewNoteBinding

class NewNoteFragment: Fragment(R.layout.fragment_create_account) {

    private var _binding: FragmentNewNoteBinding?= null
    val binding: FragmentNewNoteBinding?
        get() = _binding

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewNoteBinding.bind(view)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}