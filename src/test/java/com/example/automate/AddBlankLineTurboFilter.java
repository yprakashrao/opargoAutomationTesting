package com.example.automate;

import org.slf4j.Marker;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;

public class AddBlankLineTurboFilter extends TurboFilter {
    private boolean lastWasBrowserQuit = false;

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        if (logger.getName().equals("com.example.automate.ExistingPatientLogin") &&
            format != null && format.contains("Browser closed and driver quit.")) {
            lastWasBrowserQuit = true;
            return FilterReply.NEUTRAL;
        } else {
            if (lastWasBrowserQuit) {
                lastWasBrowserQuit = false;
                // Log a blank line
                logger.info(""); // This will log a blank line
            }
        }
        return FilterReply.NEUTRAL;
    }
}
