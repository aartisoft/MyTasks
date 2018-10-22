package com.etherealmobile.mytasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private ArrayList<TodoModel> todos = new ArrayList<>();
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

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder todoViewHolder, int position) {

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
