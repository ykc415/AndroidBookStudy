package com.bignerdranch.android.fragment_observer;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class PagerItemFragment extends Fragment {


    private int mPageNumber;

    public static PagerItemFragment create(int pageNumber) { // 프레그먼트에 인자 넣어준채로 생성해서 받아오는게 안정성에서 좋다
        PagerItemFragment fragment = new PagerItemFragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt("page"); // 번들 열어서 인트 인자 넣어준것 다시 받아넣음
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pager_item, container, false);

        ImageView image = (ImageView) view.findViewById(R.id.pager_image);
        TextView title = (TextView) view.findViewById(R.id.pager_title);
        TextView artist = (TextView) view.findViewById(R.id.pager_artist);

        MusicData musicData = Data.getInstance().datas.get(mPageNumber); // 페이지넘버로 데이어 받아서 세팅

        Bitmap bitmap = getAlbumArtImage(getContext(), musicData.albumId);
        image.setImageBitmap(bitmap);
        title.setText(musicData.title);
        artist.setText(musicData.artist);

        return view;
    }


    private Bitmap getAlbumArtImage(Context p_Context, long p_AlbumId){
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
