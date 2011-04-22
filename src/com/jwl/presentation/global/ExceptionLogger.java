package com.jwl.presentation.global;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionLogger {

	public static synchronized void severe(Class<?> clazz, Throwable ex) {
		Logger.getLogger(clazz.getName()).log(Level.SEVERE, null, ex);
	}
	
	public static synchronized void warn(Class<?> clazz, Throwable ex) {
		Logger.getLogger(clazz.getName()).log(Level.WARNING, null, ex);
	}

}
