# Q版泡泡堂

> 本文档暂用于任务分工、类的解释、代码规范...非最终README文档

十分欢迎大家在push新代码的时候，能够同时修改此README文档，方便任务交接。

## 目录

- [项目架构](#项目架构)
- [任务分工](#任务分工)
- [代码规范](#代码规范)


## 项目架构

- [game](#game)
	- [Display](#Display)
	- [MainMenu](#MainMenu)
- [map](#map)
	- [Map](#Map)
	- [Cell](#Cell)
- [player](#player)
	- [Player](#Player)
- [monster](#monster)
	- [Monster](#Monster)
- [bomb](#bomb)
	- [Bomb](#Bomb)
- [items](#items)
	- [Item](#Item)
	- [VelocityUp](#VelocityUp)

---
### game
负责前端界面的[包](src/game)，包含有主菜单，以及整个地图界面的绘制。

#### Display
负责绘制地图界面的[类](src/game/Display.java)。
将Player, Monster等类的对象作为成员变量统一在此，进而能够通过各个类提供的方法，获取这些成员变量的坐标值，最后通过JPanel提供的方法将图像绘制在视窗中。具体使用方法是通过每30毫秒执行一次的actionPerformed()方法重新绘制界面。

#### MainMenu
负责绘制主菜单的[类](src/game/MainMenu.java)。

---
### map
负责处理地图逻辑的[包](src/map)

#### Map
负责绘制地图界面的[类](src/map/Map.java)

#### Cell
负责绘制地图界面的[类](src/map/Cell.java)

---
### player
负责处理地图逻辑的[包](src/player)

#### Player
负责绘制地图界面的[类](src/player/Player.java)

---
### monster
负责处理地图逻辑的[包](src/monster)

#### Monster
负责绘制地图界面的[类](src/monster/Monster.java)

---
### bomb
负责处理地图逻辑的[包](src/bomb)

#### Bomb
负责绘制地图界面的[类](src/bomb/Bomb.java)

---
### items
负责处理地图逻辑的[包](src/items)

#### Item
负责绘制地图界面的[类](src/items/Item.java)

#### VelocityUp
负责绘制地图界面的[类](src/items/VelocityUp.java)

## 任务分工
> 大家每次向github提交代码的时候，欢迎修改此项以描述自己所做的工作

- 陈卓凡：组长，后端，负责[map](#map)包，[Bomb](#Bomb)类爆炸方法
- 郑俊悦：后端，负责[items](#items)包
- 陶然：前端，负责[game](#game)包
- 杨韫加：后端，负责[bomb](#bomb)包
- 詹昊哲：后端，负责[items](#items)包
- 熊诚淞：后端，负责[player](#player)包
- 陈航：后端，负责[monster](#monster)包
- 李柏瑶：前端，负责[game](#game)包
- 王汤军：后端，负责接口交接

## 代码规范
- 所有代码（除UI界面）使用英文，主要指注释部分。
- 每个类请务必加上说明注释，简单描述该类实现的功能。若可以的话，欢迎为每个方法也加上说明注释。具体使用方法可参考[此页面](https://www.runoob.com/java/java-documentation.html)。
- 变量命名请使用驼峰命名法，即每个单词首字母大写，其余均小写。包名首字母小写，类名首字母大写。
- github相关的使用教程，请参考慕课4.9节教程。Eclipse等IDE可以利用内置的git插件，可参考[此页面](https://blog.csdn.net/bendanany/article/details/78891804)
- 类的成员变量均为private，类的成员方法均为public。每个成员变量均需要设置set()和get()方法，即便不一定使用。
- 地图坐标，左上角为(0,0)，x轴向右，y轴向下。
- 上传github时，请不要上传配置文件如.classpath或.project，可以通过配置.gitignore来解决
