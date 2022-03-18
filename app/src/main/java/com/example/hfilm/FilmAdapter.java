package com.example.hfilm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hfilm.Info.InfoActivity;
import com.example.hfilm.data.model.Datum;
import com.example.hfilm.login.Login;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

        private List<Datum> mItems;
        private Context mContext;
        private PostItemListener mItemListener;
        String prefname = "my_data";
        String prefnamelogin = "my_login";


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public TextView titleTv, tvTen, tvView, tvScript, tvLike;
            public ImageView imageView, imageLike;
            public Button btnXem;
            PostItemListener mItemListener;

            public ViewHolder(View itemView, PostItemListener postItemListener) {
                super(itemView);

                // Anh xa
                titleTv = itemView.findViewById(R.id.tvName);
                tvTen = itemView.findViewById(R.id.tvTen);
                tvView = itemView.findViewById(R.id.tvView);
                tvScript = itemView.findViewById(R.id.tvScript);
                tvLike = itemView.findViewById(R.id.tvLike);
                imageView = itemView.findViewById(R.id.imageFilm);
                imageLike = itemView.findViewById(R.id.imgLike);
                btnXem = itemView.findViewById(R.id.btnXemFilm);


                this.mItemListener = postItemListener;
                itemView.setOnClickListener(this);



                imageLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (checkLoginRemember() < 0) {
                            Intent intent = new Intent(mContext, Login.class);
                            mContext.startActivity(intent);
                        } else {
                            Datum item = getItem(getAdapterPosition());
                            SharedPreferences pre = mContext.getSharedPreferences(prefname, MODE_PRIVATE);
                            SharedPreferences.Editor editor = pre.edit();
                            boolean chk = pre.getBoolean(item.getId().toString(), false);
                            if (chk) {

                                //thay trai tim
                                ImageView imgthich = imageLike;
                                imgthich.setImageResource(R.drawable.ic_like2x);
//                            TextView tvthich = htvLike;
                                tvLike.setText("Thích");
                                tvLike.setTextColor(Color.parseColor("#FFFFFFFF"));
                                editor.putBoolean(item.getId().toString(), false);
//            editor.clear();
                            } else {

                                ImageView imgthich = imageLike;
                                imgthich.setImageResource(R.drawable.ic_like_orange2x);
//                            TextView tvthich = holder.tvLike;
                                tvLike.setText("Đã thích");
                                tvLike.setTextColor(Color.parseColor("#fd6003"));
                                editor.putBoolean(item.getId().toString(), true);
                            }
                            editor.commit(); // lưu dữ liệu thông qua commit();
                        }
                    }
                });
            }

            @Override
            public void onClick(View view) {
                Datum item = getItem(getAdapterPosition());
                this.mItemListener.onPostClick(item.getId());

                notifyDataSetChanged();
            }


        public  int checkLoginRemember()
        {
            SharedPreferences pre = mContext.getSharedPreferences(prefnamelogin, MODE_PRIVATE);
            boolean chk = pre.getBoolean("SaveLogin", false);
            if(chk)
            {
                return  1;
            }
            else {
                return -1;
            }
        }




        }

        public FilmAdapter(Context context, List<Datum> posts, PostItemListener itemListener) {
            mItems = posts;
            mContext = context;
            mItemListener = itemListener;
        }
        

        @Override
        public FilmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

