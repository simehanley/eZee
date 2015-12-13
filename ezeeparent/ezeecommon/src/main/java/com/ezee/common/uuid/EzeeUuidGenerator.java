package com.ezee.common.uuid;

public class EzeeUuidGenerator {

	private static final char[] CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	public static String uuid(final int len) {
		return uuid(len, CHARS.length);
	}

	public static String uuid(final int len, final int radix) {
		if (radix > CHARS.length) {
			throw new IllegalArgumentException();
		}
		char[] uuid = new char[len];
		for (int i = 0; i < len; i++) {
			uuid[i] = CHARS[(int) (Math.random() * radix)];
		}
		return new String(uuid);
	}

	public static String uuid() {
		char[] uuid = new char[36];
		int r;
		uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
		uuid[14] = '4';
		for (int i = 0; i < 36; i++) {
			if (uuid[i] == 0) {
				r = (int) (Math.random() * 16);
				uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf];
			}
		}
		return new String(uuid);
	}
}