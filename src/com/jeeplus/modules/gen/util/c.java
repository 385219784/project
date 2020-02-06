package com.jeeplus.modules.gen.util;

import java.lang.reflect.Method;

public class c {
	public static Throwable Fake(Throwable throwable, Throwable throwable1) {
		try {
			throwable.getClass().getMethod("initCause", new Class[]{Throwable.class})
					.invoke(throwable, new Object[]{throwable1});
		} catch (Exception localException) {
		}

		return throwable;
	}
}