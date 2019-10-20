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
- [map](src/map)
	- [Map](src/map/Map.java)
	- [Cell](src/map/Cell.java)
- [player](src/player)
	- [Player](src/player/Player.java)
- [monster](src/monster)
	- [Monster](src/monster/Monster.java)
- [bomb](src/bomb)
	- [Bomb](src/bomb/Bomb.java)
- [items](src/items)
	- [Item](src/items/Item.java)
	- [VelocityUp](src/items/VelocityUp.java)

---
### game
负责前端界面的[包](src/game)，包含有主菜单，以及整个地图界面的绘制。

#### Display
负责绘制地图界面的[类](src/game/Display.java)

#### MainMenu
负责绘制主菜单的[类](src/game/MainMenu.java)

---
### map
负责处理地图逻辑的[包](src/map)

#### Map

#### Cell

---
### player

#### Player

---
### monster

#### Monster

---
### bomb

#### Bomb

---
### items

#### Item

#### VelocityUp

## 任务分工

## 代码规范
