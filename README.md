CoordCopy
=========
CoordCopy is a Minecraft Bukkit/Spigot plugin for very easy and convenient entity/block position grabbing. Useful for server admins to simplify setting everything up. It is a very small plugin adding the /coordcopy command. When executed, it gives you a CoordCopy wand, which is a very special stick.
* **Left clicking** it gets position from where you're looking at (block or entity).
* **Right clicking** gets your position.

Position is shown in chat with various actions beside it.
* Clicking **Get** types that position in the chat box, so you can send it for someone else to see or select it and copy it.
* **Copy** opens that position as a URL so you can just click the Copy to clipboard button.
* **Save** saves that position to the CoordCopy's configuration file.

Actions with the **Rounded** suffix do the same things, but they round the coordinates.

Tested with Spigot/Paper from 1.8 to 1.18

Download [here](https://github.com/dmkng/CoordCopy/releases/latest)

Building
--------
You can just open this as an IntelliJ IDEA project and use the Maven tab on the right side.

Or, from the command line:
```
mvn clean package
```
