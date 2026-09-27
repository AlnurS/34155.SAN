package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import org.rowlandhall.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MyClass {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(400);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(17, 17) // ширина, длина в дюймах
                .setColorScheme(new ColorSchemeRedDark()) // цвет робота
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(63.5, -17, Math.toRadians(180)))
                        // 1. Начинаем в углу Красного Альянса, смотрим вправо (0 градусов)
                        /*
                        .forward(-24) // Проехали вперед на 1 мат
                        .turn(Math.toRadians(90)) // Повернулись налево на 90 градусов (теперь смотрим вверх)
                        */
                        // 2. Едем к субмарине в центр поля, одновременно разворачивая робота
                        .waitSeconds(2)
                        .lineToLinearHeading(new Pose2d(23, -55, Math.toRadians(270)))
                        .forward(6) // Проехали вперед на 1 мат
                        .waitSeconds(2)
                        .forward(-6)
                        .lineToLinearHeading(new Pose2d(63.5, -17, Math.toRadians(180)))

                        //.lineToLinearHeading(new Pose2d(-52, -63.5, Math.toRadians(0)))
                        // 3. Отъезжаем боком (стрейфом) в зону парковки
                        /*
                        .strafeLeft(36)
                        .back(12) // Чуть-чуть сдаем назад
                        */

                        .build());


        Image img = null;
        try {
            img = ImageIO.read(new File("C:\\Users\\Public\\Documents\\ftc\\road-runner-quickstart-master\\FtcRobotController\\src\\main\\res\\biobuzz.jpg"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        if (img != null) {
            meepMeep.setBackground(img);
        } else {
            System.err.println("Предупреждение: Файл biobuzz.jpg не найден! Используется стандартный фон GRID_BLUE.");
            meepMeep.setBackground(MeepMeep.Background.GRID_BLUE);
        }

        meepMeep.setDarkMode(true)
                .setBackgroundAlpha(0.95f) // 0.0–1.0, чем меньше — тем прозрачнее фон
                .addEntity(myBot)
                .start();
    }
}