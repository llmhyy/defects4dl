package com.diogonunes.jcdp.color.api;

import com.diogonunes.jcdp.bw.api.AbstractPrinter;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;

public abstract class AbstractColoredPrinter extends AbstractPrinter implements IColoredPrinter {

    private Attribute _attribute;
    private FColor _foregroundColor;
    private BColor _backgroundColor;

    // =====================
    // GET and SET METHODS
    // =====================

    /**
     * @return the current attribute; every message will be printed formatted
     * with this attribute.
     */
    protected Attribute getAttribute() {
        return _attribute;
    }

    /**
     * @return the current foreground color; every message will be printed with
     * this foreground color.
     */
    protected FColor getForegroundColor() {
        return _foregroundColor;
    }

    /**
     * @return the current background color; every message will be printed with
     * this background color.
     */
    protected BColor getBackgroundColor() {
        return _backgroundColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttribute(Attribute a) {
        _attribute = a;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setForegroundColor(FColor c) {
        _foregroundColor = c;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBackgroundColor(BColor c) {
        _backgroundColor = c;
    }

}
