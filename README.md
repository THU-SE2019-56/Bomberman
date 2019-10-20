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
	- [Map](#Map-1)
	- [Cell](#Cell)
- [player](#player)
	- [Player](#Player-1)
- [monster](#monster)
	- [Monster](#Monster-1)
- [bomb](#bomb)
	- [Bomb](#Bomb-1)
- [items](#items)
	- [Item](#Item)
	- [VelocityUp](#VelocityUp)

> 目前只是对各个类实现功能的初步描述，欢迎大家在上传代码后或码代码的同时，完善各个类的功能介绍。这样大家在调用别的类的方法时，能够得到清晰的描述，更加方便。
---
### game
负责前端界面的[包](src/game)。  
包含有主菜单，以及整个地图界面的绘制。

#### Display
负责绘制地图界面的[类](src/game/Display.java)。  
将Player, Monster等类的对象作为成员变量统一在此，进而能够通过各个类提供的方法，获取这些成员变量的坐标值，最后通过JPanel提供的方法将图像绘制在视窗中。具体使用方法是通过每30毫秒执行一次的actionPerformed()方法重新绘制界面。

#### MainMenu
负责绘制主菜单的[类](src/game/MainMenu.java)。  
逻辑相对比较简单，即通过不同按钮链接到不同页面中，需要等待其它类实现完成后，最后实现。

---
### map
负责处理地图逻辑的[包](src/map)。  
包含有地图，以及构成地图的格点(Cell）。

#### Map
负责整张地图的[类](src/map/Map.java)。  
储存整个地图的信息，地图可认为由Cell构成。此类主要只处理和地图有关的逻辑，即人物移动、炸弹爆炸等方法均应该在其它类中实现，逻辑上**不适合**出现在此类中。

#### Cell
负责地图每个格点的[类](src/map/Cell.java)。  
每个Cell中可以有可破坏障碍物，不可破坏障碍物，炸弹，道具，是否处于爆炸范围等等信息。其它类的方法均应调用这个类的信息来进行逻辑上的判断。

---
### player
负责玩家的[包](src/player)。  
暂时只包含一个类，之后可以拓展为不同的角色。在人机对战时，电脑人也类似于玩家的操作逻辑。

#### Player
负责玩家的[类](src/player/Player.java)。  
应包含有玩家的速度、炸弹个数、炸弹威力、存货状态、坐标等等成员变量。移动、放置炸弹等成员方法。根据初步讨论，玩家在移动到一整格之前，不应接收其它指令，可以在移动方法中加入是否整除的判断。  
此类还应该实现键盘监听，键盘负责改变玩家的direction，要使玩家松开按键时停止，可以考虑加上STOP这一direction。

---
### monster
负责怪物的[包](src/monster)。  
暂时只包含一个类，之后可以拓展为不同的怪物类型。

#### Monster
负责怪物的[类](src/monster/Monster.java)。  
随机移动（与玩家的移动方法基本相似，互相沟通），警戒范围，脱离警戒范围，被炸弹消灭。

---
### bomb
负责炸弹的[包](src/bomb)。  
可能只有一种炸弹？为了结构考虑还是当做一个包来处理。

#### Bomb
负责炸弹的[类](src/bomb/Bomb.java)。  
需要与地图、人物、怪物等所有类交互，逻辑比较复杂。主要难点在爆炸范围的计算上。

---
### items
负责道具的[包](src/items)。  
里面有Item作为所有道具的父类，以及若干道具。

#### Item
负责所有道具的[类](src/items/Item.java)。  
应该实行被拾取后从地图上消失的功能，具体到每个道具的功能则在子类中实现。

#### VelocityUp
负责增加移速道具的[类](src/items/VelocityUp.java)。  
一个示例，即被拾取了以后调用Player对象的setVelocity()方法来改变速度，消除自身，通过继承父类方法实现。

---
## 任务分工
> 大家每次向github提交代码的时候，欢迎修改此项以描述自己所做的工作

- 陈卓凡：组长，后端，负责[map](#map)包，[Bomb](#Bomb-1)类爆炸方法
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
- github相关的使用教程，请参考慕课4.9节教程。Eclipse等IDE可以利用内置的git插件，可参考[此页面](https://blog.csdn.net/bendanany/article/details/78891804)。
- 类的成员变量均为private，类的成员方法均为public。每个成员变量均需要设置set()和get()方法，即便不一定使用。
- 地图坐标，左上角为(0,0)，x轴向右，y轴向下。
- 上传github时，请不要上传配置文件如.classpath或.project，可以通过配置.gitignore来解决
