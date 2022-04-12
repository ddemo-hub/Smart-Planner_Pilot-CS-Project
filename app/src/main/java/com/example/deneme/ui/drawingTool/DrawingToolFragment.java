package com.example.deneme.ui.drawingTool;

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

public class DrawingToolFragment extends Fragment {

    public static DrawingToolFragment newInstance() {
        return new DrawingToolFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_drawing_tool, container, false);
        return root;
    }
}