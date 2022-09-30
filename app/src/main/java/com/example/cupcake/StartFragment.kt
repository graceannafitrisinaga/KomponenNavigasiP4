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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentStartBinding
import com.example.cupcake.model.OrderViewModel

/**
 * This is the first screen of the Cupcake app. The user can choose how many cupcakes to order.
 */
// TODO 3 : Memulai fragment awal yaitu fragment_start pada aplikasi
class StartFragment : Fragment() {

    // Menggunakan variabel binding objek instance dengan hak akses private yang sesuai dengan layout fragment_start.xml
    // Properti ini bukan nol antara pemanggilan kembali siklus fungsi onCreateView() dan onDestroyView() ketika hierarki tampilan dilampirkan ke fragmen.
    private var binding: FragmentStartBinding? = null

    // Menggunakan delegasi variabel sharedViewModes kotlin 'by activityViewModels()' dari artefak fragmen-ktx
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.startFragment = this
    }

    /**
     * Memulai pesanan dengan jumlah cupcake yang diinginkan dan menavigasikan ke layar berikutnya yaitu layar pilihan rasa cupcake
     */
    fun orderCupcake(quantity: Int) {
        // Memperbarui sharedViewModel dengan parameter quantity yang di isi oleh pilihan jumlah cupcake yang akan di order
        sharedViewModel.setQuantity(quantity)

        // Seleksi if yang akan dijalankan Jika belum ada rasa yang diatur dalam sharedViewModel, tetapkan vanilla sebagai rasa default
        if (sharedViewModel.hasNoFlavorSet()) {
            sharedViewModel.setFlavor(getString(R.string.vanilla))
        }

        // Mengarahkan atau menavigasikan ke tujuan fragment berikutnya yaitu flavorFragment untuk memilih rasa cupcakes
        findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
    }

    /**
     * Metode siklus fungsi fragmen ini dipanggil saat hierarki tampilan yang terkait dengan fragmen sedang dihapus.
     * Akibatnya, dilakukan pembersihan objek yang mengikat.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}