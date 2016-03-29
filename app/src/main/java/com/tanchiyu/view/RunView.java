package com.tanchiyu.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tanchiyu.baseobject.GameObject;
import com.tanchiyu.constant.ConstantUtil;
import com.tanchiyu.factory.GameObjectFactory;
import com.tanchiyu.R;
import com.tanchiyu.object.EnemyFish;
import com.tanchiyu.object.MyFish;
import com.tanchiyu.sounds.GameSoundPool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
/*游戏进行的主界面*/
public class RunView extends BaseView{
	//	private int missileCount; 		// 导弹的数量
//	private int middlePlaneScore;	// 中型敌机的积分
//	private int bigPlaneScore;		// 大型敌机的积分
//	private int bossPlaneScore;		// boss型敌机的积分
//	private int missileScore;		// 导弹的积分
//	private int bulletScore;		// 子弹的积分
//	private int sumScore;			// 游戏总得分
//	private int speedTime;			// 游戏速度的倍数
//	private float bg_y;				// 图片的坐标
//	private float bg_y2;
//	private float play_bt_w;
//	private float play_bt_h;
//	private float missile_bt_y;
	private boolean isPlay;			// 标记游戏运行状态
	private boolean isTouchPlane;	// 判断玩家是否按下屏幕
	private Bitmap background; 		// 背景图片
	private GameObject lifeCount;		// 生命值
	private GameObject level;			// 等级
	private GameObject score;			//分数
	private GameObject pauseButton;		//暂停按钮
	private String pauseStr;			//
	//	private Bitmap background2; 	// 背景图片
//	private Bitmap playButton; 		// 开始/暂停游戏的按钮图片
//	private Bitmap missile_bt;		// 导弹按钮图标
//	private MyPlane myPlane;		// 玩家的飞机
//	private BossPlane bossPlane;	// boss飞机
//	private List<EnemyPlane> enemyPlanes;
//	private MissileGoods missileGoods;
//	private BulletGoods bulletGoods;
	private GameObjectFactory factory;
	/**********************************/
	private List<EnemyFish> enemyFishs;
	private MyFish			myFish=null;
	public RunView(Context context,GameSoundPool sounds) {
		super(context,sounds);

		isPlay = true;
//		speedTime = 1;
		pauseStr = "暂停";
		factory = new GameObjectFactory();						  //工厂类
		enemyFishs = new ArrayList<EnemyFish>();
		//myFish = (MyFish) factory.createMyFish(getResources());//生产玩家的飞机
//		myFish.setMainView(this);
//		for(int i = 0;i < SmallPlane.sumCount;i++){
//			//生产小型敌机
//			SmallPlane smallPlane = (SmallPlane) factory.createSmallPlane(getResources());
//			enemyPlanes.add(smallPlane);
//		}

		thread = new Thread(this);
	}
	// 视图改变的方法
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}
	// 视图创建的方法
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceCreated(arg0);
		initBitmap(); // 初始化图片资源
//		for(GameObject obj:enemyFishs){
//			obj.setScreenWH(screen_width,screen_height);
//		}
//		missileGoods.setScreenWH(screen_width, screen_height);
//		bulletGoods.setScreenWH(screen_width, screen_height);
//		myPlane.setScreenWH(screen_width,screen_height);
		//myFish.setAlive(true);
		if(thread.isAlive()){
			thread.start();
		}
		else{
			thread = new Thread(this);
			thread.start();
		}

		//sounds.playSound(3, 0);
	}
	// 视图销毁的方法
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(arg0);
		release();
	}
	// 响应触屏事件的方法
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			isTouchPlane = false;
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			float x = event.getX();
			float y = event.getY();
			if(pauseButton.getRect().contains(x,y))
			{
				if(isPlay){
					isPlay = false;
					pauseStr = "继续";
				}
				else{
					isPlay = true;
					pauseStr = "暂停";
					synchronized(thread){
						thread.notify();
					}
				}
				return true;
			}
			//判断玩家飞机是否被按下
			else if(myFish.getRect().contains(x, y))
			{
				if(isPlay){
					isTouchPlane = true;
				}
				return true;
			}
			//判断导弹按钮是否被按下
