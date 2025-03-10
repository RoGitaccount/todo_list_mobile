package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Model.Task;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnItemDeleteListener onItemDeleteListener;


    public TaskAdapter(List<Task> taskList,OnItemDeleteListener onDeleteListener) {
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

// Appliquer le style en fonction du statut
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

        holder.buttonDelete.setOnClickListener(v->
        {
            if(onItemDeleteListener != null){
                onItemDeleteListener.onItemDelete(position);
            }
        });
    }

    private boolean isDeadlinePassed(String deadline) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date taskDate = sdf.parse(deadline);
            Date currentDate = new Date();
            if (taskDate != null && taskDate.before(currentDate)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < taskList.size()) {  // Vérifier si l'index est valide
            taskList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, taskList.size());  // Mettre à jour les positions des items
        }
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, status, deadline;
        MaterialButton buttonDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title);
            description = itemView.findViewById(R.id.task_description);
            status = itemView.findViewById(R.id.task_status);
            deadline = itemView.findViewById(R.id.task_deadline);
            buttonDelete = itemView.findViewById(R.id.button_delete);
        }
    }

    public interface OnItemDeleteListener
    {
        void onItemDelete(int position);
    }
}