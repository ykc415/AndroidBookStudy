package com.bignerdranch.android.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.LayoutInflater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YKC on 2016. 10. 3..
 */
public class BeatBox {
    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets; // AssetManager 클래스를 사용하여 에셋에 엑세스
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssets = context.getAssets();
        // 이 생성자는 deprecated(중요도가 떨어져 더 이상 사용되지 않고 앞으로는 사라지게될)되었지만 호환성 유지를 위해 필요하다
        // 롤리팝부터는 SoundPool.Builder를 사용하여 SoundPool 인스턴스를생성
        // 이 프로젝트는 최소 SDK 버전을 16으로 설정하여 종전버전과 호환성유지를위해 이 생성자를 사용
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        // MAD_SOUNDS                 : 해당 시점에 재생할 음원의 최대 개수
        // AudioManager.STREAM_MUSIC  : SoundPool로 재생할 오디오 스트림의 종류를 결정
        // 0                          : 샘플 레이트 컨버터의 퀄리티
        loadsounds();
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
        //SoundPool.play(음원ID, 왼쪽볼륨(0.0~1.0), 오른쪽볼륨(0.0~1.0), 스트림 우선순위(0이면 최저 우선순위),
        //              반복 재생 여부(0이면 반복 안함 -1이면 무한 반복 그외 숫자는 반복 횟수),
        //              재생률1이면 녹음된 속도 그대로 2는 두배 빠르게 재생 0.5는 절반 느리게 재생)
    }

    public void release() {
        mSoundPool.release();
    }

    private void loadsounds() {
        String[] soundNames;
        try {
            soundNames = mAssets.list(SOUNDS_FOLDER); // SOUNDS_FOLDER에있는 asset list 를 String배열로 리턴
            Log.i(TAG, "Found" + soundNames.length + "sounds");
        } catch (IOException ioe) {
            Log.i(TAG, "Could not list assets", ioe);
            return;
        }

        for (String filename : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename; //
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException ioe) {
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException { // openFd 에서 IOException 을 발생시킬수 있으므로 IOException 을 선언
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1); // 재생될 음원 파일이 SoundPool에 로드
        sound.setSoundId(soundId);
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

}
