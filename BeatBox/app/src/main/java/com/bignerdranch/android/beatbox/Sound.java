package com.bignerdranch.android.beatbox;

/**
 * Created by YKC on 2016. 10. 3..
 */
public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/"); // 파일이름 발췌
        String filename = components[components.length-1]; //
        mName = filename.replace(".wav",""); // .wav확장자 제거
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
