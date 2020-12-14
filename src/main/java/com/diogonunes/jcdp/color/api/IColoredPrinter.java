package com.diogonunes.jcdp.color.api;

import com.diogonunes.jcdp.bw.api.IPrinter;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;

public interface IColoredPrinter extends IPrinter {
    /**
     * @param attr specifies the "look" of the message
     */
    void setAttribute(Attribute attr);

    /**
     * @param foreground specifies the message's foreground color, i.e. the font's
     *                   color
     */
    void setForegroundColor(FColor foreground);

    /**
     * @param background specifies the message's background color
     */
    void setBackgroundColor(BColor background);

    /**
     * Usual System.out.print overriding current format.
     *
     * @param msg  Message to display
     * @param attr specifies the overriding attribute
     * @param fg   specifies the foreground color
     * @param bg   specifies the background color
     */
    void print(Object msg, Attribute attr, FColor fg, BColor bg);

    /**
     * Usual System.out.println overriding current format.
     *
     * @param msg  Message to display.
     * @param attr specifies the overriding attribute
     * @param fg   specifies the foreground color
     * @param bg   specifies the background color
     */
    void println(Object msg, Attribute attr, FColor fg, BColor bg);

    /**
     * Usual System.err.print overriding current format.
     *
     * @param msg  Error message to display
     * @param attr specifies the overriding attribute
     * @param fg   specifies the foreground color
     * @param bg   specifies the background color
     */
    void errorPrint(Object msg, Attribute attr, FColor fg, BColor bg);

    /**
     * Usual System.err.println overriding current format.
     *
     * @param msg  Error message to display
     * @param attr specifies the overriding attribute
     * @param fg   specifies the foreground color
     * @param bg   specifies the background color
     */
    void errorPrintln(Object msg, Attribute attr, FColor fg, BColor bg);

    /**
     * Prints a debug message to terminal overriding current format.
     *
     * @param msg  Debug message to print
     * @param attr specifies the overriding attribute
     * @param fg   specifies the foreground color
     * @param bg   specifies the background color
     */
    void debugPrint(Object msg, Attribute attr, FColor fg, BColor bg);

    /**
     * Prints a debug message to terminal, overriding current format, if the
     * printer has enough level of debug to print that message.
     *
     * @param msg   Debug message to print
     * @param level Level of debug needed to print msg
     * @param attr  specifies the overriding attribute
     * @param fg    specifies the foreground color
     * @param bg    specifies the background color
     */
    void debugPrint(Object msg, int level, Attribute attr, FColor fg, BColor bg);

    /**
     * Prints a debug message (with a newline at the end and overriding current
     * format) to terminal.
     *
     * @param msg  Debug message to print
     * @param attr specifies the overriding attribute
     * @param fg   specifies the foreground color
     * @param bg   specifies the background color
     */
    void debugPrintln(Object msg, Attribute attr, FColor fg, BColor bg);

    /**
     * Prints a debug message (with a newline at the end and overriding current
     * format) to terminal if the printer has enough level of debug to print
     * that message.
     *
     * @param msg   Debug message to print
     * @param level Level of debug needed to print msg
     * @param attr  specifies the overriding attribute
     * @param fg    specifies the foreground color
     * @param bg    specifies the background color
     */
    void debugPrintln(Object msg, int level, Attribute attr, FColor fg, BColor bg);
}
