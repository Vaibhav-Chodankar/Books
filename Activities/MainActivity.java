package com.example.exampractise;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DBHelper DB;
    private ArrayList<String> PostData = new ArrayList<String>();
    private Button Insert, Update, Delete, View, ViewLV;
    private EditText PostID, Author, Title, Content, Publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DB = new DBHelper(this);

        PostID = findViewById(R.id.etPostID);
        Author = findViewById(R.id.etAuthor);
        Title = findViewById(R.id.etTitle);
        Content = findViewById(R.id.etContent);
        Publish = findViewById(R.id.etPublish);

        Insert = findViewById(R.id.btnInsert);
        Update = findViewById(R.id.btnUpdate);
        Delete = findViewById(R.id.btnDelete);
        View = findViewById(R.id.btnView);
        ViewLV = findViewById(R.id.btnViewLV);

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                String authorTxt = Author.getText().toString();
                String titleTxt = Title.getText().toString();
                String contentTxt = Content.getText().toString();
                String publishTxt = Publish.getText().toString();

                boolean result = DB.insertData(authorTxt,titleTxt,contentTxt,publishTxt);
                if(result){
                    Toast.makeText(MainActivity.this,"Inserted Successfully",Toast.LENGTH_SHORT).show();
                    Author.setText("");
                    Title.setText("");
                    Content.setText("");
                    Publish.setText("");
                }
                else
                    Toast.makeText(MainActivity.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                String postIDTxt = PostID.getText().toString();
                String authorTxt = Author.getText().toString();
                String titleTxt = Title.getText().toString();
                String contentTxt = Content.getText().toString();
                String publishTxt = Publish.getText().toString();

                boolean result = DB.updateData(postIDTxt,authorTxt,titleTxt,contentTxt,publishTxt);
                if(result){
                    Toast.makeText(MainActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                    PostID.setText("");
                    Author.setText("");
                    Title.setText("");
                    Content.setText("");
                    Publish.setText("");
                }
                else
                    Toast.makeText(MainActivity.this, "Updation Failed", Toast.LENGTH_SHORT).show();
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                String postIDTxt = PostID.getText().toString();

                boolean result = DB.deleteData(postIDTxt);
                if(result){
                    Toast.makeText(MainActivity.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                    PostID.setText("");
                }
                else
                    Toast.makeText(MainActivity.this, "Deletion Failed", Toast.LENGTH_SHORT).show();
            }
        });

        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Cursor rows = DB.viewData();
                if(rows.getCount() > 0){
                    StringBuffer buffer = new StringBuffer();
                    while(rows.moveToNext()){
                        buffer.append("Post ID : " + rows.getInt(0) + "\n");
                        buffer.append("Author Name : " + rows.getString(1) + "\n");
                        buffer.append("Title : " + rows.getString(2) + "\n");
                        buffer.append("Content : " + rows.getString(3) + "\n");
                        buffer.append("Publish : " + rows.getString(4) + "\n\n");
                    }
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Posts");
                    alert.setCancelable(true);
                    alert.setMessage(buffer.toString());
                    alert.show();
                }
                else
                    Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            }
        });

        ViewLV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Cursor rows = DB.viewData();
                if(rows.getCount() > 0){
                    while(rows.moveToNext()){
                        PostData.add(rows.getInt(0) + ": \t" + rows.getString(2) + "\nby " + rows.getString(1) + "\ndescription: " + rows.getString(3) + ",\t" + rows.getString(4));
                    }
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("Post Data",PostData);
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}