package com.edubrite.api.example.plugins.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Objects {
	private Objects() {
	}

	public static boolean equals(Object o1, Object o2) {
		return (o1 == null) ? (o2 == null) : o1.equals(o2);
	}

	public static int hashCode(Object... objects) {
		return Arrays.hashCode(objects);
	}

	public static <T> T checkNull(T o) {
		return checkNull(o, "");
	}

	public static <T> T checkNull(T o, String message) {
		if (o == null) {
			throw new NullPointerException(message);
		}
		return o;
	}

	public static boolean containsDuplicate(Object... objects) {
		checkNull(objects);
		Set<Object> objectList = new HashSet<Object>(Arrays.asList(objects));
		if (objectList.size() < objects.length)
			return true;
		return false;
	}

	public static void checkDuplicates(Object... objects) {
		if (containsDuplicate(objects)) {
			throw new IllegalArgumentException("Duplicate objects");
		}
	}

	public static void checkArgument(boolean expression) {
		if (!expression) {
			throw new IllegalArgumentException();
		}
	}

	public static void checkArgument(boolean expression, Object errorMessage) {
		if (!expression) {
			throw new IllegalArgumentException(String.valueOf(errorMessage));
		}
	}

	public static void checkArgument(boolean expression,
			String errorMessageFormat, Object... errorMessageArgs) {
		if (!expression) {
			throw new IllegalArgumentException(String.format(
					errorMessageFormat, errorMessageArgs));
		}
	}

	public static void checkState(boolean expression) {
		if (!expression) {
			throw new IllegalStateException();
		}
	}

	public static void checkState(boolean expression, Object errorMessage) {
		if (!expression) {
			throw new IllegalStateException(String.valueOf(errorMessage));
		}
	}

	public static void checkState(boolean expression,
			String errorMessageFormat, Object... errorMessageArgs) {
		if (!expression) {
			throw new IllegalStateException(String.format(errorMessageFormat,
					errorMessageArgs));
		}
	}
}
