package com.dantesdoesthings.polyzvision;

import android.app.ActionBar;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.support.design.widget.Snackbar;

import pzv.pzvd.FractalStage;
import pzv_math.ColorInfo;
import pzv_math.IterationInfo;
import pzv_math.MethodInfo;
import pzv_math.WorldInfo;

public class MainActivity extends AppCompatActivity implements SharpnessOptionsFragment.SharpnessListener, ColorOptionsFragment.ColorListener, NavigationOptionsFragment.NavigationListener, OtherOptionsFragment.OptionListener, AdvancedOptionsFragment.AdvancedOptionListener, AboutFragment.AboutListener{

    private GLSurfaceView glSurfaceView;
    private SharpnessOptionsFragment sharpnessFragment;
    private ColorOptionsFragment colorFragment;
    private NavigationOptionsFragment navigationFragment;
    private OtherOptionsFragment otherFragment;
    private AdvancedOptionsFragment advancedFragment;
    private AboutFragment aboutFragment;
    private Menu menu;
    private boolean fullScreenWarningDisplayed = false;
    private Snackbar fullScreenPopup;
    private Animation animHide, animShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.bringToFront();

        glSurfaceView = new FractalStage(this);

        CoordinatorLayout mainArea = (CoordinatorLayout) findViewById(R.id.main_view_area);
        mainArea.addView(glSurfaceView);

        sharpnessFragment = new SharpnessOptionsFragment();
        colorFragment = new ColorOptionsFragment();
        navigationFragment = new NavigationOptionsFragment();
        otherFragment = new OtherOptionsFragment();
        advancedFragment = new AdvancedOptionsFragment();
        aboutFragment = new AboutFragment();
        WorldInfo.initGlobalWorld();
        fullScreenPopup = Snackbar.make(mainArea, R.string.full_screen_popup, Snackbar.LENGTH_SHORT);

        getFragmentManager().beginTransaction()
                .replace(R.id.optionFragmentContainer, sharpnessFragment)
                .commit();

        initAnimation();

       // View lowerArea = findViewById(R.id.lower_area);
       // lowerArea.setVisibility(View.INVISIBLE);
      //  lowerArea.startAnimation( animShow );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, m);
        menu = m;
        return true;
    }

    private void initAnimation() {
        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation( this, R.anim.view_hide);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_full_screen:
                fullScreen(false);
                return true;
            ///case R.id.action_full_screen_exit:
             //   fullScreenExit();
              //  return true;
            case R.id.action_hide_menu:
                hideMenuArea();
                return true;
            case R.id.action_show_menu:
                showMenuArea();
                return true;
            case R.id.action_sharpness:
                getFragmentManager().beginTransaction()
                        .replace(R.id.optionFragmentContainer, sharpnessFragment)
                        .commit();
                showMenuArea();
                return true;
            case R.id.action_colors:
                getFragmentManager().beginTransaction()
                    .replace(R.id.optionFragmentContainer, colorFragment)
                    .commit();
                showMenuArea();
                return true;
            case R.id.action_navigation:
                getFragmentManager().beginTransaction()
                    .replace(R.id.optionFragmentContainer, navigationFragment)
                    .commit();
                showMenuArea();
                return true;
            case R.id.action_other_options
                    :
                getFragmentManager().beginTransaction()
                    .replace(R.id.optionFragmentContainer, otherFragment)
                    .commit();
                showMenuArea();
                return true;
            case R.id.action_advanced:
                getFragmentManager().beginTransaction()
                    .replace(R.id.optionFragmentContainer, advancedFragment)
                    .commit();
                showMenuArea();
                return true;
            case R.id.action_about:
                getFragmentManager().beginTransaction()
                    .replace(R.id.optionFragmentContainer, aboutFragment)
                    .commit();
                showMenuArea();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Action Bar related methods
     *
     */

    public void showMenuArea() {
        View temp = findViewById(R.id.lower_area);
        if (temp.getVisibility()==View.GONE) {
            temp.setVisibility(View.VISIBLE);
            temp.startAnimation(animShow);
            menu.findItem(R.id.action_hide_menu).setVisible(true);
            menu.findItem(R.id.action_show_menu).setVisible(false);
        }
    }

    public void hideMenuArea() {
        View temp = findViewById(R.id.lower_area);
        if (temp.getVisibility()==View.VISIBLE) {
            temp.startAnimation(animHide);
            temp.setVisibility(View.GONE);
            menu.findItem(R.id.action_show_menu).setVisible(true);
            menu.findItem(R.id.action_hide_menu).setVisible(false);

        }
    }

    public void fullScreen(boolean selfCalled) {

        int ui = getWindow().getDecorView().getSystemUiVisibility();
        ui ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        ui ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        ui ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(ui);
        if (!selfCalled) {
            hideMenuArea();
            findViewById(R.id.my_toolbar).setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            if (!fullScreenWarningDisplayed) {
                fullScreenWarningDisplayed=true;
                fullScreenPopup.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fullScreen(true);
                    }
                }, 6000);
            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fullScreen(true);
                    }
                }, 5000);
            }
        } else {
            findViewById(R.id.my_toolbar).setVisibility(View.VISIBLE);
        }
        //menu.findItem(R.id.action_full_screen).setVisible(false);
        //menu.findItem(R.id.action_full_screen_exit).setVisible(true);
    }
