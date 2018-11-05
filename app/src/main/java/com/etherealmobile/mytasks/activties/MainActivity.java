package com.etherealmobile.mytasks.activties;

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

import com.etherealmobile.mytasks.R;
import com.etherealmobile.mytasks.Singletone.EasyDBSingleton;
import com.etherealmobile.mytasks.adapter.TodoAdapter;
import com.etherealmobile.mytasks.model.TodoModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public String todoTextsDb;
    public int rowID;
    private TextView whosTask, task;

    private ArrayList<TodoModel> todos = new ArrayList<>();
    private DrawerLayout drawer;
    String sName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task = findViewById(R.id.item_text);

        whosTask = findViewById(R.id.whos_task_tv);

        whosTask.setText("My Tasks");

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        EasyDBSingleton.initEasydb(this);

        Cursor cursor = EasyDBSingleton.easyDB.getAllData();
        //todos.clear();
        while (cursor.moveToNext()) {
            rowID = Integer.parseInt(cursor.getString(0));
            todoTextsDb = cursor.getString(1);

            todos.add(new TodoModel(todoTextsDb, rowID));
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View view1 = inflater.inflate(R.layout.change_name_dialogue, null);
        builder.setMessage("Set Your Name");
        final EditText changeName = view1.findViewById(R.id.change_name_et);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                ;
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_delete_all:
                        EasyDBSingleton.easyDB.deleteAllDataFromTable();
                        drawer.closeDrawer(Gravity.START);
                        MainActivity.this.recreate();
                        break;
                    case R.id.completed_list:
                        startActivity(new Intent(MainActivity.this, CompletedTodoActivity.class));
                        break;
                    case R.id.nav_credit:
                        startActivity(new Intent(MainActivity.this, CreditsActivity.class));
                        break;
                }
                return true;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.todo_recyclerView);

        final TodoAdapter todoAdapter = new TodoAdapter(todos, this);
        recyclerView.setAdapter(todoAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
                                EasyDBSingleton.easyDB.addData("TODOS", todoTextsDb)
                                        .doneDataAdding();
                                todoAdapter.notifyDataSetChanged();
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

    public void changeName() {

    }

}
