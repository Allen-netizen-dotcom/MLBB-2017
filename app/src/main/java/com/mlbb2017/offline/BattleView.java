
package com.mlbb2017.offline;

import android.content.Context;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

public class BattleView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Handler handler = new Handler(Looper.getMainLooper());

    float heroX = 200;
    float heroY = 300;

    float enemyX = 700;
    float enemyY = 300;

    int playerHealth = 100;
    int enemyHealth = 100;

    boolean running = true;

    Runnable ai = new Runnable() {
        @Override
        public void run() {
            if (!running) return;

            float dx = heroX - enemyX;
            float dy = heroY - enemyY;

            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance > 65 && enemyHealth > 0
                    && playerHealth > 0) {

                enemyX += dx / distance * 5;
                enemyY += dy / distance * 5;

            } else if (distance <= 65
                    && playerHealth > 0
                    && enemyHealth > 0) {

                playerHealth = Math.max(0, playerHealth - 2);
            }

            invalidate();
            handler.postDelayed(this, 300);
        }
    };

    public BattleView(Context context) {
        super(context);
        setFocusable(true);
        handler.postDelayed(ai, 300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.rgb(50, 105, 65));

        paint.setColor(Color.rgb(140, 110, 75));
        canvas.drawRect(0, getHeight() * 0.45f,
                getWidth(), getHeight() * 0.55f, paint);

        // Player
        paint.setColor(Color.BLUE);
        canvas.drawCircle(heroX, heroY, 30, paint);

        // Enemy
        paint.setColor(Color.RED);
        canvas.drawCircle(enemyX, enemyY, 30, paint);

        // Player health
        paint.setColor(Color.WHITE);
        paint.setTextSize(22);
        canvas.drawText("PLAYER HP: " + playerHealth,
                20, 35, paint);

        // Enemy health
        canvas.drawText("ENEMY HP: " + enemyHealth,
                20, 65, paint);

        if (playerHealth <= 0) {
            canvas.drawText("YOU LOSE!", 250, 150, paint);
        } else if (enemyHealth <= 0) {
            canvas.drawText("YOU WIN!", 250, 150, paint);
        }
    }

    public void moveHero(float dx, float dy) {
        if (playerHealth <= 0 || enemyHealth <= 0) return;

        heroX = Math.max(30,
                Math.min(getWidth() - 30, heroX + dx));

        heroY = Math.max(100,
                Math.min(getHeight() - 30, heroY + dy));

        invalidate();
    }

    public void attack() {
        if (playerHealth <= 0 || enemyHealth <= 0) return;

        float dx = heroX - enemyX;
        float dy = heroY - enemyY;

        float distance =
                (float) Math.sqrt(dx * dx + dy * dy);

        if (distance < 120) {
            enemyHealth = Math.max(0, enemyHealth - 20);
        }

        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        running = false;
        handler.removeCallbacks(ai);
        super.onDetachedFromWindow();
    }
                }
