/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cupcake.databinding.FragmentSummaryBinding
import com.example.cupcake.model.OrderViewModel

// TODO 6: SummaryFragment ini berisi ringkasan detail pesanan dengan tombol untuk membagikan pesanan melalui aplikasi lain
class SummaryFragment : Fragment() {

    // Menggunakan variabel binding objek instance dengan hak akses private yang sesuai dengan layout fragment_summary.xml
    // Properti ini bukan nol antara pemanggilan kembali siklus fungsi onCreateView() dan onDestroyView() ketika hierarki tampilan dilampirkan ke fragmen.
    private var binding: FragmentSummaryBinding? = null

    // Menggunakan delegasi variabel sharedViewModes kotlin 'by activityViewModels()' dari artefak fragmen-ktx
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSummaryBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            summaryFragment = this@SummaryFragment
        }
    }

    // Fungsi yang digunakan untuk mengirim pesanan dengan membagikan detail pesanan ke aplikasi lain melalui maksud implisit
    // Lalu digunakan toast untuk menampilkan pesan 'Send Order' ketika button Send Order to Another App
    fun sendOrder() {
        Toast.makeText(activity, "Send Order", Toast.LENGTH_SHORT).show()
    }

    //Metode siklus fungsi fragmen ini dipanggil saat hierarki tampilan yang terkait dengan fragmen sedang dihapus.
    //Akibatnya, dilakukan pembersihan objek yang mengikat.
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}