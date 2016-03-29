package com.tanchiyu;

import com.tanchiyu.constant.ConstantUtil;
import com.tanchiyu.sounds.GameSoundPool;
import com.tanchiyu.view.*;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.pm.ActivityInfo;

public class MainActivity extends Activity {
	private MenuView	menuView;
	private RunView		runView;
	private LevelView	levelView;
	private OverView	overView;
	private WinView		winView;
	private GameSoundPool sounds;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what == ConstantUtil.GAME_MENU){
				toMenuView();
			}
			else  if(msg.what == ConstantUtil.GAME_RUNING){
				toRunView();
			}
			else  if(msg.what == ConstantUtil.GAME_PAUSE){
				toRunView();
			}
			else  if(msg.what == ConstantUtil.GAME_UPLEVEL){
				toLevelView();
			}
			else  if(msg.what == ConstantUtil.GAME_WIN){
				toWinView();
			}
			else  if(msg.what == ConstantUtil.GAME_OVER){
				toOverView(msg.arg1);
			}
			else  if(msg.what == ConstantUtil.GAME_END){
				endGame();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		sounds = new GameSoundPool(this);
		sounds.initGameSound();
//		menuView = new MenuView(this,sounds);
//		setContentView(menuView);
		runView = new RunView(this,sounds);
		setContentView(runView);

	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}

	//显示游戏的主界面
	public void toMenuView(){
		if(menuView == null){
			menuView = new MenuView(this,sounds);
		}
		setContentView(menuView);
		//readyView = null;
		//endView = null;
	}
	//显示游戏的主界面
	public void toRunView(){
		if(menuView == null){
			menuView = new MenuView(this,sounds);
		}
		setContentView(menuView);
		//readyView = null;
		//endView = null;
	}
	//显示游戏的主界面
	public void toLevelView(){
		if(menuView == null){
			menuView = new MenuView(this,sounds);
		}
		setContentView(menuView);
		//readyView = null;
		//endView = null;
	}
	//显示游戏的主界面
	public void toWinView(){
		if(menuView == null){
			menuView = new MenuView(this,sounds);
		}
		setContentView(menuView);
		//readyView = null;
		//endView = null;
	}
	//显示游戏结束的界面
	public void toOverView(int score){
		if(overView == null){
			overView = new OverView(this,sounds);
			//overView.setScore(score);
		}
		setContentView(overView);
		//mainView = null;
	}
	//结束游戏
	public void endGame(){
		if(menuView != null){
			menuView.setThreadFlag(false);
		}
		else if(runView != null){
			runView.setThreadFlag(false);
		}
		else if(levelView != null){
			levelView.setThreadFlag(false);
		}
		else if(winView != null){
			winView.setThreadFlag(false);
		}
		else if(overView != null){
			overView.setThreadFlag(false);
		}
		this.finish();
	}
	//getter和setter方法
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}
