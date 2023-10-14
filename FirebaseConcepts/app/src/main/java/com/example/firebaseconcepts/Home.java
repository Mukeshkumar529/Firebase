package com.example.firebaseconcepts;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    Button logout_btn, btn_add_topic;
    EditText edt_Topic_Name;

    ListView data_Lv;

    private Uri image_Uri;

    private  static final int IMAGE_REQUEST = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        logout_btn = findViewById(R.id.btn_logout);

        btn_add_topic = findViewById(R.id.btn_add_topic);

        edt_Topic_Name = findViewById(R.id.edt_Topic_Name);

        data_Lv = findViewById(R.id.data_lv);



        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Home.this,"Logout Successfull",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this,MainActivity.class));
                finish();
            }
        });

//This Section all about Real Time Database with Firebase
//        btn_add_topic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String txt_topic_name = edt_Topic_Name.getText().toString();
//
//                if(txt_topic_name.isEmpty()) {
//                    Toast.makeText(Home.this, "Please Enter Topic Name", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    FirebaseDatabase.getInstance().getReference().child("MS_Coding Hub").push().child("Topic Name").setValue(txt_topic_name);
//                }
//            }
//        });




        btn_add_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImage();
            }
        });



//        FirebaseDatabase.getInstance().getReference().child("MS_Coding Hub").child("Web Devlopment").setValue("HTML, CSS , JavaScript");


        HashMap<String , Object> map = new HashMap<>();
        map.put("Name","Mukesh");
        map.put("Email" , "kumar@gmail.com");


        HashMap<String , Object> map1 = new HashMap<>();
        map1.put("Name","Rajesh");
        map1.put("Email" , "rajesh@gmail.com");

        HashMap<String , Object> map2 = new HashMap<>();
        map2.put("Name","Nikesh");
        map2.put("Email" , "nikesh@gmail.com");

//        FirebaseDatabase.getInstance().getReference().child("MS_Coding Hub").child("Multiple Data").updateChildren(map);



        FirebaseDatabase.getInstance().getReference().child("Information").child("data1").updateChildren(map);
        FirebaseDatabase.getInstance().getReference().child("Information").child("data2").updateChildren(map1);
        FirebaseDatabase.getInstance().getReference().child("Information").child("data3").updateChildren(map2);



//        Retriving Data from Data Base

//        ArrayList<String> data_list = new ArrayList<>();
//
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.data_list_item, data_list);
//
//        data_Lv.setAdapter(adapter);
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("MS_Coding Hub");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                data_list.clear();
//                for(DataSnapshot snapshot1: snapshot.getChildren()){
//                    data_list.add(snapshot1.getValue().toString());
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



        ArrayList<String> data_list = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.data_list_item, data_list);
        data_Lv.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Information");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data_list.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Information_Data info_data = snapshot.getValue(Information_Data.class);
                    String data_text = info_data.getName() + ":" + info_data.getEmail();
                    data_list.add(data_text);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//        This is all about Firestore Database

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Map<String, Object> college1 = new HashMap<>();
        college1.put("Name", "AVIT");
        college1.put("District", "Kancheepuram");
        college1.put("Pin Code", 603104);
        college1.put("Country", "India");


        db.collection("states").document("College-1").set(college1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Home.this,"State data value is inserted successful",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Map<String, Object> college2 = new HashMap<>();
        college2.put("Name", "VIT College");
        college2.put("District", "Vellore");
        college2.put("Pin Code", 653106);
        college2.put("Country", "India");


        db.collection("states").document("College-2").set(college2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Home.this,"State data value is inserted successful",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Map<String, Object> college3 = new HashMap<>();
        college3.put("Name", "SRM Universiyt");
        college3.put("District", "Chennai");
        college3.put("Pin Code", 633101);
        college3.put("Country", "India");


        db.collection("states").document("College-3").set(college3).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Home.this,"State data value is inserted successful",Toast.LENGTH_SHORT).show();
                }
            }
        });


        Map<String, Object> college4 = new HashMap<>();
        college4.put("Name", "Sai University College");
        college4.put("District", "Chengalpattu");
        college4.put("Pin Code", 613105);
        college4.put("Country", "India");


        db.collection("states").document("College-4").set(college4).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Home.this,"State data value is inserted successful",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Map<String, Object> college5 = new HashMap<>();
        college5.put("Name", "Dream Institute University College");
        college5.put("District", "Panduchery");
        college5.put("Pin Code", 603106);
        college5.put("Country", "India");


        db.collection("states").document("College-5").set(college5).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Home.this,"State data value is inserted successful",Toast.LENGTH_SHORT).show();
                }
            }
        });

//        DocumentReference College1_ref = FirebaseFirestore.getInstance().collection("states").document("College-1");
//
//        College1_ref.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()){
//                DocumentSnapshot doc_College1 = task.getResult();
//                if(doc_College1.exists()){
//                    Log.d("Document", doc_College1.getData().toString());
//                }
//                else {
//                    Log.d("Document", "No Data Available");
//                }
//            }

//        });


//        FirebaseFirestore.getInstance().collection("states").document("College-3").addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
//            }
//        })





//
//        FirebaseFirestore.getInstance().collection("states").whereEqualTo("District", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()){
//                    for (QueryDocumentSnapshot college_1 : task.getResult()){
//                        Log.d("Document", college_1.getId() + "=>" + college_1.getData());
//                    }
//                }
//            }
//        });










//        DocumentReference ref = FirebaseFirestore.getInstance().collection("states").document("MKS");
//        ref.update("Capital",true);

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        Map<String, Object> my_data = new HashMap<>();
//
//        my_data.put("College", "AVIT");
//        my_data.put("State" , "Tamil Nadu");
//
//        db.collection("states").add(my_data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                if(task.isSuccessful()){
//            Toast.makeText(Home.this,"Data Value Added  Successful",Toast.LENGTH_SHORT).show();
//             }
//            }
//        });



//        capital_data.put("Capital", false);
//
//        db.collection("states").document("MKS").set(capital_data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//               Toast.makeText(Home.this,"Data Merged Successful",Toast.LENGTH_SHORT).show();
//               }
//            }
//        });






//        Map<String, Object> state = new HashMap<>();
//        state.put("Name", "Chennai");
//        state.put("District", "Kancheepuram");
//        state.put("Country", "India");
//
//
//        db.collection("states").document("MKS").set(state).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(Home.this,"State data value is inserted successful",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            image_Uri = data.getData();

            uploadImage();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
     final    ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading Image Please Wait");
        dialog.show();

        if (image_Uri != null){
            StorageReference file_Reference = FirebaseStorage.getInstance().getReference().child("Image Uploda").child(System.currentTimeMillis() + "." + getFileExtension(image_Uri));
            file_Reference.putFile(image_Uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    file_Reference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String url = image_Uri.toString();

                            Log.d("DownloadUrl",url);
                            dialog.dismiss();
                            Toast.makeText(Home.this,"Image is Uploaded Successful",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
              }
    }
}