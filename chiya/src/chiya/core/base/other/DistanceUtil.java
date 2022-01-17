package chiya.core.base.other;

/**
 * 经纬度计工具
 * 
 * @author chiya
 */
public class DistanceUtil {

	/**
	 * 地球平均半径，单位KM
	 */
	public static final double AVG_EARTH_RADIUS = 6371.393;
	/**
	 * 赤道半径，单位KM
	 */
	public static final double EQUATORIAL_EARTH_RADIUS = 6378.137;

	/**
	 * 计算两点之间距离，谷歌地图算法
	 * 
	 * @param longitudeA A的经度
	 * @param latitudeA  A的纬度
	 * @param longitudeB B的经度
	 * @param latitudeB  B的纬度
	 * @return 两点直接的距离，单位KM（千米）
	 */
	public static double twoPointsArcus(double longitudeA, double latitudeA, double longitudeB, double latitudeB) {
		// 弧度计算
		double ax = Math.toRadians(longitudeA);
		double ay = Math.toRadians(latitudeA);
		double bx = Math.toRadians(longitudeB);
		double by = Math.toRadians(latitudeB);
		// 计算公式获得反余弦
		double cos = Math.cos(ay) * Math.cos(by) * Math.cos(ax - bx) + Math.sin(ay) * Math.sin(by);
		return Math.acos(cos) * AVG_EARTH_RADIUS;
	}

	/**
	 * 从0,0到该点的距离，谷歌地图算法
	 * 
	 * @param longitude 经度
	 * @param latitude  纬度
	 * @return 距离，单位KM
	 */
	public static double getDistanceArcus(double longitude, double latitude) {
		return twoPointsArcus(longitude, latitude, 0, 0);
	}

	/**
	 * 计算两点之间距离，高德地图算法
	 * 
	 * @param longitudeA A的经度
	 * @param latitudeA  A的纬度
	 * @param longitudeB B的经度
	 * @param latitudeB  B的纬度
	 * @return 两点直接的距离，单位KM（千米）
	 */
	public static double twoPointsCos(double longitudeA, double latitudeA, double longitudeB, double latitudeB) {
		double ax = Math.toRadians(longitudeA);
		double bx = Math.toRadians(longitudeB);
		double x = ax - bx;
		double y = Math.toRadians(latitudeA) - Math.toRadians(latitudeB);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(x / 2), 2) + Math.cos(ax) * Math.cos(bx) * Math.pow(Math.sin(y / 2), 2)));
		s = s * EQUATORIAL_EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		return s;
	}

	/**
	 * 从0,0到该点的距离，高德地图算法
	 * 
	 * @param longitude 经度
	 * @param latitude  纬度
	 * @return 距离，单位KM
	 */
	public static double getDistanceCos(double longitude, double latitude) {
		return twoPointsCos(longitude, latitude, 0, 0);
	}
}
