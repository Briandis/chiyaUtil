package chiya.core.base.collection;

public class MapEnter<K, V> {
	private K key;
	private V value;

	/**
	 * 获取key
	 *
	 * @return key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * 获取value
	 *
	 * @return value
	 */
	public V getValue() {
		return value;
	}

	public MapEnter(K key, V value) {
		this.key = key;
		this.value = value;
	}

}
