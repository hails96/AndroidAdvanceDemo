package lsh.framgia.com.androidadvancedemo.githubdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import lsh.framgia.com.androidadvancedemo.R;
import lsh.framgia.com.androidadvancedemo.githubdemo.model.User;
import lsh.framgia.com.androidadvancedemo.utils.StringUtils;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bindData(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    public void setUsers(List<User> users) {
        if (mUsers != null) mUsers.clear();
        mUsers = users;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageAvatar;
        private TextView mTextViewName;
        private TextView mTextViewScore;

        UserViewHolder(View itemView) {
            super(itemView);
            mImageAvatar = itemView.findViewById(R.id.image_avatar);
            mTextViewName = itemView.findViewById(R.id.text_view_name);
            mTextViewScore = itemView.findViewById(R.id.text_view_score);
        }

        void bindData(User user) {
            Glide.with(mContext).load(user.getAvatarUrl()).into(mImageAvatar);
            mTextViewName.setText(user.getName());
            mTextViewScore.setText(StringUtils.formatDoubleNumber(user.getScore()));
        }
    }
}
