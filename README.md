# SwissCheese

This is a game in which a player has to find a way out of a proceduraly generated maze. 
The maze is generated through the Randomized Depth First Search algorithm. 
The graphics are rendered and displayed using a raycasting engine, similar to and inspired by classics like "Wolfenstein 3D". 
The maze is generated with Java's SecureRandom, meaning that every maze is truly random and unique.
The game can be customized by selecting a rendering engine that works best for the system and by creating [texture packs](https://gitlab.com/poof/swisscheese/wikis/Texture-Packs)

![Demo with Retro Texture-Pack](/swisscheese-demo.mp4)

**This project's primary/upstream repository is on [GitLab](https://gitlab.com/poof/swisscheese).**

## Getting Started

##### Maven (in Linux or Mac OS command-line):

1. Clone this repository into a new directory. 
2. cd into new directory.  
3. To clean, download dependencies, compile and test the game:
```
$ mvn clean install
```
4. To run the game:
```
$ mvn exec:java
```

##### Eclipse (tested as of Oxygen)

1. In Eclipse: `File > Import > Maven > Existing Maven Projects`
    - select the project directory and import.
2. In Package Explorer: right click on added project (swisscheese)
    - `Run As > Maven Install`
        - If try `Run As > Maven generate-sources` and then `Run As > Maven install` if dependancy errors occur.
3. In Package Explorer: right click on swisscheese:
    - `Run As > Java Application`
    - select `org.swisscheese.swisscheese.SwissCheese` if prompted for main class.

##### NetBeans (tested as of 8.2)

1. In Netbeans: `File > Open Project`
    - Navigate to project directory and open (should appear with 'm' icon).
2. In Package Explorer: right click on the project and right click and select:
    - `Clean`
    - `Build with Dependancies`
3. Select the project and `Run`.
    - Select `org.swisscheese.swisscheese.SwissCheese` as main class if prompted.


## Prerequisites

1. JRE 1.8 (to run)
2. JDK or OpenJDK 8 (to compile)
3. Eclipse Mars (or later) or NetBeans 8.2 (or later) to run from an IDE.
    - (optional) Maven in command line to run without an IDE.


## Built With

* [GSON](https://github.com/google/gson) - Used to serialize and deserialize objects.
* [Maven](https://maven.apache.org/) - Used for Dependecy Management.

## Authors

 **Alex Kalinins** - *Everything* - [poof](https://gitlab.com/poof)

## License

This project is licensed under the Affero GPL v3 (AGPL v3) License - see the [LICENSE.md](https://gitlab.com/poof/swisscheese/LICENSE.md) file for more details
