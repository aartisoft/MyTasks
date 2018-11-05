package com.etherealmobile.mytasks.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.etherealmobile.mytasks.R;
import com.etherealmobile.mytasks.Singletone.EasyDBSingleton;
import com.etherealmobile.mytasks.model.TodoModel;

import java.util.ArrayList;

public class TodoCompletedAdapter extends RecyclerView.Adapter<TodoCompletedAdapter.TodoViewHolder> {

    private ArrayList<TodoModel> todos;
    private Context context;
    private String TAG = "TodoAdapter";

    public TodoCompletedAdapter(ArrayList<TodoModel> todos, Context context) {
        this.todos = todos;
        this.context = context;
    }

    @NonNull
    @Override
    public TodoCompletedAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_completed, viewGroup, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TodoCompletedAdapter.TodoViewHolder todoViewHolder, final int position) {

        todoViewHolder.todoText.setText(todos.get(position).getTodo());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        TextView todoText;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.item_text);
        }
    }
}
