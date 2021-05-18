package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MessageScreenActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    //private ViewPager vpMain;
    //private TabLayout tabLayout;
    //private TabsAdapter tabsAdapter;
    private Button exit,sendbutton;
    RecyclerView recyclerView;
    EditText messageText;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<String> chatMessages = new ArrayList<>();
    public  void init()
    {
        /*vpMain=(ViewPager) findViewById(R.id.vpMain);
        tabsAdapter=new TabsAdapter((getSupportFragmentManager()));
        vpMain.setAdapter((tabsAdapter));
        tabLayout=(TabLayout) findViewById((R.id.toolbar));
        tabLayout.setupWithViewPager(vpMain);*/
        auth=FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();
        exit =(Button) findViewById((R.id.btnexit));
        messageText = findViewById(R.id.edittextchat);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(chatMessages);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_screen);
        init();
        getData();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent mainIntent= new Intent(MessageScreenActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        if(currentUser==null){
            Intent mainIntent = new Intent(MessageScreenActivity.this, MainActivity.class);
            startActivity((mainIntent));
            finish();
        }
        else{
            Toast.makeText(MessageScreenActivity.this, "Login successful.", Toast.LENGTH_LONG).show();
        }
        super.onStart();
    }
    public void  sendMessage (View view){
        String messageToSend = messageText.getText().toString();
        UUID uuid = UUID.randomUUID();
        String uuidString=uuid.toString();
        FirebaseUser user = auth.getCurrentUser();
        String userEmail= user.getEmail();

        databaseReference.child("Chats").child(uuidString).child("usermessage").setValue(messageToSend);
        databaseReference.child("Chats").child(uuidString).child("usermail").setValue(userEmail);
        databaseReference.child("Chats").child(uuidString).child("usermessagetime").setValue(ServerValue.TIMESTAMP);
        messageText.setText("");
        getData();
    }
    public void  getData(){
        DatabaseReference newReference = database.getReference("Chats");
        Query query = newReference.orderByChild("usermessagetime");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                chatMessages.clear();
                for (DataSnapshot ds : datasnapshot.getChildren()){
                    HashMap<String,String> hashMap=(HashMap<String,String>) ds.getValue();
                    String usermail = hashMap.get("usermail");
                    String usermessage = hashMap.get("usermessage");
                    chatMessages.add(usermail+":"+usermessage);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()== R.id.mainLogout){
            auth.signOut();
            Intent mainIntent= new Intent(MessageScreenActivity.this,MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
        return  true;
    }*/
}