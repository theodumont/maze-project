# Maze game

Keywords: Java, Game

## Table of contents

1. [ Introduction ](#1-introduction)
2. [ Project structure ](#2-project-structure)
3. [ Maze generation - backtracking ](#3-maze-generation)
4. [ Bonuses ](#4-bonuses)
5. [ Todo ](#5-todo)


## 1. Introduction

This project is the result of a course about the basics of java in the Mines ParisTech engineering school.

It is a very simple implementation of a maze game in which the player plays against 2 robots, who are following the left and right wall. To win the game, the player can use bonuses.


## 2. Project structure

The project `maze-project/` has the following structure:
* `maze-project/images/`: images used by the project
* `maze-project/src/`: .java files


## 3. Maze generation - backtracking

I wanted to find a fast maze generation method, which was generating perfect mazes (whose exit is findable following the right or the left wall). The generation method is as follows:

1. At the beginning, each cell has 4 walls;
2. The starting cell of the maze is the (0,h/2) cell;
3. We randomly progress in our maze, opening the walls in the way and never walking in a cell more than once;
4. If we come to a dead end, we go back and we try again.


## 4. Bonuses

- The green bonus stops the robots for a limited time;
- The violet bonus displays the solution of the maze for a limited time.

## 5. Todo

- [ ] Addition of a second player;
- [ ] Addition of a progression bar for each entity during the game;
- [ ] Improvement of the interface.
