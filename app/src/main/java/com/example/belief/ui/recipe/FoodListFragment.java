package com.example.belief.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.belief.R;
import com.example.belief.data.network.model.Food;
import com.example.belief.ui.base.BaseFragment;
import com.example.belief.ui.base.MvpView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodListFragment extends BaseFragment implements MvpView {

    @BindView(R.id.tablayout_list)
    public SwipeMenuListView listView;

    public List<Food> foodList;

    public List<Map<String, Object>> showList;

    public SimpleAdapter adapter;

    public static FoodListFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        FoodListFragment fragment = new FoodListFragment();
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
        //准备数据
        showList = new ArrayList<>();
        int id = getArguments().getInt("id");
        if (id >= 0) {
            foodList = new ArrayList<>();
            List<Food> tmp = ((FoodListActivity)getBaseActivity()).foodList;
            tmp.forEach(item -> {
                if (item.getTid() == id) {
                    Map map = new HashMap<String, Object>();
                    foodList.add(item);
                    map.put("food_name", item.getName());
                    map.put("kcal", item.getKcal().toString() + getString(R.string.food_kcal));
                    showList.add(map);
                }
            });
        }
        else {
            foodList = ((FoodListActivity)getBaseActivity()).foodList;
            foodList.forEach(item -> {
                Map map = new HashMap<String, Object>();
                map.put("food_name", item.getName());
                map.put("kcal", item.getKcal().toString() + getString(R.string.food_kcal));
                showList.add(map);
            });
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int fid = foodList.get(position).getFid();
                Intent intent = new Intent(getContext(), FoodDetailActivity.class);
                intent.putExtra("fid", fid);
                getActivity().startActivity(intent);
            }
        });

        //设置ListView
        adapter = new SimpleAdapter(getBaseActivity(), showList,
                R.layout.item_food, new String[] { "food_name",
                "kcal" }, new int[] {
                R.id.food_name,
                R.id.food_kcal });
        listView.setAdapter(adapter);
    }
}
