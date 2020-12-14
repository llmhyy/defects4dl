package com.diogonunes.jcdp.color;
import com.diogonunes.jcdp.bw.api.IPrinter;
import com.diogonunes.jcdp.color.api.AbstractColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;
import com.diogonunes.jcdp.color.api.IColoredPrinter;
//import com.sun.jna.Function;
//import com.sun.jna.platform.win32.WinDef.BOOL;
//import com.sun.jna.platform.win32.WinDef.DWORD;
//import com.sun.jna.platform.win32.WinDef.DWORDByReference;
//import com.sun.jna.platform.win32.WinNT.HANDLE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static com.diogonunes.jcdp.Constants.NEWLINE;
public class ColoredPrinter  extends AbstractColoredPrinter{
    /**
     * Constructor (using defaults): creates a Colored Printer with no format,
     * zero level of debug and timestamping active according to ISO 8601.
     */
    public ColoredPrinter() {
        this(new Builder(0, false));
    }

    /**
     * Constructor using builder.
     *
     * @param builder Builder with the desired configurations.
     */
    public ColoredPrinter(Builder builder) {
        setLevel(builder._level);
        setTimestamping(builder._timestampFlag);
        setDateFormat(builder._dateFormat);
        setAttribute(builder._attribute);
        setForegroundColor(builder._foregroundColor);
        setBackgroundColor(builder._backgroundColor);

        String currentOS = System.getProperty("os.name");
        if (currentOS.startsWith("Windows")) {
            if (currentOS.endsWith("10"))
                //enableWindows10AnsiSupport();
                print("Win10");
            else {
                String why = "Your version of JCDP (v4.*) requires Windows 10 -- if you cannot upgrade, then use JCDP v3.*";
                throw new UnsupportedOperationException(why);
            }
        }
    }

    // =========
    // BUILDER
    // =========

    /**
     * Builder pattern: allows the caller to specify the attributes that it
     * wants to change and keep the default values in the others.
     */
    public static class Builder {
        // required parameters
        private int _level;
        private boolean _timestampFlag;
        // optional parameters - initialized to default values
        private Attribute _attribute = Attribute.NONE;
        private FColor _foregroundColor = FColor.NONE;
        private BColor _backgroundColor = BColor.NONE;
        private DateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /**
         * The Colored Printer created, by default, has no format. So it's just
         * like a usual Printer {@link IPrinter}.
         *
         * @param level  specifies the maximum level of debug this printer can
         *               print.
         * @param tsFlag true, if you want a timestamp before each message.
         */
        public Builder(int level, boolean tsFlag) {
            _level = level;
            _timestampFlag = tsFlag;
        }

        /**
         * @param df the printing format of the timestamp.
         * @return the builder.
         */
        public Builder withFormat(DateFormat df) {
            this._dateFormat = df;
            return this;
        }

        /**
         * @param attr specifies the attribute component of the printing format.
         * @return the builder.
         */
        public Builder attribute(Attribute attr) {
            this._attribute = attr;
            return this;
        }

        /**
         * @param fg specifies the foreground color of the printing format.
         * @return the builder.
         */
        public Builder foreground(FColor fg) {
            this._foregroundColor = fg;
            return this;
        }

        /**
         * @param bg specifies the background color of the printing format.
         * @return the builder.
         */
        public Builder background(BColor bg) {
            this._backgroundColor = bg;
            return this;
        }

        /**
         * @return a new instance of ColoredPrinterNIX.
         */
        public ColoredPrinter build() {
            return new ColoredPrinter(this);
        }

    }

