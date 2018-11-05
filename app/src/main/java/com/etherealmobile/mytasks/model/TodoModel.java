package com.etherealmobile.mytasks.model;

public class TodoModel {
    public TodoModel(String todo, int _id) {
        this.todo = todo;
        this._id = _id;
    }

    private String todo;
    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public TodoModel(String todo) {
        this.todo = todo;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
