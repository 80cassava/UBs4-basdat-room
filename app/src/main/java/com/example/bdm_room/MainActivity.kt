package com.example.bdm_room

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bdm_room.helper.MhsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //insialisasi toolbar
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

        viewRecord()//memanggil fun viewRecord

        //mengatur item tarik untuk refresh(swipe to refresh) list data
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(//mengubah bg swipe to refresh
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        itemsswipetorefresh.setOnRefreshListener {//menjalankan sorce code dibawah ketika swipe dilakukan
            viewRecord()
            itemsswipetorefresh.isRefreshing = false
        }

        //mengatur item floatingbutton untuk fun tambah data
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            createRecord()
        }
    }

    @SuppressLint("WrongConstant")
    fun viewRecord() {
        val db = MhsDatabase(this)

//        GlobalScope.launch {
//            val data = db.mhsDao().getAll()
//            data?.forEach {
//                Log.d("Database", it.toString())
//            }
//        }

        GlobalScope.launch {
            // memamnggil fungsi viewmhs dari databsehandler untuk mengambil data
            val mhs: ArrayList<MhsEntity> = db.mhsDao().getAll() as ArrayList<MhsEntity>
            val mhsArrayId = Array(mhs.size) { "0" }
            val mhsArrayName = Array(mhs.size) { "null" }
            val mhsArrayAddress = Array(mhs.size) { "null" }
            // setiap data yang didapatkan dari database akan dimasukkan ke array
            for ((index, e) in mhs.withIndex()) {
                mhsArrayId[index] = e.nim.toString()
                mhsArrayName[index] = e.nama
                mhsArrayAddress[index] = e.alamat
            }
            try {
                // membuat customadapter untuk view UI
                val recyclerView = findViewById<RecyclerView>(R.id.itemsrv)
                val adapter = MhsAdapter(mhs) { mhsItem: MhsEntity -> partItemClicked(mhsItem) }
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
                recyclerView.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL, false)
            } catch(e: Throwable) {
//                Log.i("ERROR",e)
            }
        }
    }

    @SuppressLint("InflateParams")
    fun createRecord() {
        val db = MhsDatabase(this)

        //pembuatan AlertDialog untuk menambah data
        val dialogBuilder = AlertDialog.Builder(this@MainActivity, R.style.MyDialogTheme)
        val inflater = this.layoutInflater
        //menggunakan layout custom yaitu action_dialog.xml
        val dialogView = inflater.inflate(R.layout.action_dialog, null)
        dialogBuilder.setView(dialogView)

        //insisalisasi editText yang akan muncul pada dialog box
        val etId = dialogView.findViewById(R.id.editTextId) as EditText
        val etName = dialogView.findViewById(R.id.editTextName) as EditText
        val etAddress = dialogView.findViewById(R.id.editTextAddress) as EditText

        //pengaturan tentang dialog box yang akan dibuat
        dialogBuilder.setTitle("Tambah Data")
        dialogBuilder.setMessage("Masukkan data yang sesuai")
        //ketika user menekan simpan
        dialogBuilder.setPositiveButton("Simpan", DialogInterface.OnClickListener { _, _ ->
            //mengambil data dari editText
            val id = etId.text.toString()
            val name = etName.text.toString()
            val address = etAddress.text.toString()
            //filter untuk editText kosong
            if (id.trim() != "" && name.trim() != "" && address.trim() != "") {
                //memanggil fun dari databaseHandler untuk menambah data melalui model
                GlobalScope.launch {
//                    val status =
                    db.mhsDao().insertAll(MhsEntity(0, name, address))
                }
//                if (status > -1) {
                Toast.makeText(applicationContext, "Data Tersimpan", Toast.LENGTH_LONG).show()
//                }
            } else {
                Toast.makeText(applicationContext, "Lengkapi data", Toast.LENGTH_LONG).show()
            }
        })
        dialogBuilder.setNeutralButton("Batal", DialogInterface.OnClickListener { _, _ ->
            // tidak melakukan apa2 :)
        })
        val b = dialogBuilder.create()
        b.show()
    }

    private fun partItemClicked(mhsItem: MhsEntity) {
        //membuat AlertDialog untuk menampilkan data
//        val dialogBuilder = AlertDialog.Builder(this, R.style.MyDialogTheme)
//        val inflater = this.layoutInflater
//        //menggunakan layout custom yaitu action_dialog.xml
//        val dialogView = inflater.inflate(R.layout.action_dialog, null)
//        dialogBuilder.setView(dialogView)
//
//        //insisalisasi editText yang akan muncul pada dialog box
//        val etId = dialogView.findViewById(R.id.editTextId) as EditText
//        val etName = dialogView.findViewById(R.id.editTextName) as EditText
//        val etAddress = dialogView.findViewById(R.id.editTextAddress) as EditText
//
//        //mengambil data dari Model
//        val conId = mhsItem.nim.toString()
//        val conName = mhsItem.nama
//        val conAddress = mhsItem.alamat
//
//        etId.isEnabled = false//menonaktifkan editText untuk Id
//
//        //menampilkan data ke editText
//        etId.setText(conId)
//        etName.setText(conName)
//        etAddress.setText(conAddress)
//
//        //pengaturan tentang dialog box yang akan dibuat
//        dialogBuilder.setTitle("Aksi")
//        dialogBuilder.setMessage("Pilih aksi yang akan dilakukan")
//        //ketika user menekan simpan
//        dialogBuilder.setPositiveButton("Ubah", DialogInterface.OnClickListener { _, _ ->
////            val userId = etId.text.toString()
////            val userName = etName.text.toString()
////            val userAddress = etAddress.text.toString()
////            updateRecord(userId, userName, userEmail, userAddress)
//        })
//        //ketika user menekan hapus
//        dialogBuilder.setNegativeButton("Hapus", DialogInterface.OnClickListener { _, _ ->
////            val userId = etId.text.toString()
////            deleteRecord(userId)
//        })
//        //ketika user menekan batal
//        dialogBuilder.setNeutralButton("Batal", DialogInterface.OnClickListener { _, _ ->
//            // tidak melakukan apa2 :)
//        })
//        val b = dialogBuilder.create()
//        b.show()
    }
}
