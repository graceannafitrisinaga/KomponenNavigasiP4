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
//nama paket
package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentPickupBinding
import com.example.cupcake.model.OrderViewModel

// TODO 5 : PickupFragment digunakan agar pengguna dapat memilih tanggal pengambilan cupcake
class PickupFragment : Fragment() {

    // Menggunakan variabel binding objek instance dengan hak akses private yang sesuai dengan layout fragment_pickup.xml
    // Properti ini bukan nol antara pemanggilan kembali siklus fungsi onCreateView() dan onDestroyView() ketika hierarki tampilan dilampirkan ke fragmen.
    private var binding: FragmentPickupBinding? = null

    // Menggunakan delegasi variabel sharedViewModes kotlin 'by activityViewModels()' dari artefak fragmen-ktx
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentPickupBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            pickupFragment = this@PickupFragment
        }
    }

    // Menavigasikan atau mengarahkan ke layar berikutnya untuk ringkasan orderan yaitu summaryFragment
    fun goToNextScreen() {
        findNavController().navigate(R.id.action_pickupFragment_to_summaryFragment)
    }

    //Metode siklus fungsi fragmen ini dipanggil saat hierarki tampilan yang terkait dengan fragmen sedang dihapus.
    //Akibatnya, dilakukan pembersihan objek yang mengikat.
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}