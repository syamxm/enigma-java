# Enigma Java Project

This is a small Java project that runs an Enigma machine program.

## What you need

You need Java installed on your computer.

Check Java with:

```bash
java -version
javac -version
```

```cmd
java -version
javac -version
```

## Get the project

Download or clone the project:

```bash
git clone https://github.com/syamxm/enigma-java.git
```

Go into the project folder:

```bash
cd enigma-java
```

## Remove Git history

If you want to use this project as a normal folder and not as a Git repository, remove the `.git` folder.

On macOS or Linux:

```bash
rm -rf .git
```

On Windows Command Prompt:

```cmd
rmdir /s /q .git
```

## Run the project

Make sure you are in the main project folder.

You should see the `com` folder there.

Compile and run the program with:

macOS or Linux:

```bash
javac com/syamxm/enigma/*.java && java com.syamxm.enigma.Main
```

Windows:

```cmd
javac com\syamxm\enigma\*.java && java com.syamxm.enigma.Main
```


## Clean compiled files

Java creates `.class` files after compiling.

To remove them on macOS or Linux:

```bash
find com/syamxm/enigma -name "*.class" -delete
```

On Windows Command Prompt:

```cmd
del /s com\syamxm\enigma\*.class
```

## Project files

Main files:

- `Main.java` starts the program.
- `Enigma.java` contains the main Enigma logic.
- `Rotor.java` contains rotor logic.
- `Reflector.java` contains reflector logic.
- `Plugboard.java` contains plugboard logic.

Custom files: 
- `CustomEnigma.java` custom Enigma logic with custom rotor and reflector
- `CustomRotor.java` contains custom rotor logic
- `CustomReflector.java` contains custom reflector logic
