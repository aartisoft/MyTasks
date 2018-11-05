package com.etherealmobile.mytasks.activties;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.etherealmobile.mytasks.R;
import com.etherealmobile.mytasks.adapter.TodoAdapter;
import com.etherealmobile.mytasks.adapter.TodoCompletedAdapter;
import com.etherealmobile.mytasks.model.TodoModel;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.DataType;
import p32929.androideasysql_library.EasyDB;

public class CompletedTodoActivity extends AppCompatActivity {

    private String todoTextsDb;
    private ArrayList<TodoModel> todos = new ArrayList<>();

    private EasyDB completedListDB;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_todo);

        Toolbar toolbar = findViewById(R.id.app_bar_completed);
        setSupportActionBar(toolbar);

        completedListDB = EasyDB.init(this, "TODODBCOMPLETED", null, 1)
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

        final TodoCompletedAdapter todoCompletedAdapter = new TodoCompletedAdapter(todos, this);
        recyclerView.setAdapter(todoCompletedAdapter);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        todoCompletedAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected( item );
        int id = item.getItemId();
        switch (id){
            case R.id.delete_all_completed:
                completedListDB.deleteAllDataFromTable();
                CompletedTodoActivity.this.recreate();
                break;
        }
        return true;
    }
}
