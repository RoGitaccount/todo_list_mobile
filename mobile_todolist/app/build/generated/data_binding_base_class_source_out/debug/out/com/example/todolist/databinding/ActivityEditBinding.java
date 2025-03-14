// Generated by view binder compiler. Do not edit!
package com.example.todolist.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.todolist.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityEditBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnEditTask;

  @NonNull
  public final DatePicker editDateSelector;

  @NonNull
  public final TextView editTitle;

  @NonNull
  public final EditText inputEditTaskDescription;

  @NonNull
  public final EditText inputEditTaskTitle;

  @NonNull
  public final Spinner spinnerStatus;

  @NonNull
  public final TextView statusTitle;

  private ActivityEditBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnEditTask,
      @NonNull DatePicker editDateSelector, @NonNull TextView editTitle,
      @NonNull EditText inputEditTaskDescription, @NonNull EditText inputEditTaskTitle,
      @NonNull Spinner spinnerStatus, @NonNull TextView statusTitle) {
    this.rootView = rootView;
    this.btnEditTask = btnEditTask;
    this.editDateSelector = editDateSelector;
    this.editTitle = editTitle;
    this.inputEditTaskDescription = inputEditTaskDescription;
    this.inputEditTaskTitle = inputEditTaskTitle;
    this.spinnerStatus = spinnerStatus;
    this.statusTitle = statusTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityEditBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_edit, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityEditBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnEditTask;
      Button btnEditTask = ViewBindings.findChildViewById(rootView, id);
      if (btnEditTask == null) {
        break missingId;
      }

      id = R.id.editDateSelector;
      DatePicker editDateSelector = ViewBindings.findChildViewById(rootView, id);
      if (editDateSelector == null) {
        break missingId;
      }

      id = R.id.editTitle;
      TextView editTitle = ViewBindings.findChildViewById(rootView, id);
      if (editTitle == null) {
        break missingId;
      }

      id = R.id.inputEditTaskDescription;
      EditText inputEditTaskDescription = ViewBindings.findChildViewById(rootView, id);
      if (inputEditTaskDescription == null) {
        break missingId;
      }

      id = R.id.inputEditTaskTitle;
      EditText inputEditTaskTitle = ViewBindings.findChildViewById(rootView, id);
      if (inputEditTaskTitle == null) {
        break missingId;
      }

      id = R.id.spinnerStatus;
      Spinner spinnerStatus = ViewBindings.findChildViewById(rootView, id);
      if (spinnerStatus == null) {
        break missingId;
      }

      id = R.id.status_title;
      TextView statusTitle = ViewBindings.findChildViewById(rootView, id);
      if (statusTitle == null) {
        break missingId;
      }

      return new ActivityEditBinding((ConstraintLayout) rootView, btnEditTask, editDateSelector,
          editTitle, inputEditTaskDescription, inputEditTaskTitle, spinnerStatus, statusTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
