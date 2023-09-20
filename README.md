# chiyaUtil

## 功能说明

> 本工具库主要功能组成<br>
> 1. 位运算处理：BitMap、BitUtil
> 2. 缓存:  
     普通缓存：ChiyaCache <br>
     键值对缓存：MapCache <br>
     带读写锁缓存：MapLockCache <br>
     异步写入缓存：DelayedWriteCache <br>
     超时失效缓存：MapTimingCache <br>
     对象池：ObjectPool <br>
> 3. 集合：  
     Map内Map结构：ChiyaHashMapValueMap<T, K, V> <br>
     Map内队列结构：ChiyaHashMapValueQueue<T, V> <br>
     Map内Set结构：ChiyaHashMapValueSet<T, V> <br>
     内部消息队列：MessageQueue <br>
> 4. 集合工具库：ContainerUtil
> 5. 通用常量：ChiyaConstant
> 6. 计数器：   <br>
     键计数器：CountMap   <br>
     接口统计：InterfacePerformance <br>
> 7. 断言工具库：Assert，会抛出ChiyaException
> 8. IO工具库：IOUtil    <br>
     文件操作：FileUtil
> 9. 迭代工具：Loop
> 10. 数值工具库：NumberUtil
> 11. 类加载器：ChiyaLoader
> 12. 经纬度求距离工具：DistanceUtil
> 13. 分页对象：Page
> 14. 随机数库：RandomUtil <br>
      随机字符串库：RandomString
> 15. 通用接口返回包装对象: Result<br>
      泛型返回包装对象：ResultPack<T>
> 16. 通用流式处理：ChiyaStream<T>
> 17. 字符串处理库：StringUtil
> 18. 可复用字符：ChiyaString
> 19. 线程相关 <br>
      线程同步执行锁：BlockLock <br>
      线程上下文：ThreadSpace <br>
      线程工具库：ThreadUtil <br>
> 20. 时间相关 <br>
      IP访问次数控制工具：BanIPUtil <br>
      代码性能统计：CodeTime <br>
      日期工具库：DateUtil <br>
      获取当前日期工具：NowTime <br>
      时间段统计次数统计工具：PeriodCount <br>
      延时队列：TimeQueue<K, V> <br>
> 21. 唯一性ID生成器 <br>
      阻塞型ID生成器：BlockAtomicLong <br>
      雪花算法生成器：Snowflake <br>
      单机型雪花生算法生成器：Vine <br>
> 22. 日志工具：ChiyaLog <br>
> 23. AES工具：AESUtil
> 24. 权限注解：ChiyaSecurity、ChiyaSecurityGroup
> 25. DSL解析框架，ChiyaSyntaxParser、ChiyaTokenParser、ChiyaSyntaxTreeParser
>

### 特别说明
> 本工具库下大部分工具均为多线程、高并发下设计，均保证线程安全，部分非多线程设计功能除外


### 缓存类型回收说明
> 该库中的所有缓存均为惰性回收，即下次使用时在进行回收，由负责使用的线程进行