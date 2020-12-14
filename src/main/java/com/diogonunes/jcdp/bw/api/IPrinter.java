package com.diogonunes.jcdp.bw.api;

import java.util.Date;

public interface IPrinter {
    /**
     * @return current level of debug.
     */
    int getLevel();

    /**
     * Changes the level of debug. This level represents the maximum level of
     * the debug messages displayed by the printer.
     *
     * @param level The new level of debug (should be 0 or greater).
     */
    void setLevel(int level);

    /**
     * @return the current date and time using a format specified by the
     * implementation of this interface.
     */
    String getDateFormatted();

    /**
     * @return the current date and time.
     */
    Date getDate();

    /**
     * Usual System.out.print.
     *
     * @param msg Message to display
     */
    void print(Object msg);

    /**
     * Usual System.out.println
     *
     * @param msg Message to display
     */
    void println(Object msg);

    /**
     * Usual System.err.print.
     *
     * @param msg Error message to display
     */
    void errorPrint(Object msg);

    /**
     * Usual System.err.println.
     *
     * @param msg Error message to display
     */
    void errorPrintln(Object msg);

    /**
     * Prints a debug message to terminal.
     *
     * @param msg Debug message to print
     */
    void debugPrint(Object msg);

    /**
     * Prints a debug message to terminal if the printer has enough level of
     * debug to print that message.
     *
     * @param msg   Debug message to print
     * @param level Level of debug needed to print msg
     */
    void debugPrint(Object msg, int level);

    /**
     * Prints a debug message (with a newline at the end) to terminal.
     *
     * @param msg Debug message to print
     */
    void debugPrintln(Object msg);

    /**
     * Prints a debug message (with a newline at the end) to terminal if the
     * printer has enough level of debug to print that message.
     *
     * @param msg   Debug message to print
     * @param level Level of debug needed to print msg
     */
    void debugPrintln(Object msg, int level);
}
