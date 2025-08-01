package com.StardewValley.View.Helpers;

import com.StardewValley.model.App;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.enums.Weather;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.StardewValley.model.App.*;

public class WeatherRenderer {

    public static void handleWeather(SpriteBatch batch, OrthographicCamera camera, Viewport viewport) {
        if (currentGame != null) {
            Weather weather = currentGame.weather;
            float delta = Gdx.graphics.getDeltaTime();

            if ((weather == Weather.SNOWY || weather == Weather.RAINY || weather == Weather.STORMY) && !snowInitialized) {
                snowPos1.set(MathUtils.random(0, viewport.getWorldWidth()),
                        MathUtils.random(viewport.getWorldHeight(), viewport.getWorldHeight() * 2));
                snowPos2.set(MathUtils.random(0, viewport.getWorldWidth()),
                        MathUtils.random(viewport.getWorldHeight(), viewport.getWorldHeight() * 2));
                snowInitialized = true;
            }

            batch.begin();

            Color overlayColor;
            switch (weather) {
                case SUNNY:
                    overlayColor = new Color(1.0f, 0.95f, 0.5f, 0.25f);
                    break;
                case RAINY:
                    overlayColor = new Color(0.4f, 0.6f, 0.8f, 0.25f);
                    break;
                case SNOWY:
                    overlayColor = new Color(0.85f, 0.95f, 1.0f, 0.25f);
                    break;
                case STORMY:
                    overlayColor = new Color(0.25f, 0.2f, 0.35f, 0.3f);
                    break;
                default:
                    overlayColor = new Color(1f, 1f, 1f, 0f);
                    break;
            }

            int hour = currentGame.time.getHour();
            if (hour%24 >= 18) {
                overlayColor.r *= 0.2f;
                overlayColor.g *= 0.2f;
                overlayColor.b *= 0.4f;  // آبی کمی بیشتر حفظ بشه
                overlayColor.a = Math.min(overlayColor.a + 0.25f, 1f); // شفافیت بیشتر برای شب
            } else {
                // روشن‌تر کردن برای روز
                overlayColor.r = Math.min(overlayColor.r + 0.1f, 1f);
                overlayColor.g = Math.min(overlayColor.g + 0.1f, 1f);
                overlayColor.b = Math.min(overlayColor.b + 0.1f, 1f);
            }




            batch.setColor(overlayColor);
            batch.draw(GameAssetManager.WHITE_PIXEL,
                    camera.position.x - viewport.getWorldWidth() / 2,
                    camera.position.y - viewport.getWorldHeight() / 2,
                    viewport.getWorldWidth(),
                    viewport.getWorldHeight());

            batch.setColor(1, 1, 1, 1); // ریست رنگ

            // بارش فقط در RAINY, SNOWY, STORMY
            if ((weather == Weather.RAINY || weather == Weather.SNOWY || weather == Weather.STORMY)) {
                if (snowflakeTexture == null) {
                    snowflakeTexture = new Texture(Gdx.files.internal("Weather/Snow.png")); // یکسان برای هر دو
                }

                snowPos1.y -= snowSpeed1 * delta;
                snowPos2.y -= snowSpeed2 * delta;

                if (snowPos1.y < -snowflakeTexture.getHeight()) {
                    snowPos1.y = viewport.getWorldHeight();
                    snowPos1.x = MathUtils.random(0, viewport.getWorldWidth());
                }
                if (snowPos2.y < -snowflakeTexture.getHeight()) {
                    snowPos2.y = viewport.getWorldHeight();
                    snowPos2.x = MathUtils.random(0, viewport.getWorldWidth());
                }

                if (weather == Weather.RAINY || weather == Weather.STORMY)
                    batch.setColor(0.5f, 0.8f, 1f, 1f);
                else
                    batch.setColor(1f, 1f, 1f, 0.9f);

                batch.draw(snowflakeTexture,
                        camera.position.x - viewport.getWorldWidth() / 2 + snowPos1.x,
                        camera.position.y - viewport.getWorldHeight() / 2 + snowPos1.y);

                batch.draw(snowflakeTexture,
                        camera.position.x - viewport.getWorldWidth() / 2 + snowPos2.x,
                        camera.position.y - viewport.getWorldHeight() / 2 + snowPos2.y);

                batch.setColor(1, 1, 1, 1); // ریست رنگ
            }

            batch.end();
        }
    }
}