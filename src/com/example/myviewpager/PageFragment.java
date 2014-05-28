package com.example.myviewpager;



import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
//import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.util.FastBlur;


public class PageFragment extends Fragment {
  
  static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
  
  int pageNumber;
  int[] backColor = new int [3];
  int[] backRes = new int [3];
  RelativeLayout RelLay;
  AlphaAnimation alpha;
  private ImageView image;
  private TextView text;
  
  static PageFragment newInstance(int page) {
    PageFragment pageFragment = new PageFragment();
    Bundle arguments = new Bundle();
    arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
    pageFragment.setArguments(arguments);
    return pageFragment;
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    
    backColor[0]=Color.rgb(92, 120, 246);
    backColor[1]=Color.rgb(242, 92, 128);
    backColor[2]=Color.rgb(249, 165, 75);
    backRes[0]=R.drawable.bg1;
    backRes[1]=R.drawable.bg2;
    backRes[2]=R.drawable.bg3;

  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View page = inflater.inflate(R.layout.fragment_main, null);
      
    RelLay = (RelativeLayout) page.findViewById(R.id.RelLay);
    text = (TextView) page.findViewById(R.id.text);
    RelLay.setBackgroundResource(backRes[pageNumber]);
    applyBlur();
    //setAlphaForView(RelLay, 0.1f);
    
    return page;
  }
  
  private void setAlphaForView(View v, float alpha) {

	  AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
	  animation.setDuration(0);
	  animation.setFillAfter(true);
	  v.startAnimation(animation);
	  }
  
  private void applyBlur() {
	  RelLay.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
          @Override
          public boolean onPreDraw() {
        	  RelLay.getViewTreeObserver().removeOnPreDrawListener(this);
        	  RelLay.buildDrawingCache();

              Bitmap bmp = RelLay.getDrawingCache();
              blur(bmp, text);
              return true;
          }
      });
  }
  
  private void blur(Bitmap bkg, View view) {

	  float radius = 20;

	    Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
	            (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(overlay);
	    canvas.translate(-view.getLeft(), -view.getTop());
	    canvas.drawBitmap(bkg, 0, 0, null);
	    overlay = FastBlur.doBlur(overlay, (int)radius, true);
	    view.setBackgroundDrawable(new BitmapDrawable(getResources(), overlay));
  }
}
