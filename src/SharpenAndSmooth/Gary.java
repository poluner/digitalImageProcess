package SharpenAndSmooth;

class Gary implements Comparable {
	int gary, rgb;

	public Gary(int rgb) {
		this.rgb = rgb;
		int b = rgb & 0xff;
		rgb >>= 8;
		int g = rgb & 0xff;
		rgb >>= 8;
		int r = rgb & 0xff;
		gary = (r * 19595 + g * 38469 + b * 7472) >> 16;// ����Ҷȣ�С��2.9e7�����ᱬint
	}

	@Override
	public int compareTo(Object o) {// ���Ҷ�����
		int gary = ((Gary) o).gary;
		if (this.gary != gary)
			return this.gary - gary;
		return 0;
	}
}