package com.example.myrecycleviewintorecycleview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyDataAdapter extends RecyclerView.Adapter<MyDataAdapter.ItemRowHolder> {
    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    private boolean expanded = true;
    private MenuClick mMenuClick;

    public MyDataAdapter(Context context, ArrayList<SectionDataModel> dataList, MenuClick menuClick) {
        this.dataList = dataList;
        this.mContext = context;
        this.mMenuClick = menuClick;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);


        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
        itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);

        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expanded) {
                    mMenuClick.show(v);
                    itemRowHolder.recycler_view_list.setVisibility(View.VISIBLE);
                    LayoutAnimationController animation;
                    animation = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation_fall_down);
                    itemRowHolder.recycler_view_list.setLayoutAnimation(animation);

                } else {
                    mMenuClick.hide(v);
                    LayoutAnimationController animation;
                    animation = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation_from_bottom);
                    itemRowHolder.recycler_view_list.setLayoutAnimation(animation);

//                    itemRowHolder.recycler_view_list.setVisibility(View.GONE);


                }
                setExpanded(!expanded);
                expanded = isExpanded();
                notifyDataSetChanged();

            }

        });


    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        private TextView itemTitle;
        private RecyclerView recycler_view_list;
        private Button btnMore;

        private ItemRowHolder(View view) {
            super(view);
            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.btnMore = (Button) view.findViewById(R.id.btnMore);
        }

    }

    public interface MenuClick {
        void show(View v);

        void hide(View v);
    }
}
