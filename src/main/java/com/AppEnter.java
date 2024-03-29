package com;

import com.cli.Navigation;
import com.vo.SIR;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class AppEnter {

    private final static String LOGO = "\n" +
            "       ________          _____              __            _____     .___.__    \n" +
            "   ____\\______ \\   _____/ ____\\____   _____/  |_  ______ /  |  |  __| _/|  |   \n" +
            "  / ___\\|    |  \\_/ __ \\   __\\/ __ \\_/ ___\\   __\\/  ___//   |  |_/ __ | |  |   \n" +
            " / /_/  >    `   \\  ___/|  | \\  ___/\\  \\___|  |  \\___ \\/    ^   / /_/ | |  |__ \n" +
            " \\___  /_______  /\\___  >__|  \\___  >\\___  >__| /____  >____   |\\____ | |____/ \n" +
            "/_____/        \\/     \\/          \\/     \\/          \\/     |__|     \\/        \n";


    public static void main(String[] args) {
        System.out.println("Starting...");
        //初始数据
        List<SIR> map = DefectsDB.initDB();

        //启动web项目
        SpringApplication.run(AppEnter.class, args);

        //启动cli模块
        System.out.println(LOGO);
        try {
            Navigation.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
