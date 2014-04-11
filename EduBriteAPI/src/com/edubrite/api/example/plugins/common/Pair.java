package com.edubrite.api.example.plugins.common;

public class Pair<A, B> {

	private final A first;

	private final B second;

	private Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}

	public static <A, B> Pair<A, B> of(A first, B second) {
		return new Pair<A, B>(first, second);
	}

	public A getFirst() {
		return first;
	}

	public B getSecond() {
		return second;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Pair<?, ?>) {
			Pair<?, ?> that = (Pair<?, ?>) object;
			return Objects.equals(this.first, that.first)
					&& Objects.equals(this.second, that.second);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(first, second);
	}
}
