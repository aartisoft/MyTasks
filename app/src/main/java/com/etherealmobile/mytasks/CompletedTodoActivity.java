package com.etherealmobile.mytasks;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.DataType;
import p32929.androideasysql_library.EasyDB;

public class CompletedTodoActivity extends AppCompatActivity {

    private String todoTextsDb;
    private ArrayList<TodoModel> todos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_todo);

        final EasyDB completedListDB = EasyDB.init(this, "TODODBCOMPLETED", null, 1)
                .setTableName("completedListDB")
                .addColumn(new Column("COMPLETED", new DataType()._text_().notNull().done()))
                .doneTableColumn();

        Cursor cursor = completedListDB.getAllData();
        //todos.clear();
        while(cursor.moveToNext()){
            todoTextsDb = cursor.getString( 1 );
            todos.add(new TodoModel(todoTextsDb));
        }

        RecyclerView recyclerView = findViewById(R.id.completed_recyclerView);

        final TodoAdapter todoAdapter = new TodoAdapter(todos, this);
        recyclerView.setAdapter(todoAdapter);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        todoAdapter.notifyDataSetChanged();
    }
}
