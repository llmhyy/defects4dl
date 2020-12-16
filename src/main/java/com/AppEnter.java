package com;

import com.cli.Navigation;
import com.vo.SIR;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class AppEnter {

    private final static String LOGO = "________          _____              __            _____     .___.__   \n" +
            "\\______ \\   _____/ ____\\____   _____/  |_  ______ /  |  |  __| _/|  |  \n" +
            " |    |  \\_/ __ \\   __\\/ __ \\_/ ___\\   __\\/  ___//   |  |_/ __ | |  |  \n" +
            " |    `   \\  ___/|  | \\  ___/\\  \\___|  |  \\___ \\/    ^   / /_/ | |  |__\n" +
            "/_______  /\\___  >__|  \\___  >\\___  >__| /____  >____   |\\____ | |____/\n" +
            "        \\/     \\/          \\/     \\/          \\/     |__|     \\/       \n";


    public static void main(String[] args) {
        System.out.println("Starting...");
        //初始数据
        //Map<String, String> map = DefectsDB.initDB();
        List<SIR> map = DefectsDB.initDB();
        System.out.println(LOGO);
        System.out.println(map);

        //启动web项目
        SpringApplication.run(AppEnter.class, args);

        //启动cli模块
        try {
            Navigation.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
