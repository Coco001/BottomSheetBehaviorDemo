package com.coco.bottomsheetbehaviordemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * BottomSheetBehavior可以轻松实现底部动作条功能，底部动作条的引入需要在布局添加app:layout_behavior属性，
 * 并将这个布局作为CoordianatorLayout的子View。这个可以用于一些从下面弹出选项的操作。
 * <p>
 * 方法	用途
 * setPeekHeight	        默认显示后View露头的高度
 * getPeekHeight	        @see setPeekHeight()
 * setHideable	            设置是否可以隐藏，如果为true,表示状态可以为STATE_HIDDEN
 * isHideable	            @see setHideable()
 * setState	                设置状态;设置不同的状态会影响BottomSheetView的显示效果
 * getState	                获取状态
 * setBottomSheetCallback	设置状态改变回调
 *
 */
public class MainActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    RecyclerView recyclerview;
    RecyclerView.Adapter<MyViewHolder>adapter;
    List<String> texts;
    BottomSheetBehavior<View> behavior;
    private static final String TAG = "MainActivity";
    private View mBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        //设置监听bottomSheet的状态
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.i(TAG,"新状态："+newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i(TAG,"拖动操作："+slideOffset);
            }
        });
    }

    private void initData() {
        texts = new ArrayList<>();
        texts.add("测试1");
        texts.add("测试2");
        texts.add("测试3");
        texts.add("测试4");
        texts.add("测试5");
        texts.add("测试5");
        texts.add("测试5");
        //创建适配器
        adapter = new MyAdapter();
        //初始化recyclerview
        recyclerview.setAdapter(adapter);
        recyclerview.setHasFixedSize(true);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //配置bottomSheet
        behavior = BottomSheetBehavior.from(mBottomSheet);
    }

    private void initView() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        mBottomSheet = findViewById(R.id.bottom_sheet);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
    }

    //所有点击事件
    public void designClick(View view){
        switch (view.getId()){
            case R.id.floatingaction:
                int state = behavior.getState();
                if (state == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }else{
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
        }
    }

    //用于展示弹窗的list
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private SparseArray<View> array;
        public MyViewHolder(View itemView) {
            super(itemView);
            array = new SparseArray<>();
        }

        private <T extends View> T findViewById(int viewId){
            View view = array.get(viewId);
            if (view == null){
                view = itemView.findViewById(viewId);
                array.put(viewId,view);
            }
            return (T) view;
        }

        private View findView(int viewId){
            return findViewById(viewId);
        }

        public TextView getTextView(int viewid){
            return (TextView)findView(viewid);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.getTextView(R.id.text).setText(texts.get(position));
            holder.getTextView(R.id.text).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return texts.size();
        }
    };

}