    // =================================
    // OTHER METHODS (implementations)
    // =================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(Object msg) {
        String code = Ansi.generateCode(getAttribute(), getForegroundColor(), getBackgroundColor());
        formattedPrint(msg, code, false, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(Object msg, Attribute attr, FColor fg, BColor bg) {
        String code = Ansi.generateCode(attr, fg, bg);
        formattedPrint(msg, code, false, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(Object msg) {
        String code = Ansi.generateCode(getAttribute(), getForegroundColor(), getBackgroundColor());
        formattedPrint(msg, code, true, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(Object msg, Attribute attr, FColor fg, BColor bg) {
        String code = Ansi.generateCode(attr, fg, bg);
        formattedPrint(msg, code, true, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrint(Object msg) {
        String code = Ansi.generateCode(getAttribute(), getForegroundColor(), getBackgroundColor());
        formattedPrint(msg, code, false, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrint(Object msg, Attribute attr, FColor fg, BColor bg) {
        String code = Ansi.generateCode(attr, fg, bg);
        formattedPrint(msg, code, false, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrintln(Object msg) {
        String code = Ansi.generateCode(getAttribute(), getForegroundColor(), getBackgroundColor());
        formattedPrint(msg, code, true, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrintln(Object msg, Attribute attr, FColor fg, BColor bg) {
        String code = Ansi.generateCode(attr, fg, bg);
        formattedPrint(msg, code, true, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg) {
        if (isLoggingDebug())
            print(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg, Attribute attr, FColor fg, BColor bg) {
        if (isLoggingDebug())
            print(msg, attr, fg, bg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg, int level) {
        if (isLoggingDebug() && canPrint(level))
            print(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg, int level, Attribute attr, FColor fg, BColor bg) {
        if (canPrint(level))
            print(msg, attr, fg, bg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg) {
        println(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg, Attribute attr, FColor fg, BColor bg) {
        println(msg, attr, fg, bg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg, int level) {
        if (canPrint(level))
            println(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg, int level, Attribute attr, FColor fg, BColor bg) {
        if (canPrint(level))
            println(msg, attr, fg, bg);
    }

    /**
     * @return The text representation of the Printer.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " | level: " + getLevel() + " | timestamping: "
                + isLoggingTimestamps() + " | Attribute: " + getAttribute().name()
                + " | Foreground color: " + getForegroundColor().name() + " | Background color: "
                + getBackgroundColor().name();
    }

    private void formattedPrint(Object msg, String ansiFormatCode, boolean appendNewline, boolean isError) {
        StringBuilder output = new StringBuilder();
        output.append(isLoggingTimestamps() ? getDateFormatted() + " " : "");
        output.append(msg);
        output.append(appendNewline ? NEWLINE : "");
        String formattedMsg = Ansi.formatMessage(output.toString(), ansiFormatCode);

        if (isError)
            System.err.print(formattedMsg);
        else
            System.out.print(formattedMsg);
    }

    /* Windows 10 supports Ansi codes. However, it's still experimental and not enabled by default.
     * This method enables the necessary Windows 10 feature.
     *
     * More info: https://stackoverflow.com/a/51681675/675577
     * Code source: https://stackoverflow.com/a/52767586/675577
     * Reported issue: https://github.com/PowerShell/PowerShell/issues/11449#issuecomment-569531747
     */
//    private void enableWindows10AnsiSupport() {
//        Function GetStdHandleFunc = Function.getFunction("kernel32", "GetStdHandle");
//        DWORD STD_OUTPUT_HANDLE = new DWORD(-11);
//        HANDLE hOut = (HANDLE) GetStdHandleFunc.invoke(HANDLE.class, new Object[]{STD_OUTPUT_HANDLE});
//
//        DWORDByReference p_dwMode = new DWORDByReference(new DWORD(0));
//        Function GetConsoleModeFunc = Function.getFunction("kernel32", "GetConsoleMode");
//        GetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, p_dwMode});
//
//        int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
//        DWORD dwMode = p_dwMode.getValue();
//        dwMode.setValue(dwMode.intValue() | ENABLE_VIRTUAL_TERMINAL_PROCESSING);
//        Function SetConsoleModeFunc = Function.getFunction("kernel32", "SetConsoleMode");
//        SetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, dwMode});
//    }
}
