package com.example.videoreminder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.videoreminder.TaskRepository;
import com.example.videoreminder.db.entity.Task;

import java.util.List;

/**
 * ViewModel for the list of tasks
 */
public class TaskListViewModel extends AndroidViewModel {

    private TaskRepository repository;
    private LiveData<PagedList<Task>> allTasks;

    public TaskListViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = new LivePagedListBuilder<>(repository.getAllTasks(), 10).build();
    }

    public LiveData<PagedList<Task>> getAllTasks(){
        return this.allTasks;
    }
}
