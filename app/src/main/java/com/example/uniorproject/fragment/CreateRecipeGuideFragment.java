package com.example.uniorproject.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.uniorproject.MainActivity;
import com.example.uniorproject.R;
import com.example.uniorproject.adapter.RecipeCreatorAdapter;
import com.example.uniorproject.databinding.FragmentCreateRecipeGuideBinding;
import com.example.uniorproject.databinding.FragmentCreateRecipeIngredientsBinding;
import com.example.uniorproject.noDb.NoDb;

public class CreateRecipeGuideFragment extends Fragment {

    private RecipeCreatorAdapter recipeCreatorAdapter;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentCreateRecipeGuideBinding binding = FragmentCreateRecipeGuideBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        if(NoDb.GUIDE_LIST.size() == 0) {
            NoDb.GUIDE_LIST.add("");
        }

        recipeCreatorAdapter = new RecipeCreatorAdapter(context, NoDb.GUIDE_LIST, 2);
        binding.guideRv.setLayoutManager(new LinearLayoutManager(context));
        binding.guideRv.setAdapter(recipeCreatorAdapter);

        binding.guideRv.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < NoDb.GUIDE_LIST.size(); i++){
                    if (NoDb.GUIDE_LIST.get(i).equals("")){
                        NoDb.GUIDE_LIST.remove(i);
                    }
                }
                NoDb.GUIDE_LIST.add("");
                NoDb.PICTURE_LINK_LIST.add("");
                recipeCreatorAdapter.notifyItemInserted(NoDb.GUIDE_LIST.size());
            }
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NoDb.GUIDE_LIST.size() > 0 && !(NoDb.GUIDE_LIST.size() == 1 && NoDb.GUIDE_LIST.get(0).equals(""))) {
                    for (int i = 0; i < NoDb.GUIDE_LIST.size(); i++){
                        if (NoDb.GUIDE_LIST.get(i).equals("")){
                            NoDb.GUIDE_LIST.remove(i);
                        }
                    }
                    ((MainActivity) context).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.create_container, new SetRecipeTagsFragment(), "setTags")
                            .commit();
                }
                else{
                    Toast.makeText(getContext(), "Введите шаги!", Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.create_container, new CreateRecipeIngredientsFragment(), "setIngredients")
                        .commit();
            }
        });
        return view;
    }

    public void updateAdapter(int position){
        recipeCreatorAdapter.notifyItemChanged(position);
    }

}