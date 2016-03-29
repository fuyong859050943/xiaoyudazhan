package com.tanchiyu.sounds;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.tanchiyu.MainActivity;
import com.tanchiyu.R;

public class GameSoundPool {
	private MainActivity mainActivity;
	private SoundPool soundPool;
	private HashMap<Integer,Integer> map;
	public GameSoundPool(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		map = new HashMap<Integer,Integer>();
		soundPool = new SoundPool(8,AudioManager.STREAM_MUSIC,0);
	}
	public void initGameSound(){
		map.put(1, soundPool.load(mainActivity, R.raw.bubble, 1));
		map.put(2, soundPool.load(mainActivity, R.raw.enemybite, 1));
		map.put(3, soundPool.load(mainActivity, R.raw.gamebckmusic, 1));
		map.put(4, soundPool.load(mainActivity, R.raw.gameover, 1));
		map.put(5, soundPool.load(mainActivity, R.raw.menumusic, 1));
		map.put(6, soundPool.load(mainActivity, R.raw.mousedown, 1));
		map.put(7, soundPool.load(mainActivity, R.raw.mouseover, 1));
		map.put(8, soundPool.load(mainActivity, R.raw.playerbite, 1));
		map.put(9, soundPool.load(mainActivity, R.raw.playereaten, 1));
		map.put(10, soundPool.load(mainActivity, R.raw.playergrow, 1));
		map.put(11, soundPool.load(mainActivity, R.raw.score, 1));
		map.put(12, soundPool.load(mainActivity, R.raw.victory, 1));
	}
	//播放音效
	public void playSound(int sound,int loop){
		AudioManager am = (AudioManager)mainActivity.getSystemService(Context.AUDIO_SERVICE);
		float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float stramMaxVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volume = stramVolumeCurrent/stramMaxVolumeCurrent;
		soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);
	}
}
