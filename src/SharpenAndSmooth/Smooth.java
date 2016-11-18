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

	void medianFilter(File file) throws Exception {// ��ֵ�˲�
		BufferedImage biTarget = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);// Ŀ���ļ�
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
				Collections.sort(garys);// ���Ҷ�����
				biTarget.setRGB(i, j, garys.elementAt(garys.size() / 2).rgb);// ȡ��ֵ����Ϊ������һ���㣬���Բ���Խ��
			}
		}
		ImageIO.write(biTarget, "jpg", file);
	}

	public static void main(String[] args) throws Exception {
		Scanner sin = new Scanner(System.in);
		System.out.print("����Ҫƽ����ͼƬ�ļ���:");
		String filenameSourse = sin.next();
		System.out.print("����ƽ��֮��ͼƬ������ļ���:");
		String filenameTarget = sin.next();
		new Smooth(new File(filenameSourse)).medianFilter(new File(filenameTarget));// ��3������Ч������
		System.out.println("ƽ�����");
	}

}
