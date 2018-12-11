# Tool
之前在做项目时,频繁使用圆角的TextView,然后要写一大堆的xml文件,傻傻的已经分不清了,所以决定自己写一个自定义的TextView解决这个麻烦,目前已发布,后续会更新更多功能



Gradle引入:
`compile 'com.tool.russ.view:TxView:1.0.0'`

maven引入:

`<dependency>
  <groupId>com.tool.russ.view</groupId>
  <artifactId>TxView</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>`

使用:

新增自定义属性

radius:上下左右圆角度数

topRightRadius,topLeftRadius,botomRightRadius,bottomLeftRadius分别表示对应的位置,以这些数值为准

backColor:背景颜色

startColor.endColor:设置背景颜色为渐变的颜色,必须设置其中一个

type:渐变颜色的方向:有8个值可以选择:

top_bottom,

bottom_top,

left_right,

right_left,

leftTop_rightBottm,

rightBottm_leftTop,

rightTop_leftBottom,

leftBottom_rightTop