//			else if(x > 10 && x < 10 + missile_bt.getWidth()
//					&& y > missile_bt_y && y < missile_bt_y + missile_bt.getHeight()){
//				if(missileCount > 0){
//					missileCount--;
//					sounds.playSound(5, 0);
//					for(EnemyPlane pobj:enemyPlanes){
//						if(pobj.isCanCollide()){
//							pobj.attacked(100);		   // 敌机增加伤害
//							if(pobj.isExplosion()){
//								addGameScore(pobj.getScore());// 获得分数
//							}
//						}
//					}
//				}
//				return true;
//			}
		}
		//响应手指在屏幕移动的事件
		else if(event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1){
			//判断触摸点是否为玩家的飞机
			if(isTouchPlane){
				float x = event.getX();
				float y = event.getY();
//				if(myFish.getObject_x()>0 && myFish.getObject_x()<this.screen_width-myFish.getObject_width())
//					myFish.setObject_x(x+ (x-myFish.getObject_x()));
//				if(myFish.getObject_y()>0 && myFish.getObject_y()<this.screen_height-myFish.getObject_height())
//					myFish.setObject_y(y+(y-myFish.getObject_y()));

				if(x > myFish.getObject_x()+myFish.getObject_width()/2 + 20){
					myFish.setForward(1);
					myFish.setRotate(true);
					if(myFish.getObject_x()+myFish.getObject_width()/2 + myFish.getSpeed() <= screen_width){
						myFish.setObject_x(myFish.getObject_x() + myFish.getSpeed());
					}
				}
				else if(x < myFish.getObject_x()+myFish.getObject_width()/2 - 20){
					myFish.setForward(3);
					myFish.setRotate(false);
					if(myFish.getObject_x()+myFish.getObject_width()/2 - myFish.getSpeed() >= 0){
						myFish.setObject_x(myFish.getObject_x() - myFish.getSpeed());
					}
				}
				if(y > myFish.getObject_y()+myFish.getObject_height()/2 + 20){
					myFish.setForward(2);
					if(myFish.getObject_y()+myFish.getObject_height()/2 + myFish.getSpeed() <= screen_height){
						myFish.setObject_y(myFish.getObject_y() + myFish.getSpeed());
					}
				}
				else if(y < myFish.getObject_y()+myFish.getObject_height()/2 - 20){
					myFish.setForward(0);
					if(myFish.getObject_y()+myFish.getObject_height()/2 - myFish.getSpeed() >= 0){
						myFish.setObject_y(myFish.getObject_y() - myFish.getSpeed());
					}
				}
//				return true;
			}
		}
		return true;
	}
	//	// 初始化图片资源方法
//	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
//		playButton = BitmapFactory.decodeResource(getResources(),R.drawable.play);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.back1);
//		background2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_02);
//		missile_bt = BitmapFactory.decodeResource(getResources(), R.drawable.missile_bt);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();

		lifeCount = (GameObject) factory.createObject(getResources());
		lifeCount.setScreenWH(screen_width, screen_height);
		lifeCount.initBitmap(0.5f, 1, 1, R.drawable.info0);
		lifeCount.setObject_x(20);
		lifeCount.setObject_y(20);

		level = (GameObject) factory.createObject(getResources());
		level.setScreenWH(screen_width, screen_height);
		level.initBitmap(0.5f, 1, 1, R.drawable.info1);
		level.setObject_x((screen_width-level.getObject_width())/2-60);
		level.setObject_y(20);

		score = (GameObject) factory.createObject(getResources());
		score.setScreenWH(screen_width, screen_height);
		score.initBitmap(0.5f, 1, 1, R.drawable.info2);
		score.setObject_x(screen_width-80-score.getObject_width());
		score.setObject_y(20);

		pauseButton = (GameObject) factory.createObject(getResources());
		pauseButton.setScreenWH(screen_width, screen_height);
		pauseButton.initBitmap(0.3f, 1, 2, R.drawable.buble0);
		pauseButton.setObject_x(screen_width-10-pauseButton.getObject_width());
		pauseButton.setObject_y(screen_height-10-pauseButton.getObject_height());

//		play_bt_w = playButton.getWidth();
//		play_bt_h = playButton.getHeight()/2;
//		bg_y = 0;
//		bg_y2 = bg_y - screen_height;
//		missile_bt_y = screen_height - 10 - missile_bt.getHeight();
	}
	//	//初始化游戏对象
