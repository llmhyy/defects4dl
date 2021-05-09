# Defects4dl
A deep learning general bug library.

Defects4dl is available in two ways，the Command line and Websites.

## Setting up Defects4dl
### Requirements

-  Java>=1.8

-  Docker

-  Maven

## Steps to set up Defects4dl

### Set up from source code

1.Clone Defects4dl:

```
git clone https://github.com/llmhyy/defects4dl.git
```

2.Import  the project  to IntelliJ IDEA as a general program


3.Run as a java application in IntelliJ IDEA ,enter the `help` command in the running terminal to get guidance.

### Set up from releases





## Using Defects4dl

Important!
* Before using it, you need to pull down our Docker Images from the command line using the `pullBug` command (this process is a bit slow).
* You can also pull a single image through the `pullOneBug <bugId>` command.
* If you've already downloaded these Docker Images, you can use `runBug` to start all Containers or `startOneBug <bugId>` command to start a single container.


### Command line:

1.See the help

```
help
```

2.Pull down all Docker Images

```
pullBug
```

3.Pull down one Docker Image

```
pullOneBug <bugId>
```


4.Start all Containers

```
runBug
```

5.Start one Container

```
startOneBug <bugId>
```

6.Query all bugs

```
ls
```

7.Keyword query

```
ls grep <Keyword>
```

8.View bug details

```
info <bugId>
```

9.See Diff for Buggy Version and Fix Version

```
diff <bugId>
```

10.Run the buggy Version or Fix Version test case

```
test <bugId> <version>
```

11.Exit the program

```
exit
```

### Web sites:
It is a Spring Boot and Maven project.


You need to configure Maven and it will automatically download dependencies for you.

The entry to the program is: AppEnter

When the project starts,you can use a browser to access *[http://127.0.0.1:9000/bugList](http://127.0.0.1:9000/bugList) ,then you will see the home page of our library.
