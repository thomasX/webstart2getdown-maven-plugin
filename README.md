# _webstart2getdown_ Maven Plugin 

## [Table of Contents:](#table-of-contents)

### 1. [Summary](#summary)
### 2. [Intention](#intention)
### 3. [Goals](#goals)
### 4. [Configuration](#configuration)
### 5. [Usage](#usage)
### 6. [Example](#example)

***

## [Summary:](#summary)

This plugin is designed to create a complete [GETDOWN]() - project, which includes the **getdown.txt**, **digest.txt** and **digest2.txt**. The **getdown.txt** will be created from a jnlp - File. The [maven-webstart-plugin]() creates the needed jnlp - File. Further this plugin is able to sign your code with a specific **keystore**. However this feature is only optional (see [Features](#features)).

## [Features:](#features)

Feature | Description
--------|------------
getdown.txt - generation | The plugin will create your getdown.txt with all the needed infromation by itself. For more information considering the getdown.txt - file visite the [original wiki]()!
digest\[2\].txt - generation | The Generation of the digest - files (digest.txt and digest2.txt) will also happen automatically. These digest - files include the hashes of your project files. With these hashes Getdown is able to tell if it has to update or redownload any files.
signing | Optionally you can provide a keystore, with which the plugin should sign your files. If you don't provide any keystore - files the plugin will not sign anything.

## [Goals:](#goals)

There are a few different goals, which can be declared in the plugin configuration inside your pom.xml - file. 

1. `getdowntxt`\
This goal will trigger the getdown - creation. This includes the files getdown.txt, digest.txt and digest2.txt. 

2. `digest`\
This goal will sign your the files, which has been created by the **getdowntxt - goal**, if you provide a keystore.

## [Configuration:](#configuration)

### Minimum Configuration:

>`jnlpfile` points to the generated jnlp - File 

>`appbase` defines the source path to the source - Files

### Further Configuration:

>`appdir` defines the output directory for the getdown - files (default `{project.basedir}\target\` will be used)

>`keystore` defines the location of the keystore.jks for code signing (default it will be set to `null` so signing is disabled)

>`storepass` defines the password for the keystore.jks (default it will be set to `null`)

>`alias` defines the alias for the used key in your keystore (default it will be set to `null`)

>`bgImage` defines the location of the background image (default it will be set to `null`)

>`bgColor` defines the background color which is shown when the background image is not set or is not loaded (default it will be set to `null`)

>`iconImage` defines the location of the icon image (default it will be set to `null`)

>`progressbarColor` defines the color of the progressbar (default it will be set to `null`)

>`progressTextColor` defines the color of the progressbar text (default it will be set to `null`)

## [Usage:](#usage)

```XML
<project>
  ...
  <dependencies>
    ...
    <dependency>
      <!-- getdown -->
    </dependency>
    ...
  </dependencies>
  ...
  <build>
    ...
    <!-- webstart-maven-plugin -->
    ...
  <plugin>
    <groupId>info.margreiter.getdown</groupId>
    <artifactId>webstart2getdown-maven-plugin</artifactId>
    <version>1.0.2</version>   <!-- version can be different -->
      <executions>
        <execution>	
          <phase>package</phase>
            <goals>
              <!-- define goals here | see goals - section for more information --> 
            </goals>
          <configuration>
            <!-- define configuration here | see configuration - section for more information --> 
          </configuration>
        </execution>
      </executions>
    </plugin>
    ...
  </build>
  ...
  <pluginRepositories>
    <pluginRepository>
      <id>getdown-maven-github-repo</id>
      <url>https://thomasx.github.io/webstart2getdown-maven-plugin/</url>
      <snapshots>
        <enabled>true</enabled>
        <updatePolicy>always</updatePolicy>
      </snapshots>
    </pluginRepository>
  </pluginRepositories>
  ...
</project>
```

## [Example:](#example)

https://github.com/thomasX/webstart2getdown-maven-plugin-example1 
