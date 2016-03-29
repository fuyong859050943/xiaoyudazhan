package com.tanchiyu.baseobject;

//import com.tanchiyu.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
/*游戏对象类*/
public class GameObject {
	protected int currentFrame; 	// 当前动画帧
	protected int speed; 			// 对象的速度
	protected float object_x; 		// 对象的横坐标
	protected float object_y;		// 对象的纵坐标
	protected float object_width; 	// 对象的宽度
	protected float object_height; 	// 对象的高度
	protected float screen_width; 	// 屏幕的宽度
	protected float screen_height;  // 屏幕的高度
	protected boolean isAlive;		// 判断是否存活
	protected Paint paint; 			// 画笔对象
	protected Resources resources;  // 资源类
	/******************/
	protected boolean rotate;		//图片是否翻转
	protected Bitmap photo;			//图片
	protected Bitmap lphoto;		//缩放的图片
	protected Bitmap rphoto;		//翻转缩放之后的图片
	protected float scale;			//缩放
	protected int rowCount;			//图片帧行数
	protected int colCount;			//图片帧列数
	// 构造函数
	public GameObject(Resources resources) {
		this.resources = resources;
		this.paint = new Paint();
		this.currentFrame = 0;
		this.rotate = false;
		this.scale = 1;
		this.isAlive = true;
	}
	// 设置屏幕宽度和高度
	public void setScreenWH(float screen_width, float screen_height) {
		this.screen_width = screen_width;
		this.screen_height = screen_height;
	}
	//	// 初始化数据//参数分别为:速度增加的倍数,x中心坐标,y中心坐标,
//	public void initial(int arg0,float arg1,float arg2){}
	// 初始化图片资源的
	public void initBitmap(float sca,int cc, int rc, int name)
	{
		this.scale = sca;
		this.colCount = cc;
		this.rowCount = rc;
		photo = BitmapFactory.decodeResource(resources, name);
		object_width = photo.getWidth()/this.colCount * this.scale;			//获得每一帧位图的宽
		object_height = photo.getHeight()/this.rowCount * this.scale;		//获得每一帧位图的高

		Matrix matrixL = new Matrix();
		matrixL.postScale(this.scale, this.scale);
		this.lphoto = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrixL, true);

		Matrix matrixR = new Matrix();
		matrixR.setScale(-1, 1);
		matrixR.postTranslate(photo.getWidth(), 0);
		matrixR.postScale(this.scale, this.scale);
		//matrixR.postRotate(180, photo.getWidth()/2, photo.getHeight()/2);
		this.rphoto = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrixR, true);

	}
	// 对象的绘图方法
	public void drawSelf(Canvas canvas)
	{
		if(isAlive)
		{
			if(!rotate)
			{
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(lphoto, object_x - (currentFrame%colCount)*object_width, object_y - (currentFrame/colCount)*object_height,paint);
				canvas.restore();
			}else
			{
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				//canvas.translate(0, object_height);
				//canvas.scale(1f, -1f);
				//canvas.rotate(180);
				canvas.drawBitmap(rphoto, object_x - (currentFrame%colCount)*object_width, object_y - (currentFrame/colCount)*object_height,paint);
				//canvas.scale(1f, -1f);
				canvas.restore();
			}
		}
	}
	//摆动的函数
	public void play()
	{
		this.currentFrame++;
		if(this.currentFrame >= this.colCount*this.rowCount-1)
		{
			this.currentFrame = 0;
		}
	}

	//获得当前矩形大小
	public RectF getRect()
	{
		RectF rect = null;
		if(this.isAlive)
		{
			rect = new RectF(this.object_x, this.object_y, this.object_x + this.object_width, this.object_y + this.object_height);
		}
		return rect;
	}
	// 释放资源的方法
	public void release()
	{
		if(!photo.isRecycled()){
			photo.recycle();
		}
		if(!lphoto.isRecycled()){
			lphoto.recycle();
		}
		if(!rphoto.isRecycled()){
			rphoto.recycle();
		}
	}
	//	// 检测碰撞的方法
//	public boolean isCollide(GameObject obj) {
//		return true;
//	}
	// 对象的逻辑方法
//	public void logic(){}
	//getter和setter方法
	public int getSpeed(){
		return speed;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public float getObject_x() {
		return object_x;
	}
	public void setObject_x(float object_x) {
		this.object_x = object_x;
	}
	public float getObject_y() {
		return object_y;
	}
	public void setObject_y(float object_y) {
		this.object_y = object_y;
	}
	public float getObject_width() {
		return object_width;
	}
	public void setObject_width(float object_width) {
		this.object_width = object_width;
	}
	public float getObject_height() {
		return object_height;
	}
	public void setObject_height(float object_height) {
		this.object_height = object_height;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public boolean isRotate() {
		return rotate;
	}
	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getColCount() {
		return colCount;
	}
	public void setColCount(int colCount) {
		this.colCount = colCount;
	}
	public Bitmap getPhoto() {
		return photo;
	}
	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}
	public Bitmap getLphoto() {
		return lphoto;
	}
	public void setLphoto() {
		this.object_width = photo.getWidth()/this.colCount * this.scale;
		this.object_height = photo.getHeight()/this.rowCount * this.scale;
		Matrix matrixL = new Matrix();
		matrixL.postScale(this.scale, this.scale);
		this.lphoto = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrixL, true);
	}
	public Bitmap getRphoto() {
		return rphoto;
	}
	public void setRphoto() {
		this.object_width = photo.getWidth()/this.colCount * this.scale;
		this.object_height = photo.getHeight()/this.rowCount * this.scale;
		Matrix matrixR = new Matrix();
		matrixR.setScale(-1, 1);
		matrixR.postTranslate(photo.getWidth(), 0);
		matrixR.postScale(this.scale, this.scale);
		//matrixR.postRotate(180);
		//matrixR.postRotate(180, photo.getWidth()/2, photo.getHeight()/2);
		this.rphoto = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrixR, true);
	}


}