//            View postView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            View postView = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_layout_1_dong, parent, false);

            ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(FilmAdapter.ViewHolder holder, int position) {

         Datum item = mItems.get(position);



            ImageView imageView = holder.imageView;
            Picasso.with(mContext)
                    .load(item.getImage())
                    .placeholder(R.drawable.loading_image)
                    .into(imageView);

            String s = item.getTitle();

            if(s.contains("/"))
            {
                String[] splits =  s.split("/");
                TextView textView = holder.titleTv;
                textView.setText(splits[0]);

                String name = splits[1];
                name = name.toLowerCase();
                // bo khang trang
                if(name.substring(0,1).equals(" "))
                    name = name.substring(1, name.length());
                //Viet hoa chu dau
                String w = name.substring(0,1).toUpperCase();
                String s2 = w + name.substring(1, name.length());
                //anh xa
                TextView tvTen = holder.tvTen;
                tvTen.setText(s2);
            }
            else
            {
                TextView textView = holder.titleTv;
                textView.setText(s);

                //bo TextView neu khong co ten
                TextView tvTen = holder.tvTen;
                tvTen.setVisibility(View.GONE);
//                tvTen.setText(splits[0]);
            }

            TextView tvview = holder.tvView;
            tvview.setText("Lượt xem: " + item.getViews().toString());

//            //cat chuoi
            String script1 = item.getDescription();
//            if(script1.length() > 200) {
//                script1 = script1.substring(0, 180) + "...";
//            }

            TextView tvscript = holder.tvScript;
            tvscript.setText(script1);
            tvscript.setMaxLines(5);




            holder.btnXem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences pre = mContext.getSharedPreferences(prefnamelogin, MODE_PRIVATE);
                    boolean chk = pre.getBoolean("SaveLogin", false);
                    if(chk)
                    {
                        Datum i = mItems.get(position);
                        Intent intent = new Intent(mContext, InfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("film", i);
//                  Toast.makeText(context , traiCay.getName(), Toast.LENGTH_SHORT).show();
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(mContext, Login.class);
                        mContext.startActivity(intent);
                    }

                }
            });








            String idF = item.getId().toString();


                SharedPreferences pre = mContext.getSharedPreferences(prefname, MODE_PRIVATE);
                boolean chk = pre.getBoolean(idF, false);
                //        boolean chk2 = chkLuuMK.isChecked();
                if(chk)
                {
                    ImageView imgthich = holder.imageLike;
                    imgthich.setImageResource(R.drawable.ic_like_orange2x);
                    TextView tvthich = holder.tvLike;
                    tvthich.setText("Đã thích");
                    tvthich.setTextColor(Color.parseColor("#fd6003"));
                }
                else {
                    //thay trai tim
                    ImageView imgthich = holder.imageLike;
                    imgthich.setImageResource(R.drawable.ic_like2x);
                    TextView tvthich = holder.tvLike;
                    tvthich.setText("Thích");
                    tvthich.setTextColor(Color.parseColor("#FFFFFFFF"));
                }

//            holder.imageLike.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SharedPreferences pre = mContext.getSharedPreferences(prefname, MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pre.edit();
//                    boolean chk = pre.getBoolean(idF, false);
//                    if(chk)
//                    {
//
//                        //thay trai tim
//                        ImageView imgthich = holder.imageLike;
//                        imgthich.setImageResource(R.drawable.ic_like2x);
//                        TextView tvthich = holder.tvLike;
//                        tvthich.setText("Thích");
//                        tvthich.setTextColor(Color.parseColor("#FFFFFFFF"));
//                        editor.putBoolean(idF, false);
////            editor.clear();
//                    }
//                    else
//                    {
//
//                        ImageView imgthich = holder.imageLike;
//                        imgthich.setImageResource(R.drawable.ic_like_orange2x);
//                        TextView tvthich = holder.tvLike;
//                        tvthich.setText("Đã thích");
//                        tvthich.setTextColor(Color.parseColor("#fd6003"));
//                        editor.putBoolean(idF, true);
//                    }
//                    editor.commit(); // lưu dữ liệu thông qua commit();
//                }
//            });




        }

        

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public void updateAnswers(List<Datum> items) {
            mItems = items;
            notifyDataSetChanged();
        }
        public void InsertAnswers(List<Datum> items) {

            int size = mItems.size();
            for (Datum i : items) {
                mItems.add(i);
            }
            notifyItemInserted(size - 1);
            notifyDataSetChanged();
        }

        private Datum getItem(int adapterPosition) {
            return mItems.get(adapterPosition);
        }

        public interface PostItemListener {
            void onPostClick(long id);
        }
        
        
        
    }
