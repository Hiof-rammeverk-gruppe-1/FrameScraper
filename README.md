# FrameScraper user manual

## Description
FrameScraper is a Java framework designed to allow users to scrape and search through HTML code found in websites.

This framework was made as a part of the framework course at Østfold Univeristy College. Part of the reason why we made this framework was that we wished to have a quick and easy system for parsing HTML code and searching for specific content in said code.

FrameScraper is recognized through both Maven and Gradle, so you can easily implement it into your own projects with a build.gradle dependency or a maven dependency; how to do this is described below.

For more documentation please refer to our [JavaDoc](https://javadoc.jitpack.io/com/github/Hiof-rammeverk-gruppe-1/Framescraper/1.0.0/javadoc/).


## Adding FrameScraper to IntelliJ

FrameScraper is accessed through dependencies with maven and gradle.

#### With Gradle:
##### Create Gradle project:
1. Click `New Project`
2. Select `Gradle` from the options on the left
3. Specify projects SDK(JDK), and make sure the `Java`-box beneath is checked. Click `Next`
4. On the next page, give the project a `Name` and `Location`, and specify the following Gradle project details that are added to the projects pom-file: `GroupId` and `ArtifactId`. Click Finish

(Continue below for how to add the framework to the Gradle-project)
##### Adding to Gradle project:
1. Open the projects `build.gradle` file, and add the following in the `<project>`-tag:

```
repositories {
	 maven { url 'https://jitpack.io' }
}
dependencies {
	implementation 'com.github.Hiof-rammeverk-gruppe-1:Framescraper:1.0.0' 
}
```
2. When added, open your Gradle Projects tool window to the right, and `Reload`.

You can now use the framework in your code.


#### With Maven:
##### Create Maven project:
1. Click `New Project`
2. Select `Maven` from the options on the left
3. Specify projects SDK(JDK), and Click `Next`
4. On the next page, give the project a `Name` and `Location`, and specify the following Maven project details that are added to the projects pom-file: `GroupId` and `ArtifactId`. Click Finish

(Continue below for how to add the framework to the Maven-project)

##### Adding to Maven project:
1. Open the projects `pom.xml` file, and add the following at the bottom, or in their dedicated tags:

```
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependencies>
	<dependency>
		<groupId>com.github.Hiof-rammeverk-gruppe-1</groupId>
		<artifactId>Framescraper</artifactId>
		<version>1.0.0</version>
	</dependency>
</dependencies>
```
2. When added, open your Maven Projects tool window to the right, and `Reload`.

You can now use the framework in your code.


## Getting Started

This is a guide for how to start using the webscraper FrameScraper:

#### With webpage:

1. Initializing a `Scraper`-object.
     ```
         Scraper sc = Scraper.buildScraperWithWebUrl("www.webpage.com");
     ```
     (www. webpage .com must be replaced by the URL of the webpage you wish to scrape)


2. You can now use the `sc`-object to access the methods in the framework
     ```
         sc.X();
     ```
     (X must be replaced by the method you wish to use)



#### With HTML-file:

1. Initializing a `Scraper`-object.
     ```
         Scraper sc = Scraper.buildScraperWithFile("FileToScrape.html");
     ```
     (FileToScrape.html must be replaced by the full filepath of the file you wish to scrape.)


2. You can now use the `sc`-object to access the methods in the framework
     ```
         sc.X();
     ```
     (X must be replaced by the method you wish to use)



#### With HTML-String:

1. Initializing a `Scraper`-object.
     ```
         Scraper sc = Scraper.buildScraperWithString("<html><body><h1>Hello world</h1></body></html>");
     ```
     (The HTML-code must be replaced by the HTML-String you wish to scrape.)


2. You can now use the `sc`-object to access the methods in the framework
     ```
         sc.X();
     ```
     (X must be replaced by the method you wish to use)



## Javadoc
The Javadoc for the framework can be accessed to [this page](https://javadoc.jitpack.io/com/github/Hiof-rammeverk-gruppe-1/Framescraper/1.0.0/javadoc/)



## A simple example of FrameScraper in use:
```
        Scraper sc = Scraper.buildScraperWithString("<html><body><h1>Hello world</h1><p>This is the text-content of the HTML-p-tag.</p><p>Text2</p></body></html>");

	sc.getContentFromTagAsString("p");

	(This will return a Arraylist of Strings: [This is the text-content of the HTML-p-tag, Text2])
	
```
