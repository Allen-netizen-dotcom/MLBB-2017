
package com.mlbb2017.offline;

import android.content.Context;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

public class BattleView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    float heroX = 300;
    float heroY = 350;
    float enemyX = 700;
    float enemyY = 350;

    int health = 100;

    public BattleView(Context context) {
        super(context);
        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w = getWidth();
        float h = getHeight();

        canvas.drawColor(Color.rgb(50, 105, 65));

        paint.setColor(Color.rgb(80, 125, 75));
        canvas.drawRect(0, h * 0.4f, w, h * 0.6f, paint);

        paint.setColor(Color.rgb(140, 110, 75));
        canvas.drawRect(0, h * 0.47f, w, h * 0.53f, paint);

        // Player
        paint.setColor(Color.BLUE);
        canvas.drawCircle(heroX, heroY, 35, paint);

        // Enemy bot
        paint.setColor(Color.RED);
        canvas.drawCircle(enemyX, enemyY, 35, paint);

        // Health bar
        paint.setColor(Color.BLACK);
        canvas.drawRect(heroX - 35, heroY - 55,
                heroX + 35, heroY - 45, paint);

        paint.setColor(Color.GREEN);
        canvas.drawRect(heroX - 35, heroY - 55,
                heroX - 35 + (70 * health / 100f),
                heroY - 45, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(28);
        canvas.drawText("OFFLINE BATTLE", 30, 40, paint);
        canvas.drawText("HP: " + health, 30, 80, paint);

        paint.setColor(Color.RED);
        canvas.drawCircle(enemyX, enemyY, 12, paint);
    }

    public void moveHero(float dx, float dy) {
        heroX += dx;
        heroY += dy;

        heroX = Math.max(40, Math.min(getWidth() - 40, heroX));
        heroY = Math.max(100, Math.min(getHeight() - 40, heroY));

        invalidate();
    }

    public void attack() {
        float dx = heroX - enemyX;
        float dy = heroY - enemyY;

        if (Math.sqrt(dx * dx + dy * dy) < 120) {
            health = Math.max(0, health - 10);
        }

        invalidate();
    }
}
