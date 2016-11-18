package SharpenAndSmooth;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Smooth {
	private BufferedImage bi;
	private int w, h;
	private Move move;

	public Smooth(File file) throws Exception {
		bi = ImageIO.read(file);
		w = bi.getWidth();
		h = bi.getHeight();
		move = new Move(w, h);
	}

	void medianFilter(File file) throws Exception {// 中值滤波
		BufferedImage biTarget = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);// 目标文件
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Vector<Gary> garys = new Vector<>();
				for (int k = 0; k < Move.step; k++) {
					int x = i + Move.dx[k];
					int y = j + Move.dy[k];
					if (move.in(x, y)) {
						garys.add(new Gary(bi.getRGB(x, y)));
					}
				}
				Collections.sort(garys);// 按灰度升序
				biTarget.setRGB(i, j, garys.elementAt(garys.size() / 2).rgb);// 取中值，因为至少有一个点，所以不会越界
			}
		}
		ImageIO.write(biTarget, "jpg", file);
	}

	public static void main(String[] args) throws Exception {
		Scanner sin = new Scanner(System.in);
		System.out.print("输入要平滑的图片文件名:");
		String filenameSourse = sin.next();
		System.out.print("输入平滑之后图片保存的文件名:");
		String filenameTarget = sin.next();
		new Smooth(new File(filenameSourse)).medianFilter(new File(filenameTarget));// 锐化3次左右效果明显
		System.out.println("平滑完成");
	}

}