/*
    public void fullScreenExit() {

        int ui = getWindow().getDecorView().getSystemUiVisibility();
        ui ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        ui ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        ui ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(ui);

        menu.findItem(R.id.action_full_screen_exit).setVisible(false);
        menu.findItem(R.id.action_full_screen).setVisible(true);
    } */

    /* Logging changes to the various option menus
     *
     */
    @Override
    public void onSharpnessFragmentChange(View v, int i) {
        switch (v.getId()) {
            case R.id.slider_sharpness:
                IterationInfo.setIterations(i + 1); // Slider has range of 0 -> 24. This adjusts to 1 -> 25.
                break;
            case R.id.slider_smoothness_sharpness:
                IterationInfo.setEps((float) Math.pow(10.0, -1 * (i / 20.0)) / 10f); // Slider has range of 0 -> 60. This adjusts to 0 -> 3, then scales appropriately.
                break;
            case R.id.slider_dominance_sharpness:
                float t = (float) Math.pow(10.0, -1 * (i/10.0 ) +1f ); // Slider has range of 0 -> 60. This adjusts to -1 -> 5, then scales appropriately.
                IterationInfo.setEps2(t);
                break;
            default:
                break;
        }
        glSurfaceView.requestRender();
    }

    @Override
    public void onColorFragmentChange(int currentPoint, float[] color) {
        ColorInfo.setColor(currentPoint, color);
        glSurfaceView.requestRender();
    }

    @Override
    public void onNavigationChange(View v) {
       switch (v.getId()) {
           case R.id.nav_left_button:
               WorldInfo.moveLeft();
               break;
           case R.id.nav_right_button:
               WorldInfo.moveRight();
               break;
           case R.id.nav_up_button:
               WorldInfo.moveUp();
               break;
           case R.id.nav_down_button:
               WorldInfo.moveDown();
               break;
           case R.id.nav_zoom_in_button:
               WorldInfo.zoomIn();
               break;
           case R.id.nav_zoom_out_button:
               WorldInfo.zoomOut();
               break;
           case R.id.nav_memorize_button:
               WorldInfo.memorize();
               break;
           case R.id.nav_recall_button:
               WorldInfo.restore();
               break;
           case R.id.nav_default_button:
               WorldInfo.setDefault();
               break;
           default:
               break;
       }
        glSurfaceView.requestRender();
    }

    @Override
    public void onOptionFragmentChange(View v) {
        int temp;
        switch (v.getId()) {
            case R.id.options_button_minus:
                temp = MethodInfo.option();
                if (temp!=0) MethodInfo.setOption(temp-1);
                break;
            case R.id.options_button_plus:
                temp = MethodInfo.option();
                if (temp<2) MethodInfo.setOption(temp+1);
                break;
            case R.id.option_radio_polygon:
                MethodInfo.setNorm(0);
                break;
            case R.id.option_radio_square:
                MethodInfo.setNorm(1);
                break;
            case R.id.option_radio_circle:
                MethodInfo.setNorm(2);
                break;
        }
        glSurfaceView.requestRender();
    }

    @Override
    public void onOptionFragmentChange(View v, int i) {
        if (v.getId()==R.id.option_toggle_points) {
            IterationInfo.setShowRoots(i==1);
        }
        glSurfaceView.requestRender();
    }

    @Override
    public void onAdvancedFragmentChange(View v) {
        int temp = v.getId();
        // Set the appropriate Method: Newton, Halley, or MRBF
        if (temp==R.id.advanced_radio_newton) {
            MethodInfo.setMethod(0);
        } else if (temp==R.id.advanced_radio_halley) {
            MethodInfo.setMethod(1);
        } else {
            MethodInfo.setMethod(2);
        }
        glSurfaceView.requestRender();
    }

    @Override
    public void onAdvancedFragmentChange(View v, int i) {
        switch (v.getId()) {
            // Spiraling on or off
            case R.id.toggle_advanced_spiraling:
                MethodInfo.useAlpha(i);
                break;
            // "Speed" seekbar
            case R.id.slider_advanced_speed:
                MethodInfo.setAlphaRd((float)Math.pow(10.0,(i-100)/100.0));
                break;
            // "Swirl" seekbar
            case R.id.slider_advanced_swirl:
                MethodInfo.setAlphaAngle(i/40.0f);
                break;
        }
        glSurfaceView.requestRender();
    }

    @Override
    public void onAboutFragmentChange(View v, int i) {

    }

    public void onMenuAreaClick (View v) {

    }

}
