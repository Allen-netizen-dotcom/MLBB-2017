package com.mlbb2017.offline;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showMainMenu();
    }

    private GradientDrawable background(int color) {
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(color);
        bg.setCornerRadius(18);
        return bg;
    }

    private TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        return t;
    }

    private Button makeButton(String value) {
        Button b = new Button(this);
        b.setText(value);
        b.setTextColor(Color.WHITE);
        b.setTextSize(17);
        b.setTypeface(null, Typeface.BOLD);
        b.setBackground(background(Color.rgb(150, 85, 25)));
        return b;
    }

    private void showMainMenu() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(30, 20, 30, 20);
        root.setBackgroundColor(Color.rgb(16, 28, 48));

        TextView title = text(
            "MOBILE LEGENDS",
            32,
            Color.rgb(255, 210, 110)
        );
        title.setTypeface(null, Typeface.BOLD);

        root.addView(title);

        TextView subtitle = text(
            "2017 CLASSIC ERA • OFFLINE EDITION",
            14,
            Color.WHITE
        );
        root.addView(subtitle);

        TextView space = text("", 15, Color.WHITE);
        root.addView(space);

        Button play = makeButton("PLAY OFFLINE");
        root.addView(play);

        Button heroes = makeButton("HERO COLLECTION");
        root.addView(heroes);

        Button settings = makeButton("SETTINGS");
        root.addView(settings);

        TextView footer = text(
            "OFFLINE MOBA PROTOTYPE",
            12,
            Color.LTGRAY
        );
        root.addView(footer);

        play.setOnClickListener(v -> showModeMenu());

        heroes.setOnClickListener(v ->
            showMessage("Hero Collection coming soon!")
        );

        settings.setOnClickListener(v ->
            showMessage("Settings coming soon!")
        );

        setContentView(root);
    }

    private void showModeMenu() {
        root.removeAllViews();

        TextView title = text(
            "SELECT GAME MODE",
            27,
            Color.YELLOW
        );
        root.addView(title);

        Button classic = makeButton("CLASSIC 5V5");
        root.addView(classic);

        Button back = makeButton("BACK");
        root.addView(back);

        classic.setOnClickListener(v ->
            showMessage("Classic 5v5 offline mode is under development.")
        );

        back.setOnClickListener(v -> showMainMenu());

        setContentView(root);
    }

    private void showMessage(String message) {
        root.removeAllViews();

        TextView info = text(message, 20, Color.WHITE);
        root.addView(info);

        Button back = makeButton("BACK TO MENU");
        root.addView(back);

        back.setOnClickListener(v -> showMainMenu());

        setContentView(root);
    }
          }
      
