
package com.mlbb2017.offline;

import android.content.Context;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

public class BattleView extends View {

    Paint paint = new Paint(3);
    Handler handler = new Handler(Looper.getMainLooper());

    float playerX = 150;
    float playerY = 300;

    int playerHP = 100;

    float[] allyX = new float[4];
    float[] allyY = new float[4];
    int[] allyHP = {100, 100, 100, 100};

    float[] enemyX = new float[5];
    float[] enemyY = new float[5];
    int[] enemyHP = {100, 100, 100, 100, 100};

    boolean initialized = false;
    boolean running = true;

    public BattleView(Context context) {
        super(context);
        setFocusable(true);
    }

    @Override
    protected void onSizeChanged(int w, int h,
                                 int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        playerX = w * 0.15f;
        playerY = h * 0.50f;

        for (int i = 0; i < 4; i++) {
            allyX[i] = w * 0.10f;
            allyY[i] = h * (0.20f + i * 0.18f);
        }

        for (int i = 0; i < 5; i++) {
            enemyX[i] = w * 0.85f;
            enemyY[i] = h * (0.15f + i * 0.17f);
        }

        initialized = true;
        handler.post(ai);
    }

    Runnable ai = new Runnable() {
        @Override
        public void run() {
            if (!running) return;

            for (int i = 0; i < 5; i++) {
                if (enemyHP[i] <= 0) continue;

                float tx = playerX;
                float ty = playerY;

                for (int j = 0; j < 4; j++) {
                    if (allyHP[j] <= 0) continue;

                    float d = distance(
                        enemyX[i], enemyY[i],
                        allyX[j], allyY[j]
                    );

                    if (d < distance(
                        enemyX[i], enemyY[i], tx, ty)) {
                        tx = allyX[j];
                        ty = allyY[j];
                    }
                }

                moveBot(enemyX, enemyY, i, tx, ty, 3);

                if (distance(enemyX[i], enemyY[i], tx, ty) < 55) {
                    if (tx == playerX && ty == playerY) {
                        playerHP = Math.max(0, playerHP - 1);
                    }
                }
            }

            for (int i = 0; i < 4; i++) {
                if (allyHP[i] <= 0) continue;

                int target = -1;
                float nearest = Float.MAX_VALUE;

                for (int j = 0; j < 5; j++) {
                    if (enemyHP[j] <= 0) continue;

                    float d = distance(
                        allyX[i], allyY[i],
                        enemyX[j], enemyY[j]
                    );

                    if (d < nearest) {
                        nearest = d;
                        target = j;
                    }
                }

                if (target >= 0) {
                    if (nearest > 55) {
                        moveBot(allyX, allyY, i,
                            enemyX[target], enemyY[target], 2);
                    } else {
                        enemyHP[target] =
                            Math.max(0, enemyHP[target] - 1);
                    }
                }
            }

            invalidate();
            handler.postDelayed(this, 100);
        }
    };

    float distance(float x1, float y1,
                   float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    void moveBot(float[] x, float[] y, int i,
                 float tx, float ty, float speed) {

        float dx = tx - x[i];
        float dy = ty - y[i];
        float d = distance(x[i], y[i], tx, ty);

        if (d > 55) {
            x[i] += dx / d * speed;
            y[i] += dy / d * speed;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.rgb(50, 110, 65));

        paint.setColor(Color.rgb(130, 105, 70));
        canvas.drawRect(0, getHeight() * 0.43f,
            getWidth(), getHeight() * 0.57f, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(23);
        canvas.drawText("OFFLINE 5V5 BATTLE",
            20, 30, paint);

        paint.setColor(Color.BLUE);
        canvas.drawCircle(playerX, playerY, 25, paint);

        for (int i = 0; i < 4; i++) {
            if (allyHP[i] > 0) {
                paint.setColor(Color.CYAN);
                canvas.drawCircle(
                    allyX[i], allyY[i], 22, paint);
            }
        }

        for (int i = 0; i < 5; i++) {
            if (enemyHP[i] > 0) {
                paint.setColor(Color.RED);
                canvas.drawCircle(
                    enemyX[i], enemyY[i], 22, paint);
            }
        }

        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        canvas.drawText("PLAYER HP: " + playerHP,
            20, 60, paint);

        if (playerHP <= 0) {
            canvas.drawText("DEFEAT", 200, 130, paint);
        } else {
            boolean won = true;

            for (int hp : enemyHP) {
                if (hp > 0) won = false;
            }

            if (won) {
                canvas.drawText("VICTORY!", 200, 130, paint);
            }
        }
    }

    public void moveHero(float dx, float dy) {
        if (playerHP <= 0) return;

        playerX = Math.max(25,
            Math.min(getWidth() - 25, playerX + dx));

        playerY = Math.max(90,
            Math.min(getHeight() - 25, playerY + dy));

        invalidate();
    }

    public void attack() {
        if (playerHP <= 0) return;

        for (int i = 0; i < 5; i++) {
            if (enemyHP[i] <= 0) continue;

            if (distance(playerX, playerY,
                enemyX[i], enemyY[i]) < 120) {

                enemyHP[i] =
                    Math.max(0, enemyHP[i] - 20);

                break;
            }
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
