package com.sweetorangejuice.artisan.view.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.controller.adapter.CategoryAdapter;
import com.sweetorangejuice.artisan.model.Category;
import com.sweetorangejuice.artisan.view.Activity.SubPlazaActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2017/5/3.
 */

public class PlazaFragment extends Fragment{

    private List<Category> mCategories=new ArrayList<>();

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Category building=new Category();
        building.setImageResId(R.drawable.ic_building);
        building.setNameResId(R.string.fragment_image_buildings);
        building.setCategoryCode(SubPlazaActivity.CategoryCode.BUILDING);
        mCategories.add(building);
        Category drawing=new Category();
        drawing.setImageResId(R.drawable.ic_drawing);
        drawing.setNameResId(R.string.fragment_image_drawings);
        drawing.setCategoryCode(SubPlazaActivity.CategoryCode.DRAWING);
        mCategories.add(drawing);
        Category handwork=new Category();
        handwork.setImageResId(R.drawable.ic_handwork);
        handwork.setNameResId(R.string.fragment_image_handwork);
        handwork.setCategoryCode(SubPlazaActivity.CategoryCode.HANDWORK);
        mCategories.add(handwork);
        Category sculpture=new Category();
        sculpture.setImageResId(R.drawable.ic_sculpture);
        sculpture.setNameResId(R.string.fragment_image_sculptures);
        sculpture.setCategoryCode(SubPlazaActivity.CategoryCode.SCULPTURE);
        mCategories.add(sculpture);
        Category graphic=new Category();
        graphic.setImageResId(R.drawable.ic_graphic);
        graphic.setNameResId(R.string.fragment_image_graphics);
        graphic.setCategoryCode(SubPlazaActivity.CategoryCode.GRAPHIC);
        mCategories.add(graphic);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_plaza,container,false);

        mRecyclerView=(RecyclerView)view.findViewById(R.id.fragment_plaza_recycler_view);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        CategoryAdapter adapter=new CategoryAdapter(mCategories);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    /**
     * newInstance:返回PlazaFragment的一个实例
     * @return  PlazaFragment的一个实例
     */
    public static Fragment newInstance(){
        return new PlazaFragment();
    }
}
