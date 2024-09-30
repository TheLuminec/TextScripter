# Text-Based Adventure Game

This is a text-based adventure game written in Java that reads game content from a custom scripting language defined in a text file. The game features rooms, options, items, conditional descriptions, an in-game timer.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Compilation](#compilation)
  - [Running the Game](#running-the-game)
- [Scripting Language Guide](#scripting-language-guide)
  - [Script Structure](#script-structure)
  - [Global Settings](#global-settings)
  - [Rooms](#rooms)
    - [Descriptions](#descriptions)
    - [Options](#options)
  - [Conditions](#conditions)
  - [Actions](#actions)
  - [Time Costs](#time-costs)
  - [Special Rooms](#special-rooms)
- [Example Script](#example-script)
- [Creating Your Own Adventure](#creating-your-own-adventure)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- **Custom Scripting Language**: Define your game's narrative, rooms, options, and logic in an external script file.
- **Conditional Descriptions**: Display room descriptions based on the player's inventory or game state.
- **Inventory System**: Players can pick up and use items, which affect available options and descriptions.
- **In-Game Timer**: Actions consume time, and the game can end when time runs out.
- **Multiple Endings**: Different endings based on player choices and actions.
- **Extensibility**: Easily add new rooms, items, and mechanics by editing the script file.

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Ensure you have JDK 8 or higher installed.
- **Text Editor**: Any text editor for editing the script file and code if needed.

### Compilation

1. **Clone or Download the Repository**

   Download the source code files to your local machine.

2. **Navigate to the Source Directory**

   Open a terminal or command prompt and navigate to the directory containing the `.java` files.

3. **Compile the Java Classes**

   ```bash
   javac Main.java Game.java Room.java Option.java Description.java Player.java
   ```

   This command compiles all the necessary Java classes.

### Running the Game

After successful compilation, run the game using:

```bash
java Main
```

The game will read the `script.txt` file in the same directory and start the adventure.

---

## Scripting Language Guide

The game uses a custom scripting language defined in a text file (e.g., `script.txt`) to specify the game's content. This guide explains how to structure and write your own game scripts.

### Script Structure

The script file is organized into sections:

1. **Global Settings**: Define game-wide settings like the timer and starting room.
2. **Rooms**: Define the different locations in the game.
3. **Descriptions**: Provide textual descriptions of rooms, which can be conditional.
4. **Options**: Define the choices available to the player in each room.
5. **Special Rooms**: Handle specific game states like time running out.

### Global Settings

- **TIMER**: Sets the starting time for the game.

  ```plaintext
  TIMER=10
  ```

- **START**: Specifies the ID of the starting room.

  ```plaintext
  START=room1
  ```

### Rooms

Each room starts with a room ID enclosed in angle brackets:

```plaintext
<room_id>
```

#### Descriptions

- **[DESCRIPTION]**: Marks the beginning of a description block.
- **TEXT**: Provides the description text.
  - Supports multi-line text between `TEXT=` and `ENDTEXT`.
- **CONDITION** (Optional): Specifies if the description should be shown based on the player's inventory.

**Example:**

```plaintext
<room1>
[DESCRIPTION]
TEXT=
You are in a dark cave. The sound of dripping water echoes around you.
There is a faint light to the east.
ENDTEXT

[DESCRIPTION]
CONDITION=!torch
TEXT=It's hard to see without a torch.
ENDTEXT
```

#### Options

- **[O]**: Marks the beginning of an option block.
- **TEXT**: The text displayed to the player for this option.
- **CONDITION** (Optional): Determines if the option is available based on the player's inventory.
- **ACTIONS** (Optional): A list of actions to perform when the option is selected.
- **TIMECOST** (Optional): The amount of time consumed by this action (default is 1).
- **NEXT**: The ID of the next room to move to after this option.

**Example:**

```plaintext
[O]
TEXT=Pick up the torch
CONDITION=!torch
ACTIONS=addItem:torch
NEXT=room1

[O]
TEXT=Go east towards the light
NEXT=room2
```

### Conditions

Conditions control when descriptions and options are available.

- **Syntax**:
  - `item`: The player must have `item`.
  - `!item`: The player must NOT have `item`.

**Example:**

```plaintext
CONDITION=key
```

### Actions

Actions are executed when an option is selected.

- **addItem:item**: Adds `item` to the player's inventory.
- **removeItem:item**: Removes `item` from the player's inventory.

**Example:**

```plaintext
ACTIONS=addItem:key,removeItem:old_key
```

### Time Costs

- **TIMECOST**: Specifies how much time (integer value) the action consumes.
  - If not specified, defaults to 1.

**Example:**

```plaintext
TIMECOST=2
```

### Special Rooms

#### Time End Room

When the timer reaches zero, the game transitions to the `timeEnd` room if it exists.

```plaintext
<timeEnd>
[DESCRIPTION]
TEXT=
Time has run out! The cave collapses around you.
ENDTEXT

[O]
TEXT=Start over
NEXT=room1
```

---

## Example Script

Here's a simplified example script:

```plaintext
# Global settings
TIMER=10
START=room1

<room1>
[DESCRIPTION]
TEXT=
You find yourself in a forest clearing. There is a path leading north and a small hut to the east.
ENDTEXT

[O]
TEXT=Go north along the path
NEXT=forest_path

[O]
TEXT=Enter the hut
NEXT=hut

<forest_path>
[DESCRIPTION]
TEXT=You walk along the path and encounter a wild animal.

[O]
TEXT=Fight the animal
CONDITION=sword
ACTIONS=removeItem:sword
NEXT=forest_clearing

[O]
TEXT=Run away
TIMECOST=2
NEXT=room1

<hut>
[DESCRIPTION]
TEXT=Inside the hut, you find a rusty sword.

[O]
TEXT=Take the sword
CONDITION=!sword
ACTIONS=addItem:sword
NEXT=hut

[O]
TEXT=Leave the hut
NEXT=room1

<forest_clearing>
[DESCRIPTION]
TEXT=
After defeating the animal, you find a hidden treasure!
ENDTEXT

[O]
TEXT=Take the treasure
ACTIONS=addItem:treasure
NEXT=win

<win>
[DESCRIPTION]
TEXT=
Congratulations! You have won the game.
ENDTEXT
```

---

## Creating Your Own Adventure

1. **Create a New Script File**

   Write your game script in a text file (e.g., `script.txt`) following the scripting language guidelines.

2. **Define Global Settings**

   Set the `TIMER` and `START` room.

3. **Design Your Rooms**

   - Define unique room IDs.
   - Write descriptions using `[DESCRIPTION]` blocks.
   - Use conditions to display descriptions based on player state.

4. **Add Options**

   - Provide meaningful choices for the player.
   - Use conditions to control option availability.
   - Specify actions and next rooms.

5. **Implement Game Logic**

   - Use items and conditions to create puzzles and branching paths.
   - Manage the in-game timer with `TIMECOST`.

6. **Test Your Game**

   - Run the game and play through different paths.
   - Debug any issues with conditions or actions.

---

## Disclosure

I'm bad at writing README's so this was written by ChatGPT
