package com.example.belief.ui.sport;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.data.network.model.SportClass;
import com.example.belief.ui.base.BaseFragment;
import com.example.belief.ui.base.MvpView;
import com.example.belief.utils.UIUtils;
import com.flyco.dialog.widget.NormalDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowClassListFragment extends BaseFragment implements MvpView {

    @BindView(R.id.tablayout_list)
    public SwipeMenuListView listView;

    public List<SportClass> classList;

    public List<Map<String, Object>> showList;

    public SimpleAdapter adapter;

    public NormalDialog mdialog;

    public int willDelClass;

    public int willDelPos;

    public int business;

    public static ShowClassListFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        ShowClassListFragment fragment = new ShowClassListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayout_list, container, false);
        setUnbinder(ButterKnife.bind(this, view));
        setUp(view);
        return view;
    }

    @Override
    protected void setUp(View view) {

        business = ((ShowClassActivity)getBaseActivity()).business;

        //准备数据
        showList = new ArrayList<>();
        int id = getArguments().getInt("id");
        if (id >= 0) {
            classList = new ArrayList<>();
            List<SportClass> tmp = ((ShowClassActivity)getBaseActivity()).classList;
            tmp.forEach(item -> {
                if (item.getType() == id) {
                    Map map = new HashMap<String, Object>();
                    classList.add(item);
                    map.put("class_name", item.getName());
                    map.put("class_time", item.getTime().toString() + "分钟");
                    showList.add(map);
                }
            });
        }
        else {
            classList = ((ShowClassActivity)getBaseActivity()).classList;
            classList.forEach(item -> {
                Map map = new HashMap<String, Object>();
                map.put("class_name", item.getName());
                map.put("class_time", item.getTime().toString() + "分钟");
                showList.add(map);
            });
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int scid = classList.get(position).getScid();
                Intent intent = new Intent(getContext(), ClassDetailActivity.class);
                intent.putExtra("scid", scid);
                getActivity().startActivity(intent);
            }
        });

        //设置ListView
        adapter = new SimpleAdapter(getBaseActivity(), showList,
                R.layout.item_show_class, new String[] { "class_name",
                "class_time" }, new int[] {
                R.id.show_class_name,
                R.id.show_class_time });
        listView.setAdapter(adapter);

        if (business == 1) {
            showJoinedClass();
        }

    }

    private void showJoinedClass() {

        //设置对话框
        mdialog = UIUtils.getNormalDialog("确认删除", getBaseActivity());
        mdialog.setOnBtnClickL(() -> { mdialog.dismiss(); }, () -> {
            //删除课程
            ((ShowClassActivity)getBaseActivity()).sportMvpPresenter
                    .deleteJoinedClass(MvpApp.get(getBaseActivity()).getCurUser().getUid(), willDelClass, willDelPos,
                            showList, classList, mdialog, adapter);

        });

        listView.setMenuCreator(menu -> {
            // create "delete" item
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                    0x3F, 0x25)));
            // set item width
            deleteItem.setWidth(dp2px(90));
            // set a icon
            deleteItem.setIcon(R.drawable.ic_delete);
            // add to menu
            menu.addMenuItem(deleteItem);
        });

        listView.setOnMenuItemClickListener(
                (int position, SwipeMenu menu, int index) -> {
                    switch (index) {
                        case 0: {
                            willDelClass = classList.get(position).getScid();
                            willDelPos = position;
                            mdialog.show();
                            break;
                        }
                    }
                    // false : close the menu; true : not close the menu
                    return false;
                });

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
