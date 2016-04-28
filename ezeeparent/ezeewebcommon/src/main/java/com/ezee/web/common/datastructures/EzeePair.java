package com.ezee.web.common.datastructures;

public class EzeePair<U, V> {

	private final U first;

	private final V second;

	public EzeePair(final U first, final V second) {
		this.first = first;
		this.second = second;
	}

	public final U getFirst() {
		return first;
	}

	public final V getSecond() {
		return second;
	}
}