//	public void initObject(){
//		for(EnemyPlane obj:enemyPlanes){
//			//初始化小型敌机
//			if(obj instanceof SmallPlane){
//				if(!obj.isAlive()){
//					obj.initial(speedTime,0,0);
//					break;
//				}
//			}
//			//初始化中型敌机
//			else if(obj instanceof MiddlePlane){
//				if(middlePlaneScore > 10000){
//					if(!obj.isAlive()){
//						obj.initial(speedTime,0,0);
//						break;
//					}
//				}
//			}
//			//初始化大型敌机
//			else if(obj instanceof BigPlane){
//				if(bigPlaneScore >= 25000){
//					if(!obj.isAlive()){
//						obj.initial(speedTime,0,0);
//						break;
//					}
//				}
//			}
//			//初始化BOSS敌机
//			else{
//				if(bossPlaneScore >= 80000){
//					if(!obj.isAlive()){
//						obj.initial(0,0,0);
//						break;
//					}
//				}
//			}
//		}
//		//初始化导弹物品
//		if(missileScore >= 30000){
//			if(!missileGoods.isAlive()){
//				missileGoods.initial(0,0,0);
//				missileScore = 0;
//			}
//		}
//		//初始化子弹物品
//		if(bulletScore >= 20000){
//			if(!bulletGoods.isAlive()){
//				bulletGoods.initial(0,0,0);
//				bulletScore = 0;
//			}
//		}
//		//初始化BOSS飞机的子弹
//		if(bossPlane.isAlive())
//			bossPlane.initButtle();
//		myPlane.isBulletOverTime();
//		myPlane.initButtle();		//初始化玩家飞机的子弹
//		//提升等级
//		if(sumScore >= speedTime*100000 && speedTime < 6){
//			speedTime++;
//		}
//	}
//	// 释放图片资源的方法
//	@Override
//	public void release() {
//		// TODO Auto-generated method stub
//		for(GameObject obj:enemyPlanes){
//			obj.release();
//		}
//		myPlane.release();
//		bulletGoods.release();
//		missileGoods.release();
//		if(!playButton.isRecycled()){
//			playButton.recycle();
//		}
//		if(!background.isRecycled()){
//			background.recycle();
//		}
//		if(!background2.isRecycled()){
//			background2.recycle();
//		}
//		if(!missile_bt.isRecycled()){
//			missile_bt.recycle();
//		}
//	}
	public void collide()
	{
		if(myFish.isAlive())
		{
			for(int i = enemyFishs.size()-1; i>0; i--)
			{
				EnemyFish ef = enemyFishs.get(i);
				if(ef.isBeEate(myFish))
				{
					sounds.playSound(2, 0);
					sounds.playSound(11, 0);
					myFish.setScore(myFish.getScore()+ef.getScore());
					if(myFish.getScore()>7000)
					{
						sounds.playSound(12, 0);
						mainActivity.getHandler().sendEmptyMessage(ConstantUtil.GAME_WIN);
					}
					if(myFish.getLevel()*1000 <= myFish.getScore())
					{
						myFish.setLevel(myFish.getLevel()+1);
						sounds.playSound(10, 0);
						myFish.setScale(myFish.getScale()+0.1f);
						myFish.initBitmap(myFish.getScale());
					}
					enemyFishs.remove(ef);
				}
				else if(myFish.isBeEate(ef))
				{
					sounds.playSound(8, 0);
					if(myFish.getLifeCount()==0)
					{
						myFish.setAlive(false);
						this.threadFlag = false;
					}
				}
			}
		}
	}

	public void makeFishs()
	{
		if(myFish == null)
		{
			myFish = (MyFish) factory.createMyFish(getResources());//生产玩家的飞机
			myFish.setScreenWH(screen_width, screen_height);
			myFish.initBitmap((float)0.3);
			myFish.initial();
		}
		for(int i = enemyFishs.size()-1; i>0; i--)
		{
			EnemyFish ef = enemyFishs.get(i);
			if(ef.isAlive()==false)
			{
				enemyFishs.remove(ef);
			}
		}
		if(enemyFishs.size()<10){

			Random ran = new Random();
			int s = ran.nextInt(5);
			EnemyFish ef;
			switch(s)
			{
				case 0:
					ef = (EnemyFish) factory.createEnemyFish(getResources());
					ef.setScreenWH(screen_width,screen_height);
					ef.initBitmap((float)0.5, 4, 2, R.drawable.fish0);
					ef.initial(100, 1, 2, 0);
					enemyFishs.add(ef);
					break;
				case 1:
					ef = (EnemyFish) factory.createEnemyFish(getResources());
					ef.setScreenWH(screen_width,screen_height);
					ef.initBitmap((float)0.5, 5, 3, R.drawable.fish1);
					ef.initial(50, 1, 4, 1);

					enemyFishs.add(ef);
					break;
				case 2:
					ef = (EnemyFish) factory.createEnemyFish(getResources());
					ef.setScreenWH(screen_width,screen_height);
					ef.initBitmap((float)0.5, 4, 2, R.drawable.fish2);
					ef.initial(200, 1, 1, 2);

					enemyFishs.add(ef);
					break;
				case 3:
					ef = (EnemyFish) factory.createEnemyFish(getResources());
					ef.setScreenWH(screen_width,screen_height);
					ef.initBitmap((float)0.5, 4, 2, R.drawable.fish3);
					ef.initial(70, 1, 3, 3);

					enemyFishs.add(ef);
					break;
				case 4:
					ef = (EnemyFish) factory.createEnemyFish(getResources());
					ef.setScreenWH(screen_width,screen_height);
					ef.initBitmap((float)0.6, 5, 2, R.drawable.fish4);
					ef.initial(250, 1, 2, 4);

					enemyFishs.add(ef);
					break;
				case 5:
					ef = (EnemyFish) factory.createEnemyFish(getResources());
					ef.setScreenWH(screen_width,screen_height);
					ef.initBitmap((float)0.6, 4, 2, R.drawable.fish5);
					ef.initial(300, 1, 3, 5);

					enemyFishs.add(ef);
					break;
				default:
					break;
			}

		}
	}

	// 绘图方法
	@Override
	public void drawSelf() {

		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK); // 绘制背景色
			canvas.save();
			// 计算背景图片与屏幕的比例
			canvas.scale(scalex, scaley, 0, 0);
			canvas.drawBitmap(background, 0, 0/*bg_y*/, paint);   // 绘制背景图
