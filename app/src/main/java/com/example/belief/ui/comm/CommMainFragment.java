package com.example.belief.ui.comm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.belief.R;
import com.example.belief.data.network.model.ShareInfoResponse;
import com.example.belief.di.component.ActivityComponent;
import com.example.belief.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

//TODO: 拍照分享做成悬浮按钮
public class CommMainFragment extends BaseFragment implements CommMvpView {

    @Inject
    CommMvpPresenter<CommMvpView> commMvpPresenter;

    @BindView(R.id.rec_view)
    public RecyclerView recyclerView;

    public List<ShareInfoResponse> shareInfoResponseList;

    public CommListAdapter adapter;

    public static CommMainFragment newInstance() {
        Bundle args = new Bundle();
        CommMainFragment fragment = new CommMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_comm_amain, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, view));
            commMvpPresenter.onAttach(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        commMvpPresenter.getCommListData();
        adapter = new CommListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    public void setData(List data) {
        shareInfoResponseList = data;
        //初始化View
        adapter.notifyDataSetChanged();
    }

    class CommListAdapter extends RecyclerView.Adapter<CommListAdapter.ShareViewHolder> {

        @Override
        public ShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            ShareViewHolder holder = new ShareViewHolder(LayoutInflater.from(
                    getContext()).inflate(R.layout.item_comm_part, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(ShareViewHolder holder, int position)
        {
            ShareInfoResponse item = shareInfoResponseList.get(position);
            //下载图片
            commMvpPresenter.downPic(item.getPhotoUrl(), holder.mImg);
            commMvpPresenter.downPic(item.getHeadUrl(), holder.head);
            holder.mTitle.setText(item.getTitle());
            holder.mAuthor.setText(item.getAuthor());
        }

        @Override
        public int getItemCount()
        {
            return shareInfoResponseList.size();
        }

        class ShareViewHolder extends RecyclerView.ViewHolder{

            private ImageView mImg;
            private ImageView head;
            private TextView mTitle;
            private TextView mAuthor;

            public ShareViewHolder(View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.share_title);
                mImg = (ImageView) itemView.findViewById(R.id.share_img);
                //FIXME: 用glide变圆
                head = itemView.findViewById(R.id.share_head);
                mAuthor = (TextView) itemView.findViewById(R.id.share_author);
            }
        }
    }
}
