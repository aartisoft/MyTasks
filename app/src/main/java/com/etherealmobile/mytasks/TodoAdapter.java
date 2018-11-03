package com.etherealmobile.mytasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.DataType;
import p32929.androideasysql_library.EasyDB;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private ArrayList<TodoModel> todos;
    private Context context;

    public TodoAdapter(ArrayList<TodoModel> todos, Context context) {
        this.todos = todos;
        this.context = context;
    }

    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new TodoViewHolder(itemView);
    }

    public void swapItems(ArrayList<TodoModel> todos){
        this.todos = todos;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final TodoAdapter.TodoViewHolder todoViewHolder, final int position) {

        todoViewHolder.todoText.setText(todos.get(position).getTodo());

        todoViewHolder.deletedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EasyDB completedListDB = EasyDB.init(context, "TODODBCOMPLETED", null, 1)
                        .setTableName("completedListDB")
                        .addColumn(new Column("COMPLETED", new DataType()._text_().notNull().done()))
                        .doneTableColumn();
                EasyDB easyDB = EasyDB.init(context, "TODODB", null, 1)
                        .setTableName("TODO TABLE")
                        .doneTableColumn();
                easyDB.deleteRow(position);
                completedListDB.addData("COMPLETED", todos.get(position).getTodo())
                        .doneDataAdding();
            }
        });

    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        TextView todoText;
        Button deletedButton;

        public TextView getTodoText() {
            return todoText;
        }

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.item_text);
            deletedButton = itemView.findViewById(R.id.delete);
        }
    }
}