//			canvas.drawBitmap(background2, 0, bg_y2, paint); // 绘制背景图
			canvas.restore();
//			//绘制按钮
//			canvas.save();
//			canvas.clipRect(10, 10, 10 + play_bt_w,10 + play_bt_h);
//			if(isPlay){
//				canvas.drawBitmap(playButton, 10, 10, paint);
//			}
//			else{
//				canvas.drawBitmap(playButton, 10, 10 - play_bt_h, paint);
//			}
//			canvas.restore();
//			//绘制导弹按钮
//			if(missileCount > 0){
//				paint.setTextSize(40);
//				paint.setColor(Color.BLACK);
//				canvas.drawBitmap(missile_bt, 10,missile_bt_y, paint);
//				canvas.drawText("X "+String.valueOf(missileCount), 20 + missile_bt.getWidth(), screen_height - 25, paint);//绘制文字
//			}
//			//绘制导弹物品
//			if(missileGoods.isAlive()){
//				if(missileGoods.isCollide(myPlane)){
//					missileGoods.setAlive(false);
//					missileCount++;
//					sounds.playSound(6, 0);
//				}
//				else
//					missileGoods.drawSelf(canvas);
//			}
//			//绘制子弹物品
//			if(bulletGoods.isAlive()){
//				if(bulletGoods.isCollide(myPlane)){
//					bulletGoods.setAlive(false);
//					if(!myPlane.isChangeBullet()){
//						myPlane.setChangeBullet(true);
//						myPlane.changeButtle();
//						myPlane.setStartTime(System.currentTimeMillis());
//					}
//					else{
//						myPlane.setStartTime(System.currentTimeMillis());
//					}
//					sounds.playSound(6, 0);
//				}
//				else
//					bulletGoods.drawSelf(canvas);
//			}
//			//绘制敌机
			for(EnemyFish obj:enemyFishs){
				if(obj.isAlive()){
					obj.drawSelf(canvas);
					obj.play();
					obj.move();
					//检测敌机是否与玩家的飞机碰撞
//					if(obj.isCanCollide() && myPlane.isAlive()){
//						if(obj.isCollide(myPlane)){
//							myPlane.setAlive(false);
//						}
//					}
				}
			}
