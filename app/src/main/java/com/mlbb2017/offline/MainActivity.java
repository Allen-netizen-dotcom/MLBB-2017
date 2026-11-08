
package com.mlbb2017.offline;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {

    LinearLayout root;
    String selectedHero = "Warrior";

    int gold = Color.rgb(255, 205, 90);
    int dark = Color.rgb(15, 27, 45);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showMainMenu();
    }

    GradientDrawable bg(int color) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(15);
        return d;
    }

    TextView label(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        return t;
    }

    Button button(String value) {
        Button b = new Button(this);
        b.setText(value);
        b.setTextColor(Color.WHITE);
        b.setTextSize(15);
        b.setTypeface(null, Typeface.BOLD);
        b.setBackground(bg(Color.rgb(130, 75, 30)));
        return b;
    }

    void setup() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(20, 15, 20, 15);
        root.setBackgroundColor(dark);
    }

    void showMainMenu() {
        setup();

        root.addView(label("MOBILE LEGENDS", 30, gold));
        root.addView(label("2017 CLASSIC ERA", 16, Color.WHITE));

        Button play = button("PLAY OFFLINE");
        Button heroes = button("HERO COLLECTION");
        Button settings = button("SETTINGS");

        root.addView(play);
        root.addView(heroes);
        root.addView(settings);

        play.setOnClickListener(v -> showModeMenu());
        heroes.setOnClickListener(v -> showHeroes());
        settings.setOnClickListener(v ->
            showMessage("Settings coming soon!")
        );

        setContentView(root);
    }

    void showModeMenu() {
        setup();

        root.addView(label("SELECT GAME MODE", 27, gold));

        Button classic = button("CLASSIC 5V5");
        Button back = button("BACK");

        root.addView(classic);
        root.addView(back);

        classic.setOnClickListener(v -> startBattle());
        back.setOnClickListener(v -> showMainMenu());

        setContentView(root);
    }

    void showHeroes() {
        setup();

        root.addView(label("SELECT YOUR HERO", 27, gold));

        root.addView(label(
            "Choose a hero for offline practice",
            14, Color.WHITE
        ));

        String[] heroes = {
            "Warrior",
            "Mage",
            "Assassin",
            "Tank",
            "Marksman",
            "Support"
        };

        LinearLayout grid = new LinearLayout(this);
        grid.setGravity(Gravity.CENTER);
        grid.setOrientation(LinearLayout.HORIZONTAL);

        for (String hero : heroes) {
            Button b = button(hero);

            LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(0, 65, 1);

            params.setMargins(3, 5, 3, 5);
            grid.addView(b, params);

            b.setOnClickListener(v -> {
                selectedHero = hero;
                showHeroes();
            });
        }

        root.addView(grid);

        root.addView(label(
            "SELECTED: " + selectedHero,
            19, gold
        ));

        Button confirm = button("CONFIRM HERO");
        Button back = button("BACK");

        root.addView(confirm);
        root.addView(back);

        confirm.setOnClickListener(v ->
            showMessage("Selected hero: " + selectedHero +
            "\nOffline battle system is under development.")
        );

        back.setOnClickListener(v -> showMainMenu());

        setContentView(root);
    }

    
void showMessage(String message) {
    setup();

    root.addView(label(message, 20, Color.WHITE));

    Button back = button("BACK TO MENU");
    root.addView(back);

    back.setOnClickListener(v -> showMainMenu());

    setContentView(root);
}

void startBattle() {
    BattleView battle = new BattleView(this);

    LinearLayout screen = new LinearLayout(this);
    screen.setOrientation(LinearLayout.VERTICAL);
    screen.setBackgroundColor(Color.rgb(30, 70, 40));

    screen.addView(
        battle,
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            1
        )
    );

    LinearLayout controls = new LinearLayout(this);
    controls.setGravity(Gravity.CENTER);

    Button up = button("UP");
    Button down = button("DOWN");
    Button left = button("LEFT");
    Button right = button("RIGHT");
    Button attack = button("ATTACK");
    Button exit = button("EXIT");

    controls.addView(up);
    controls.addView(down);
    controls.addView(left);
    controls.addView(right);
    controls.addView(attack);
    controls.addView(exit);

    screen.addView(controls);

    up.setOnClickListener(v -> battle.moveHero(0, -30));
    down.setOnClickListener(v -> battle.moveHero(0, 30));
    left.setOnClickListener(v -> battle.moveHero(-30, 0));
    right.setOnClickListener(v -> battle.moveHero(30, 0));

    attack.setOnClickListener(v -> battle.attack());
    exit.setOnClickListener(v -> showMainMenu());

    setContentView(screen);
    }
    
