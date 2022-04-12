package com.example.deneme.ui.toDoList;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deneme.R;

public class ToDoListFragment extends Fragment {

    public static ToDoListFragment newInstance() { return new ToDoListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_to_do_list, container, false);
        return root;
    }
}