package com.example.myviewpager;


import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

  static final int PAGE_COUNT = 3;
  
  ImageView droid,browser,dialer,camera,home;
  LinearLayout LinLay1; 
  int home_x,home_y,browser_x,browser_y,dialer_x,dialer_y,camera_x,camera_y;
  int[] droidpos;
  int windowwidth,windowheight;
  private LayoutParams layoutParams;
  TextView time;
  private Timer timer;

  ViewPager pager;
  PagerAdapter pagerAdapter;
  
  @Override
	 public void onAttachedToWindow() {
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG|WindowManager.LayoutParams.FLAG_FULLSCREEN);
     super.onAttachedToWindow();
	 }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_main);
    
    pager = (ViewPager) findViewById(R.id.pager);
    
    droid = (ImageView) findViewById(R.id.droid);
    browser = (ImageView) findViewById(R.id.browser);
    dialer = (ImageView) findViewById(R.id.dialer);
    camera = (ImageView) findViewById(R.id.camera);
    home = (ImageView) findViewById(R.id.home);
    LinLay1 = (LinearLayout) findViewById(R.id.LinLay1);
    time = (TextView) findViewById(R.id.time);    
    
   
    
    
    if(getIntent()!=null&&getIntent().hasExtra("kill")&&getIntent().getExtras().getInt("kill")==1){
		finish();
	}
    
    try{
    	Drawable icon = getPackageManager().getApplicationIcon("com.android.browser");
    	browser.setImageDrawable(icon);
    	Drawable icon1 = getPackageManager().getApplicationIcon("com.android.phone");
        dialer.setImageDrawable(icon1);
        Drawable icon2 = getPackageManager().getApplicationIcon("com.android.camera");
        camera.setImageDrawable(icon2);
    	}
    catch (PackageManager.NameNotFoundException ne)
     {}
    
    startService(new Intent(this,MyService.class));
    
    
    windowwidth=getWindowManager().getDefaultDisplay().getWidth();
    windowheight=getWindowManager().getDefaultDisplay().getHeight();
    MarginLayoutParams marginParams2 = new MarginLayoutParams(droid.getLayoutParams());

    marginParams2.setMargins((windowwidth/24)*10,((windowheight/32)*12),0,0);
    RelativeLayout.LayoutParams layoutdroid = new RelativeLayout.LayoutParams(marginParams2);  
    droid.setLayoutParams(layoutdroid);
    
    droid.setOnTouchListener(new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			layoutParams = (LayoutParams) v.getLayoutParams();
			Animation anim = null;
		    
		    switch (event.getAction()) {
		    case MotionEvent.ACTION_DOWN: 
		    	
		        anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.appear);
		    	int[] browserpos = new int[2];
         		browser.getLocationOnScreen(browserpos);
         		browser_x=browserpos[0];
	        	browser_y=browserpos[1];
	        	
	        	int[] dialerpos = new int[2];
         		dialer.getLocationOnScreen(dialerpos);
         		dialer_x=dialerpos[0];
	        	dialer_y=dialerpos[1];
	        	
	        	int[] camerapos = new int[2];
         		camera.getLocationOnScreen(camerapos);
         		camera_x=camerapos[0];
	        	camera_y=camerapos[1];
	        	
	        	int[] hompos=new int[2];
	        	home.getLocationOnScreen(hompos);
	        	home_x=hompos[0];
	        	home_y=hompos[1];
	        	
         		droidpos=new int[2];
         		
         		LinLay1.setVisibility(View.VISIBLE);
         		LinLay1.startAnimation(anim);
         		
		      break;
		    case MotionEvent.ACTION_MOVE: 
		    	
		    	int x_cord = (int)event.getRawX();
         		int y_cord = (int)event.getRawY();
         		
		    	if(x_cord>windowwidth-(windowwidth/24))
         		{x_cord=windowwidth-(windowwidth/24)*2;}
         		if(y_cord>windowheight-(windowheight/32))
         		{y_cord=windowheight-(windowheight/32)*2;}

         		layoutParams.leftMargin = x_cord-32;
         		layoutParams.topMargin = y_cord-32;

         		droid.getLocationOnScreen(droidpos);
         		v.setLayoutParams(layoutParams);
         		
         		if(((x_cord-home_x)<=(windowwidth/24)*8 && (home_x-x_cord)<=(windowwidth/24)*2)&&((home_y-y_cord)<=(windowheight/32)*3) &&(y_cord-home_y)<=(windowheight/32)*4)
     			{				
     				v.setVisibility(View.GONE);  
     				finish();
     			}	
     		
     		
     		if(((x_cord-browser_x)<=(windowwidth/24)*5 && (browser_x-x_cord)<=(windowwidth/24)*4)&&((browser_y-y_cord)<=(windowheight/32)*5) &&(y_cord-browser_y)<=(windowheight/32)*4)
 				{
     				
     			    Uri address = Uri.parse("about:blank");
     				Intent openlink = new Intent(Intent.ACTION_VIEW, address);
     				startActivity(openlink);
     				v.setVisibility(View.GONE);
     				finish();
 				}
     		
     		if(((x_cord-dialer_x)<=(windowwidth/24)*5 && (dialer_x-x_cord)<=(windowwidth/24)*4)&&((dialer_y-y_cord)<=(windowheight/32)*5) &&(y_cord-dialer_y)<=(windowheight/32)*4)
 				{
     			    
     			    Intent intent = new Intent(Intent.ACTION_DIAL);
     				startActivity(intent);
     				v.setVisibility(View.GONE);
     				finish();
 				}
     		if(((x_cord-camera_x)<=(windowwidth/24)*5 && (camera_x-x_cord)<=(windowwidth/24)*4)&&((camera_y-y_cord)<=(windowheight/32)*5) &&(y_cord-camera_y)<=(windowheight/32)*4)
				{
     				Intent i = new Intent();
     				i.setAction(Intent.ACTION_CAMERA_BUTTON);
     				i.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
     				KeyEvent.KEYCODE_CAMERA));
     				sendOrderedBroadcast(i, null);
     				v.setVisibility(View.GONE);
     				finish();
				}
     		
		      break;
		    case MotionEvent.ACTION_UP: 
		    	anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.disappear);
		    	LinLay1.startAnimation(anim);
		    	LinLay1.setVisibility(View.INVISIBLE);
		    	
		    	int x_cord1 = (int)event.getRawX();
	            int y_cord2 = (int)event.getRawY();

	            if(((x_cord1-home_x)<=(windowwidth/24)*5 && (home_x-x_cord1)<=(windowwidth/24)*4)&&((home_y-y_cord2)<=(windowheight/32)*5))
	              	 {
	              	 }
	            else {

	            	 	layoutParams.leftMargin = (windowwidth/24)*10;
	            	 	layoutParams.topMargin = (windowheight/32)*12;
	            	 	v.setLayoutParams(layoutParams);
		    	
	             	 }
		      break;
		    }
		    
		    return true;
		  }
    });
    
    
    pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
    pager.setAdapter(pagerAdapter);

    pager.setOnPageChangeListener(new OnPageChangeListener() {

      @Override
      public void onPageSelected(int position) {
    	  
    	
        
      }

      @Override
      public void onPageScrolled(int position, float positionOffset,
          int positionOffsetPixels) {
      }

      @Override
      public void onPageScrollStateChanged(int state) {
    	
      }
    });
  }

  private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public MyFragmentPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return PageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
      return PAGE_COUNT;
    }

  }

  @Override
  protected void onStart() {
      super.onStart();
      timer = new Timer("DigitalClock");
      Calendar calendar = Calendar.getInstance();
      
      final Runnable updateTask = new Runnable() {
          public void run() {
             time.setText(getCurrentTime()); 
          }
      };

     
      int msec = 999 - calendar.get(Calendar.MILLISECOND);
      timer.scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
              runOnUiThread(updateTask);
          }
      }, msec, 1000);
  }

  @Override
  protected void onStop() {
      super.onStop();
      timer.cancel();
      timer.purge();
      timer = null;
  }

  private String getCurrentTime() {
      Calendar calendar = Calendar.getInstance();
      int hour = calendar.get(Calendar.HOUR_OF_DAY);
      int minute = calendar.get(Calendar.MINUTE);
      int second = calendar.get(Calendar.SECOND);
      return String.format("%02d:%02d:%02d", hour, minute, second); // ЧЧ:ММ:СС - формат времени
  }
  
  class StateListener extends PhoneStateListener
	{
      @Override
      public void onCallStateChanged(int state, String incomingNumber) 
      	{
	            super.onCallStateChanged(state, incomingNumber);
	            switch(state)
	            	{
	            		case TelephonyManager.CALL_STATE_RINGING:
	            			break;
	                	case TelephonyManager.CALL_STATE_OFFHOOK:
	                		finish();
	                		break;
	                	case TelephonyManager.CALL_STATE_IDLE:
	                		break;
	            	}
      	}
	};

  public void onSlideTouch(View view, MotionEvent event)
	{
	 	switch(event.getAction())
	 		{
	 			case MotionEvent.ACTION_DOWN:
	 				break;
	 			case MotionEvent.ACTION_MOVE:
	 				int x_cord = (int)event.getRawX();
	 				int y_cord = (int)event.getRawY();

	 				if(x_cord>windowwidth){x_cord=windowwidth;}
	 				if(y_cord>windowheight){y_cord=windowheight;}

	 				layoutParams.leftMargin = x_cord -25;
	 				layoutParams.topMargin = y_cord - 75;

	 				view.setLayoutParams(layoutParams);
	 				break;
	 			default:
	 				break;
	 		}

	}
  
  @Override
  public void onBackPressed() {
      return;
  }

  @Override
  protected void onPause() {
      super.onPause();
  }


  @Override
  public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

  	if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)||(keyCode == KeyEvent.KEYCODE_POWER)||(keyCode == KeyEvent.KEYCODE_VOLUME_UP)||(keyCode == KeyEvent.KEYCODE_CAMERA)) {
  	    return true; 
  	}
     if((keyCode == KeyEvent.KEYCODE_HOME)){

  	   return true;
      }

	return false;

  }

  public boolean dispatchKeyEvent(KeyEvent event) {
  	if (event.getKeyCode() == KeyEvent.KEYCODE_POWER ||(event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)||(event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {

  	    return false;
  	}
  	 if((event.getKeyCode() == KeyEvent.KEYCODE_HOME)){

    	   return true;
       }
  return false;
  }

  public void onDestroy(){

      super.onDestroy();
  }
}