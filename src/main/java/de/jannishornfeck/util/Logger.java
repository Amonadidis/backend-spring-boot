package de.jannishornfeck.util;

import org.slf4j.LoggerFactory;

public abstract class Logger {

    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

}
