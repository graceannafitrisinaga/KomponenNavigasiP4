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
package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO 2 : Membuat ViewModel bersama untuk menyimpan data aplikasi dalam satu ViewModel

// Membuat variabel price_per_cupcake dengan properti private untuk menetapkan harga satu cupcake
private const val PRICE_PER_CUPCAKE = 2.00

// Membuat variabel price_for_same_day_pickup dengan properti private sebagai Biaya tambahan untuk pengambilan pesanan di hari yang sama
private const val PRICE_FOR_SAME_DAY_PICKUP = 3

// Class OrderViewModel untuk melanjutkan ViewModel agar dapat menyimpan informasi tentang pesanan cupcake dalam hal jumlah, rasa, dan tanggal pengambilan.
// Dan juga bagaimana menghitung harga total berdasarkan rincian pesanan ini.
class OrderViewModel : ViewModel() {

    // Mengubah jenis properti menjadi LiveData dan menambahkan kolom pendukung agar properti tersebut dapat diamati dan UI dapat diperbarui saat data sumber di model tampilan berubah.
    // Membuat variabel _quantity untuk menentukan jumlah cupcakes dalam pesanan
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    // Membuat variabel _flavor untuk Menentukan Rasa cupcake dalam pesanan
    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    // Membuat variabel dateOptions sebagai opsi tanggal pengambilan pesanan yang memungkinkan
    val dateOptions: List<String> = getPickupOptions()

    // Membuat variabel _date sebagai tanggal pengambilan pesanan
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    // Membuat variabel _price sebagai harga pesanan setelah memilih jumlah, rasa, dan tanggal pengambilan pesanan
    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        // Format harga ke dalam mata uang lokal dan mengembalikannya sebagai LiveData<String>
        NumberFormat.getCurrencyInstance().format(it)
    }

    init {
        // Menetapkan nilai awal untuk pesanan
        resetOrder()
    }

    // Fungsi setQuantity yang digunakan untuk Menetapkan jumlah cupcakes yang di pesan dimana variabel numberCupcake sesuai pesanan menggunakan tipe data Int
    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
    }

    // Fungsi setFlavor digunakan untuk Mengatur rasa cupcakes dalam pesanan.
    // Hanya 1 rasa yang dapat dipilih untuk seluruh pesanan. variabel desiredFlavor adalah rasa cupcake sebagai string
    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    // Fungsi setDate digunakan untuk menetapkan tanggal pengambilan pesanan.
    // variabel pickupDate adalah tanggal pengambilan sebagai string
    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    // Fungsi hasNoFlavorSet digunakan untuk Mengembalikan nilai true jika rasa belum dipilih untuk pesanan.
    // Mengembalikan false sebaliknya.
    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    // Fungsi resetOrder digunakan untuk mengatur ulang pesanan dengan menggunakan nilai default awal untuk kuantitas, rasa, tanggal, dan harga.
    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    // Fungsi updatePrice digunakan untuk Memperbarui harga berdasarkan detail pesanan.
    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        // Seleksi if digunakan Jika pengguna memilih opsi pertama (hari ini) untuk pengambilan, maka dikenakan biaya tambahan
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

    // Fungsi getPickupOption digunakan untuk Mengembalikan daftar opsi tanggal yang dimulai dengan tanggal saat ini dan 3 tanggal berikut.
    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }
}