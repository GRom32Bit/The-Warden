package com.example.thewarden;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.thewarden.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import android.content.Context;
class ContextHolder {
    static Context ApplicationContext;

    public static void initial(Context context) {
        ApplicationContext = context;
    }

    public static Context getContext() {
        return ApplicationContext;
    }
}
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextHolder.initial(this);
        Context ctx=ContextHolder.getContext();
        if (ctx==null) System.out.println("NOT CONTEXT");
        else System.out.println("CONTEXT");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        setContentView(new GameView(ctx,height,width));
        //setContentView(new GameView(this));
    }

}