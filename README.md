README.md for MapGenerator

# Software design based on modified Simple App Framework
This framework is provided by Richard McKenna (http://www3.cs.stonybrook.edu/~richard/).
Modified from a JClassDesigner which was HW for Stony Brook CSE219 Spring 2016(?)

# Core components
1. DataManager: handles data structures that are used internally
2. FileManager: handles input/output of files
3. Workspace: UI layout of the program

# How things work
## How dynamic componenets are rendered.
1. dObj is interface for holding basic information such as RenderInfo
2. dObj.getRenderObject() returns node

## How file is loaded
DataManager keeps track of all of the dObjs created.
For each of the object, getRenderObject() is called.
The returned Node object is added to the viewAreaPane

## How file is saved
DataManager keeps track of all of the dObjs created.
For each of the object, toJsonObject() is called.
The returned JsonObject is added to JsonArray in FileManager.
Gathered JsonArray is dumped as file.

## How point -> polygon conversion occurs
1. When viewAreaPane/javafx.Polygon is clicked, Point is generated by adding dObj's Point to the DataManager dObj list
   This list of points is also kept tracked by another arraylist called poly in WorkspaceHandler.java
2. When convert is clicked, all the points in the poly arraylist is added to the dObj's PolygonObj.
3. PolygonObj is added to the DataManager's dObj list
   arraylist poly is reset.

## How points are deleted
1. When point is pressed, point removes itself from the Datamanager dObj list

## When points are moved
1. When point is moved, point updates its location using the dObj's RenderInfo's posX and posY. They both are SimpleDoubleProperty
WARNING* However this does not get reflected on the PolygonObj, need to fix this.

# Known issues
* When zoomed in, moving an object does not work as expected

# Features to add
* Resetting zoom
* Undo, redo
* Dashed polygon for "hole" polygon