//			if(!myPlane.isAlive()){
//				threadFlag = false;
//				sounds.playSound(4, 0);			//飞机炸毁的音效
//			}
			myFish.drawSelf(canvas);	//绘制玩家的飞机
			myFish.play();
//			myPlane.shoot(canvas,enemyPlanes);
//			sounds.playSound(1, 0);	  //子弹音效
//			//绘制导弹按钮
//			if(missileCount > 0){
//				paint.setTextSize(40);
//				paint.setColor(Color.BLACK);
//				canvas.drawBitmap(missile_bt, 10,missile_bt_y, paint);
//				canvas.drawText("X "+String.valueOf(missileCount), 20 + missile_bt.getWidth(), screen_height - 25, paint);//绘制文字
//			}
			lifeCount.drawSelf(canvas);
			level.drawSelf(canvas);
			score.drawSelf(canvas);
			pauseButton.drawSelf(canvas);
			//绘制积分文字
			paint.setTextSize(12);
			paint.setColor(Color.rgb(235, 161, 1));
			canvas.drawText(String.valueOf(myFish.getLifeCount()), lifeCount.getObject_x()+lifeCount.getObject_width(), 33, paint);
			canvas.drawText(String.valueOf(myFish.getLevel()), level.getObject_x()+level.getObject_width(), 33, paint);
			canvas.drawText(String.valueOf(myFish.getScore()), score.getObject_x()+score.getObject_width(), 33, paint);
			paint.setColor(Color.RED);
			canvas.drawText(pauseStr, pauseButton.getObject_x()+pauseButton.getObject_width()/2-12, pauseButton.getObject_y()+pauseButton.getObject_height()/2, paint);
//			//绘制积分文字
//			paint.setTextSize(30);
//			paint.setColor(Color.rgb(235, 161, 1));
//			canvas.drawText("积分:"+String.valueOf(sumScore), 30 + play_bt_w, 40, paint);		//绘制文字
//			canvas.drawText("等级 X "+String.valueOf(speedTime), screen_width - 150, 40, paint); //绘制文字
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	//	// 背景移动的逻辑函数
//	public void viewLogic(){
//		if(bg_y > bg_y2){
//			bg_y += 10;
//			bg_y2 = bg_y - background.getHeight();
//		}
//		else{
//			bg_y2 += 10;
//			bg_y = bg_y2 - background.getHeight();
//		}
//		if(bg_y >= background.getHeight()){
//			bg_y = bg_y2 - background.getHeight();
//		}
//		else if(bg_y2 >= background.getHeight()){
//			bg_y2 = bg_y - background.getHeight();
//		}
//	}
//	// 增加游戏分数的方法
//	public void addGameScore(int score){
//		middlePlaneScore += score;	// 中型敌机的积分
//		bigPlaneScore += score;		// 大型敌机的积分
//		bossPlaneScore += score;	// boss型敌机的积分
//		missileScore += score;		// 导弹的积分
//		bulletScore += score;		// 子弹的积分
//		sumScore += score;			// 游戏总得分
//	}
//	// 播放音效
//	public void playSound(int key){
//		sounds.playSound(key, 0);
//	}
//	// 线程运行的方法
	@Override
	public void run() {

		while (threadFlag) {
			long startTime = System.currentTimeMillis();
//			initObject();
			makeFishs();
			drawSelf();
			collide();
//			viewLogic();		//背景移动的逻辑

			long endTime = System.currentTimeMillis();
			if(!isPlay){
				synchronized (thread) {
					try {
						thread.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				if (endTime - startTime < 100)
					Thread.sleep(100 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sounds.playSound(4, 0);
		Message message = new Message();
		message.what = 	ConstantUtil.GAME_OVER;
		message.arg1 = Integer.valueOf(myFish.getScore());
		mainActivity.getHandler().sendMessage(message);
	}
}
