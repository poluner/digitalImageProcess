package SharpenAndSmooth;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Sharpen {
	private BufferedImage bi;
	private int w, h;
	private Move move;

	public Sharpen(File file) throws Exception {
		bi = ImageIO.read(file);
		w = bi.getWidth();
		h = bi.getHeight();
		move = new Move(w, h);
	}

	// laplace算子，元素个数为Move.step个

	private int laplace[] = { -1, -1, -1, -1, 8, -1, -1, -1, -1 };// 该算子效果最好
	private int laplace2[] = { 0, -1, 0, -1, 4, -1, 0, -1, 0 };
	private int laplace3[] = { 1, -2, 1, -2, 4, -2, 1, -2, 1 };

	private int convolution(int f[], int g[], int k) {// 求卷积h(k)=sigma(f(i)*g(k-i))
		int ans = 0;
		int n = f.length;
		for (int i = 0; i <= k; i++) {
			ans += f[i] * g[k - i];
		}
		for (int i = k + 1; i < n; i++) {// 旋转180度再做一遍卷积
			ans += f[i] * g[n - (i - k) - 1];
		}
		return ans;
	}

	void laplace(File file) throws Exception {
		BufferedImage biTarget = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);// 目标文件
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int bs[] = new int[Move.step];
				int gs[] = new int[Move.step];
				int rs[] = new int[Move.step];
				boolean ok = true;
				for (int k = 0; k < Move.step; k++) {
					int x = i + Move.dx[k];
					int y = j + Move.dy[k];
					if (move.in(x, y)) {
						int rgb = bi.getRGB(x, y);
						bs[k] = rgb & 0xff;
						rgb >>= 8;
						gs[k] = rgb & 0xff;
						rgb >>= 8;
						rs[k] = rgb & 0xff;
					} else {
						ok = false;
						break;
					}
				}
				if (ok) {// 不是边界就可以卷积
					int b = Math.min(255, Math.max(0, convolution(laplace, bs, Move.step / 2)));
					int g = Math.min(255, Math.max(0, convolution(laplace, gs, Move.step / 2)));
					int r = Math.min(255, Math.max(0, convolution(laplace, rs, Move.step / 2)));
					int rgb = r;
					rgb <<= 8;
					rgb += g;
					rgb <<= 8;
					rgb += r;
					biTarget.setRGB(i, j, rgb);
				}
			}
		}
		ImageIO.write(biTarget, "jpg", file);
	}

	public static void main(String[] args) throws Exception {
		Scanner sin = new Scanner(System.in);
		System.out.print("输入要锐化的图片文件名:");
		String filenameSourse = sin.next();
		System.out.print("输入锐化之后图片保存的文件名:");
		String filenameTarget = sin.next();
		new Sharpen(new File(filenameSourse)).laplace(new File(filenameTarget));
		System.out.println("锐化完成");
	}

}
