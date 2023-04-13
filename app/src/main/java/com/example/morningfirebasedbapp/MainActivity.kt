package com.example.morningfirebasedbapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var  edtName:EditText
    lateinit var  edtEmail:EditText
    lateinit var  edtIdNumber: EditText
    lateinit var  btnSave:Button
    lateinit var  btnView:Button
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName  = findViewById(R.id.mEdtName)
        edtEmail = findViewById(R.id.mEdtEmail)
        edtIdNumber = findViewById(R.id.mEdtidNumber)
        btnSave = findViewById(R.id.mBtnSave)
        btnView = findViewById(R.id.mBtnView)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("saving")
        progressDialog.setMessage("please wait......")
        // The save button
        btnSave.setOnClickListener{
            //  Receiving data from the user

            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIdNumber.text.toString().trim()
            var id = System.currentTimeMillis().toString()
            //Creating error messages if the user has not input the fields
            if(name.isEmpty()){
                edtName.setError("please fill in this field")
                edtName.requestFocus()

            }else if  (email.isEmpty()){
                edtEmail.setError("please fill in this field")
                edtEmail.requestFocus()

            }else if (idNumber.isEmpty()){
                edtIdNumber.setError("please fill in this field")
                edtIdNumber.requestFocus()
            }else{
                //proceed to save
                //Prepare the usser to be saved
                var user = User(name,email,idNumber,id)
                //Create a table/child in the database
                var ref = FirebaseDatabase.getInstance().getReference().child("Users/"+id)
                //Show the dialog to start saving
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener{
                    //Dismiss the process and check if the task was successful
                    task ->
                    progressDialog.dismiss()
                    if(task.isSuccessful){
                        Toast.makeText(this,"User saved successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"User saving failed",Toast.LENGTH_LONG).show()
                    }


                }
            }


        }
        btnView.setOnClickListener{

        }
    }
}