package com.sweetorangejuice.artisan.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.sweetorangejuice.artisan.R;

/**
 * Author:李易沾
 * Date:2017/5/2
 * Function:实现存放单个Fragment的抽象类，子类负责告知如何创建Fragment即可.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    /**
     * 子类必须实现如何创建子类Fragment，才能返回子类Fragment
     * @return 子类Fragment
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        //用子类创建Fragment的方法创建Fragment并置入到容器视图中
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager
                .findFragmentById(R.id.activity_single_fragment_container);
        if(fragment==null){
            fragment=createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_single_fragment_container,fragment)
                    .commit();
        }
    }
}
