package com.seerkanyilmazz.kotlin.kotlinTest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.seerkanyilmazz.kotlin.kotlinTest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //DB connection
        var database = FirebaseDatabase.getInstance().reference

        //DB write process
        binding.addButton.setOnClickListener{
            var etEmployeeName = binding.editTextEmployeeName.text.toString()
            var etEmployeeNumber = Integer.parseInt(binding.editTextEmploeeNumber.text.toString())
            var etEmployeeSalary = Integer.parseInt(binding.editTextEmployeeSalary.text.toString())
            var etEmployeeStatus = true
            database.child(etEmployeeNumber.toString()).setValue(Personel(etEmployeeName,etEmployeeSalary,etEmployeeStatus))
        }

        //DB read process
        binding.searchButton.setOnClickListener{
            var getData = object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var sb = StringBuilder()
                    for (i in snapshot.children){
                        when(i.child("employeeStatus")?.getValue()){
                            true -> { var nameSurname = i.child("employeeName").getValue()
                                        var salary = i.child("employeeSalary").getValue()
                                        sb.append("${i.key} $nameSurname $salary \n") }
                        }
                    }
                    binding.peopleInfoView.setText(sb)
                }
                override fun onCancelled(error: DatabaseError) {

                }
            }
            database.addValueEventListener(getData)
        }
    }
}