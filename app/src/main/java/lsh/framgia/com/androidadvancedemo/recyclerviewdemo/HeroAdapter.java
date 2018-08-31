package lsh.framgia.com.androidadvancedemo.recyclerviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lsh.framgia.com.androidadvancedemo.R;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.HeroViewHolder> {

    private List<Hero> mHeroes;

    public HeroAdapter(Context context) {
        mHeroes = new ArrayList<>();
        mHeroes.add(Hero.newInstance(
                context, R.string.label_scarlet_witch, R.drawable.image_scarlet_witch));
        mHeroes.add(Hero.newInstance(
                context, R.string.label_iron_man, R.drawable.image_iron_man));
        mHeroes.add(Hero.newInstance(
                context, R.string.label_wolverine, R.drawable.image_wolverine));
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_hero, parent, false);
        return new HeroViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        holder.bindData(mHeroes.get(position));
    }

    @Override
    public int getItemCount() {
        return mHeroes == null ? 0 : mHeroes.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageAvatar;
        private TextView mTextViewName;

        public HeroViewHolder(View itemView) {
            super(itemView);
            mImageAvatar = itemView.findViewById(R.id.image_avatar);
            mTextViewName = itemView.findViewById(R.id.text_view_name);
        }

        public void bindData(Hero hero) {
            mImageAvatar.setImageResource(hero.getAvatarResId());
            mTextViewName.setText(hero.getName());
        }
    }
}
