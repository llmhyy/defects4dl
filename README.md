# Defects4dl
A deep learning general bug library.

Defects4dl is available in two ways，the Command line and Websites.

## Setting up Defects4dl
### Requirements

-  Java>=1.8

-  Docker>=19.03.8

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

    Important!!!
Before using it, you need to pull down our Docker Images from the command line using the `pullBug` command (this process is a bit slow).
If you've already downloaded these Docker Images, you can use `runBug` to start Containers


### Command line:

1.See the help

```
help
```

2.Pull down all Docker Images

```
pullBug
```

3.Start all Containers

```
runBug
```

4.Query all bugs

```
ls
```

5.Keyword query

```
ls grep <Keyword>
```

6.View bug details

```
info <bugId>
```

7.See Diff for Buggy Version and Fix Version

```
diff <bugId>
```

8.Run the buggy Version or Fix Version test case

```
test <bugId> <version>
```

9.Exit the program

```
exit
```

### Web sites:
It is a Spring Boot and Maven project.


You need to configure Maven and it will automatically download dependencies for you.

The entry to the program is: AppEnter

When the project starts,you can use a browser to access `http://127.0.0.1:9000/bugList` ,then you will see the home page of our library.
