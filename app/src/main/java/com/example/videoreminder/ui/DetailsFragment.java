package com.example.videoreminder.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.videoreminder.R;
import com.example.videoreminder.db.entity.Task;
import com.example.videoreminder.viewmodel.TaskViewModel;

import java.util.Arrays;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private TaskViewModel viewModel;
    private Task currentTask;
    private int taskId;


    public DetailsFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id", taskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        final TextView title = rootView.findViewById(R.id.task_details_title);
        final TextView description = rootView.findViewById(R.id.task_details_description);
        final View main = rootView.findViewById(R.id.details_fragment);

        if(getArguments().size() != 0) {
            Log.i("id from passed bundle", "" + getArguments().getInt("id"));
            taskId = getArguments().getInt("id");
        } else if(savedInstanceState != null) {
            Log.i("id from saved bundle", "" + savedInstanceState.getInt("id"));
            taskId = savedInstanceState.getInt("id");
        } else {
            taskId = -1;
        }

        viewModel.getTaskById(taskId).observe(this, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                currentTask = task;
                title.setText(currentTask.getTitle());
                description.setText(currentTask.getDescription());
                /*
                   setting background depending on task type
                   1 - Video notification task
                   2 - Audio notification task
                   3 - Text notification task
                    */
                if(currentTask.getType()==1){
                    main.setBackgroundResource(R.color.colorVideoTaskBackground);
                } else if(currentTask.getType()==2){
                    main.setBackgroundResource(R.color.colorAudioTaskBackground);
                } else {
                    main.setBackgroundResource(R.color.colorDefaultTaskBackground);
                }
            }
        });
        getArguments().clear();

        ImageView closeTask = rootView.findViewById(R.id.task_details_close);
        closeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_detailsFragment_to_mainFragment);
            }
        });

        ImageView deleteTask = rootView.findViewById(R.id.task_details_delete);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteTask(currentTask);
                Bundle bundle = new Bundle();
                bundle.putInt("origin", 2);
                Navigation.findNavController(v).navigate(R.id.action_detailsFragment_to_mainFragment, bundle);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
