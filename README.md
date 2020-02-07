# CustomDate
一款多功能日历 支持单选 多选 起终位置选择 触摸连续选择

建议应用场景：
- 酒店预订
- 机票预订
- 火车票预订
- 汽车票预订
- 景点门票预订
- 旅游预订
...

版本说明：

||version|
|:-|:-:|
|minSdkVersion|>=16|

引用:
```
maven { url 'https://jitpack.io' }
```

```
 implementation 'com.github.zouhuanxin:CustomDate:1.5'
```

效果图：

|类型|效果|对应type值|
|:-|:-:|-:|
|单选|<img src="http://zhx02.xiaoxingxing.online/2020/02/02/2c2a6c93407eccb2804300ad28c7eedf.jpg" height="300" />|1|
|多选|<img src="http://zhx02.xiaoxingxing.online/2020/02/02/459a1f394079a4d9807380604ce3d35d.jpg" height="300" />|2|
|连续|<img src="http://zhx02.xiaoxingxing.online/2020/02/02/9985cfd3407b4222803d59fd5f9b2fb9.jpg" height="300" />|3|
|触摸|<img src="http://zhx02.xiaoxingxing.online/2020/02/02/f539b8da40fd901a8015322bd4763135.jpg" height="300" />|4|

提供方法:

- 获取选中的所有数据集合
  ```
  getList();
  ```
- 设置日历类型
  ```
  setType(1);
  ```
- 设置禁用类型

  - 1 指定日期启用 支持单选模式 多选模式
  - 2 指定日期禁用 支持单选模式 多选模式
  - 3 日期全禁用 支持单选模式 多选模式
  - 4 指定日期预选 支持多选模式
  ```
  List<String> templist = new ArrayList<>();
  templist.add("2020年1月8");
  setStalist(Integer,templist);
  ```
- 初始化
  ```
   setType(1);
   setStalist(0, null);
  ```
- 设置倍数
  ps:设置的倍数一定要是2的倍数 最小为2 （以倍数为基准除2 取当前年份往上和往下加载除设置的年份视图 不设置默认为2）
  ```
  setBs(2);
  ```
- 设置标注信息
  ```
  List<Notebean> templist = new ArrayList<>();
  templist.add(new Notebean("2020年2月8","2000"));
  zhxDate.setNotebeans(templist);
  ```
- 设置代码选中日期 支持多选和连续俩种模式  连续模式只能传俩个日期
  ```
  List<String> templist2 = new ArrayList<>();
  templist2.add("2020年2月12");
  templist2.add("2020年2月13");
  zhxDate.setDayReslist(templist2);
  ```

提供xml类型方法设置:

|值|类型|示例|说明|
|:-|:-:|:-:|-:|
|bs|Integer|2|只能是双数 最小为2|
|dayitemcolor|string|#4678ff|选中日期圆角颜色|
|month_xhxcolor|string|#4678ff|月份下划线颜色|
|month_fontsize|Integer|15|整数 月份字体大小设置|
|day_fontsize|Integer|14|整数 日期字体大小设置|
|daynote_fontsize|Integer|8|整数 日期下面小标注字体大小|
|year_fontsize|Integer|16|整数 顶部年份字体大小设置|

---
有问题联系作者 qq:634448817 备注当前项目名称哦


