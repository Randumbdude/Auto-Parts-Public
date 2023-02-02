package autopartsclient.util;

public class RainbowColor {
	private ColorUtil color;
	private float timer = 0f;

	public RainbowColor() {
		this.color = new ColorUtil(255, 0, 0);
	}

	public void update(float timerIncrement) {
		if (timer >= (20 - timerIncrement)) {
			timer = 0f;
			this.color.setHSV(((this.color.hue + 1f) % 361), 1f, 1f);
		} else {
			timer++;
		}

	}

	public int getColor() {
		return this.color.getColorAsInt();
	}
}
