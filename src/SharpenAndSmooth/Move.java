package SharpenAndSmooth;

public class Move {
	public final static int step = 9;

	static int dx[] = { 0, 0, 0, 1, 1, 1, -1, -1, -1 };// È¡step¸öµã
	static int dy[] = { 0, 1, -1, 0, 1, -1, 0, 1, -1 };

	int n, m;

	public Move(int n, int m) {
		this.n = n;
		this.m = m;
	}

	public boolean in(int x, int y) {
		return x >= 0 && x < n && y >= 0 && y < m;
	}
}
