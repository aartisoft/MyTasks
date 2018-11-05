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

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private ArrayList<TodoModel> todos;
    private Context context;
    private String TAG = "TodoAdapter";

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
    public void onBindViewHolder(@NonNull final TodoAdapter.TodoViewHolder todoViewHolder, final int position) {

        todoViewHolder.todoText.setText(todos.get(position).getTodo());

        todoViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EasyDBSingleton.initEasydb(context);
                boolean done = EasyDBSingleton.easyDB.deleteRow(todos.get(position).get_id());
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();

                EasyDBSingleton.initCOmpletedEasyDb(context);
                boolean done2 = EasyDBSingleton.completedListDB.addData("COMPLETED", todos.get(position).getTodo())
                        .doneDataAdding();

                ((Activity) context).recreate(); //To refresh the state
                Log.d(TAG, "onClick: Done: " + done);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        TextView todoText;
        CheckBox checkBox;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.item_text);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
