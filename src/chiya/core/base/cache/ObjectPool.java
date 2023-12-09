package chiya.core.base.cache;

import java.util.concurrent.atomic.AtomicInteger;

import chiya.core.base.collection.ChiyaQueue;
import chiya.core.base.function.GenericNoParamFunction;
import chiya.core.base.function.VoidGenericFunction;
import chiya.core.base.object.ObjectUtil;
import chiya.core.base.time.task.TimeQueue;

/**
 * 对象池管理工具<br>
 * 可以自定义链接过程，对象弃用时需要手动回收。<br>
 * getPoolInstance构造实例，putBack将对象放回。
 * 
 * @author chiya
 *
 * @param <K>对象分租的类型
 * @param <V>对象类型
 */
public class ObjectPool<K, V> {

	/** 类文件模板 */
	private Class<V> classTemplate;
	/** 自定义获取实例方法 */
	private GenericNoParamFunction<V> getValueFunction;
	/** 简易id生成的原子对象 */
	private AtomicInteger atomicInteger = new AtomicInteger();
	/** 默认超时5分钟 */
	private long lastTime = 1000 * 60 * 5;
	/** 如果对象被回收，则执行的方法 */
	private VoidGenericFunction<V> task;

	/**
	 * 延时队列初始化
	 * 
	 * @param task 对象如果被回收，则触发的方法
	 */
	private void timeQueueInit(VoidGenericFunction<V> task) {
		this.task = task;
		timeQueue.start();
	}

	/**
	 * 默认构造方法
	 * 
	 * @param task 对象如果被回收，则触发的方法
	 */
	public ObjectPool(VoidGenericFunction<V> task) {
		timeQueueInit(task);
	}

	/**
	 * 自定义获取方法的实例化方法
	 * 
	 * @param getValueFunction 自定义实例化方法的方法，返回对象实例
	 * @param task             对象如果被回收，则触发的方法
	 */
	public ObjectPool(GenericNoParamFunction<V> getValueFunction, VoidGenericFunction<V> task) {
		this.getValueFunction = getValueFunction;
		timeQueueInit(task);
	}

	/**
	 * 类模板反射构造的实例化方法
	 * 
	 * @param classTemplate 类信息
	 * @param task          对象如果被回收，则触发的方法
	 */
	public ObjectPool(Class<V> classTemplate, VoidGenericFunction<V> task) {
		this.classTemplate = classTemplate;
		timeQueueInit(task);
	}

	/**
	 * 获取实例<br>
	 * 优先按照自定义获取实例方法，再按照反射的构造方法
	 * 
	 * @return 对象实例
	 */
	private V getNewInstance() {
		if (getValueFunction != null) { return getValueFunction.getValue(); }
		if (classTemplate != null) { return ObjectUtil.newObject(classTemplate); }
		return null;
	}

	/** 对象容器 */
	class ObjectContainer {
		/** 最后回收时间 */
		public long lastTime = System.currentTimeMillis();
		/** 持有的实例对象 */
		public V objectData;
		/** 是否被使用 */
		public boolean isUse = false;
		/** 对象唯一标识 */
		public int id = atomicInteger.getAndIncrement();
		/** 所属组标识 */
		public K key;

		/**
		 * 获取对象实例
		 * 
		 * @return 对象实例
		 */
		public V getObjectData() {
			return objectData;
		}

		/**
		 * 设置对象实例
		 * 
		 * @param objectData 对象实例
		 */
		public void setObjectData(V objectData) {
			this.objectData = objectData;
		}

		/**
		 * 链式设置对象实例，返回自身
		 * 
		 * @param objectData 对象实例
		 * @return 自身
		 */
		public ObjectContainer chainObjectData(V objectData) {
			setObjectData(objectData);
			return this;
		}

	}

	/** 实例化容器 */
	private ChiyaQueue<K, ObjectContainer> objectCache = new ChiyaQueue<>();

	/** 延时列队 */
	private TimeQueue<Integer, ObjectContainer> timeQueue = new TimeQueue<>(v -> {
		// 此处是利用v的哈希值，将其移除对象池中
		objectCache.remove(v.key, v);
		task.execute(v.getObjectData());
	});

	/**
	 * 获取池中的实例信息
	 * 
	 * @param key 对象唯一索引
	 * @return 对象实例
	 */
	public V getPoolInstance(K key) {
		return getPoolInstance(key, null);
	}

	/**
	 * 获取池中的实例信息
	 * 
	 * @param key              对象唯一索引
	 * @param getValueFunction 该组下，自定义的对象创建方法
	 * @return 对象实例
	 */
	public V getPoolInstance(K key, GenericNoParamFunction<V> getValueFunction) {
		for (ObjectContainer objectContainer : objectCache.getOrNewValue(key)) {
			if (!objectContainer.isUse) {
				objectContainer.isUse = true;
				timeQueue.remove(objectContainer.id);
				return objectContainer.getObjectData();
			}
		}
		// 如果没有，则新建
		ObjectContainer objectContainer = new ObjectContainer()
			.chainObjectData(getValueFunction == null ? getNewInstance() : getValueFunction.getValue());
		objectContainer.isUse = true;
		objectContainer.key = key;
		objectCache.put(key, objectContainer);
		return objectContainer.getObjectData();
	}

	/**
	 * 放回对象
	 * 
	 * @param key    键
	 * @param object 值
	 */
	public void putBack(K key, V object) {
		for (ObjectContainer objectContainer : objectCache.getOrNewValue(key)) {
			if (objectContainer.getObjectData() == object) {
				objectContainer.isUse = false;
				// 放入延时队列
				timeQueue.put(objectContainer.id, objectContainer, lastTime);
			}
		}
	}

	/**
	 * 设置类模板
	 * 
	 * @param classTemplate 类模板
	 */
	public void setClassTemplate(Class<V> classTemplate) {
		this.classTemplate = classTemplate;
	}

	/**
	 * 设置创建对象的方法
	 * 
	 * @param getValueFunction 创建对象的方法
	 */
	public void setGetValueFunction(GenericNoParamFunction<V> getValueFunction) {
		this.getValueFunction = getValueFunction;
	}

	/**
	 * 设置对象如果多久没有使用，则进行回收
	 * 
	 * @param lastTime 回收的时间
	 */
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

}
