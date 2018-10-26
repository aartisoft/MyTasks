package com.etherealmobile.mytasks;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.DataType;
import p32929.androideasysql_library.EasyDB;

public class MainActivity extends AppCompatActivity {

    public String todoTextsDb;
    private String name = "John";
    private TextView whosTask, task;

    private ArrayList<TodoModel> todos = new ArrayList<>();
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task = findViewById(R.id.item_text);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        final EasyDB easyDB = EasyDB.init(this, "TODODB", null, 1)
                .setTableName("TODO TABLE")
                .addColumn(new Column("TODOS", new DataType()._text_().done()))
                .doneTableColumn();

        Cursor cursor = easyDB.getAllData();
        //todos.clear();
        while(cursor.moveToNext()){
            todoTextsDb = cursor.getString( 1 );
            todos.add(new TodoModel(todoTextsDb));
        }



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.nav_change_name:
                        changeName();
                        break;
                    case R.id.nav_delete_all:
                        easyDB.deleteAllDataFromTable();
                        drawer.closeDrawer(Gravity.START);
                        MainActivity.this.recreate();
                        break;
                    case R.id.completed_list:
                         startActivity(new Intent(MainActivity.this, CompletedTodoActivity.class));
                }
                return true;
            }
        });

        whosTask = findViewById(R.id.whos_task_tv);

        RecyclerView recyclerView = findViewById(R.id.todo_recyclerView);

        final TodoAdapter todoAdapter = new TodoAdapter(todos, this);
        recyclerView.setAdapter(todoAdapter);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        todoAdapter.notifyDataSetChanged();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View view1 = inflater.inflate(R.layout.custom_dialogue, null);
                final EditText todoText = view1.findViewById(R.id.dialogue_et_newtask);
                builder.setView(view1)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                todoTextsDb = todoText.getText().toString();
                                easyDB.addData("TODOS", todoTextsDb)
                                        .doneDataAdding();
                                todoAdapter.notifyDataSetChanged();
//                                finish();
//                                startActivity(getIntent());
//                                overridePendingTransition(0, 0);
                                  MainActivity.this.recreate();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    public void menuDrawer(View view) {

        drawer.openDrawer(Gravity.START);

    }

    public void changeName(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View view1 = inflater.inflate(R.layout.change_name_dialogue, null);
        builder.setMessage("Set Your Name");
        final EditText changeName = view1.findViewById(R.id.change_name_et);
        builder.setView(view1)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final EasyDB easyDBName = EasyDB.init(MainActivity.this, "NAMEDB", null, 1)
                                .setTableName("NAME TABLE")
                                .addColumn(new Column("NAME", new DataType()._text_().notNull().done()))
                                .doneTableColumn();

                        easyDBName.addData("NAME", changeName.getText().toString())
                                .doneDataAdding();

                        Cursor cursorName = easyDBName.getAllData();

                        while(cursorName.moveToNext()){
                            name = cursorName.getString( 1 );
                            whosTask.setText(name + "'s Tasks");
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
