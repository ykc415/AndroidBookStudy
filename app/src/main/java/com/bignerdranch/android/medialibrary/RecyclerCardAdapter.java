package com.bignerdranch.android.medialibrary;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by YKC on 2016. 9. 28..
 */
public class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.ViewHolder>{

    ArrayList<RecyclerData> datas;
    int itemLayout;
    Context context;


    public RecyclerCardAdapter(Context context, ArrayList<RecyclerData> datas, int itemLayout) {
        this.context = context;
        this.datas = datas;
        this.itemLayout = itemLayout;
    }

    // View 를 만들어서 홀더에 저장하는역할
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent ,false);

        return new ViewHolder(view);
    }

    // 일반 ListAdapter의 getView 를 대체하는 함수
    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        RecyclerData data = datas.get(position);

        Bitmap bitmap = getAlbumArtImage(context, data.albumId);
        holder.image.setImageBitmap(bitmap);

        holder.title.setText(data.title);
        holder.artist.setText(data.artist);
        holder.itemView.setTag(data);


        setanimation(holder.card, position);

    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    //데이터를 재사용해주는 객체
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        TextView artist;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            artist = (TextView) itemView.findViewById(R.id.name);
            card = (CardView) itemView.findViewById(R.id.carditem);
        }
    }

    int lastPosition = -1;

    public void setanimation(View view, int position) {

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            view.startAnimation(animation);
            lastPosition = position;
        }
    }

    public static final Bitmap getAlbumArtImage(Context p_Context, long p_AlbumId){
        Bitmap cover = null;
        ByteArrayOutputStream w_OutBuf = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 8];
        int w_bFirst = 1;
        int w_nZeroCount = 0;

        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(sArtworkUri, Long.valueOf(p_AlbumId));

        ContentResolver res = p_Context.getContentResolver();
        InputStream in;
        try {
            in = res.openInputStream(uri);

            while(true) {
                int count = in.read(buffer);
                if(count == -1){
                    break;
                }
                if(w_bFirst == 1){
                    //. 맨 첫 바이트토막을 쓰는 경우 앞에 붙은 0값들은 제외한다.
                    for(int i = 0; i < count; i++){
                        if(buffer[i] == 0){
                            w_nZeroCount++;
                        }
                        else{
                            break;
                        }
                    }
                    w_OutBuf.write(buffer, w_nZeroCount, count - w_nZeroCount);
                    w_bFirst = 0;
                }
                else {
                    w_OutBuf.write(buffer, 0, count);
                }
            }

            cover = BitmapFactory.decodeByteArray(w_OutBuf.toByteArray(), 0, w_OutBuf.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cover;
    }

}
