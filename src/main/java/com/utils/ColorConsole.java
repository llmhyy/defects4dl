package com.utils;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class ColorConsole {
    static ColoredPrinter cp = new ColoredPrinter.Builder(0, false)
            .background(Ansi.BColor.BLUE).foreground(Ansi.FColor.YELLOW) //setting format
            .build();

    public static void println(String line) {
        cp.println(line);
    }

}
