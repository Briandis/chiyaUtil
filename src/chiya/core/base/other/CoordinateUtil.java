package chiya.core.base.other;

/**
 * 坐标工具
 * 
 * @author chiya
 *
 */
public class CoordinateUtil {

	/**
	 * 轴平方
	 * 
	 * @param a 轴a值
	 * @param b 轴b值
	 * @return 平方值
	 */
	public static double axisToPower(double a, double b) {
		return (a - b) * (a - b);
	}

	/**
	 * 两点之间直线距离
	 * 
	 * @param x1 第一个坐标x轴
	 * @param y1 第一个坐标y轴
	 * @param x2 第二个坐标x轴
	 * @param y2 第二个坐标y轴
	 * @return 距离
	 */
	public static double distance(double x1, double y1, double x2, double y2) {
		double sumValue = axisToPower(x1, x2) + axisToPower(y1, y2);
		return Math.sqrt(sumValue);
	}

	/**
	 * 两点之间直线距离
	 * 
	 * @param x1 第一个坐标x轴
	 * @param y1 第一个坐标y轴
	 * @param z1 第一个坐标z轴
	 * @param x2 第二个坐标x轴
	 * @param y2 第二个坐标y轴
	 * @param z2 第二个坐标z轴
	 * @return 距离
	 */
	public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
		double sumValue = axisToPower(x1, x2) + axisToPower(y1, y2) + axisToPower(z1, z2);
		return Math.sqrt(sumValue);
	}
}
