package com.example.todolist.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Model.Task;
import com.example.todolist.R;
import com.example.todolist.View.ActivityEdit;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnItemDeleteListener onItemDeleteListener;

    public TaskAdapter(List<Task> taskList, OnItemDeleteListener onDeleteListener) {
        this.taskList = taskList;
        this.onItemDeleteListener = onDeleteListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getTitre());
        holder.description.setText(task.getDescription());
        holder.deadline.setText("Échéance : " + task.getDeadline());

        String status = task.getStatut();
        holder.status.setText(status);

        if ("Faite".equalsIgnoreCase(status)) {
            holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.green));
            holder.status.setBackgroundColor(holder.itemView.getResources().getColor(R.color.light_green));
        } else if ("Date d'échéance dépassée".equalsIgnoreCase(status)) {
            holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.red));
            holder.status.setBackgroundColor(holder.itemView.getResources().getColor(R.color.light_red));
        } else {
            holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.orange));
            holder.status.setBackgroundColor(holder.itemView.getResources().getColor(R.color.light_orange));
        }

        holder.buttonDelete.setOnClickListener(v -> {
            if (onItemDeleteListener != null) {
                onItemDeleteListener.onItemDelete(position);
            }
        });

        holder.buttonUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ActivityEdit.class);
            intent.putExtra("taskId", task.getId());
            intent.putExtra("title", task.getTitre());
            intent.putExtra("description", task.getDescription());
            intent.putExtra("deadline", task.getDeadline());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < taskList.size()) {
            taskList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, taskList.size());
        }
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, status, deadline;
        MaterialButton buttonDelete, buttonUpdate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title);
            description = itemView.findViewById(R.id.task_description);
            status = itemView.findViewById(R.id.task_status);
            deadline = itemView.findViewById(R.id.task_deadline);
            buttonDelete = itemView.findViewById(R.id.button_delete);
            buttonUpdate = itemView.findViewById(R.id.button_update);
        }
    }

    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }
}