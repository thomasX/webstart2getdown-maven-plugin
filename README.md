# :white_check_mark: _webstart2getdown_ Maven Plugin 

## [Table of Contents:](#table-of-contents)

### 1. [Summary](#summary)
### 2. [Features](#features)
### 3. [Goals](#goals)
### 4. [Configuration](#configuration)
### 5. [Usage](#usage)
### 6. [Example](#example)

***

## [Summary:](#summary)

This plugin is designed to create a complete [GETDOWN](https://github.com/threerings/getdown/) - project, which includes the **getdown.txt**, **digest.txt** and **digest2.txt**. The **getdown.txt** will be created from a jnlp - File. The [maven-webstart-plugin](https://mvnrepository.com/artifact/org.codehaus.mojo/webstart-maven-plugin) creates the needed jnlp - File. Further this plugin is able to sign your code with a specific **keystore**. However this feature is only optional (see [Features](#features)).

## [Features:](#features)

Feature | Description
--------|------------
getdown.txt - generation | The plugin will create your getdown.txt with all the needed infromation by itself. For more information considering the getdown.txt - file visite the [original wiki](https://github.com/threerings/getdown/wiki)!
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

>`version` defines the current version (default it will be set to `null`)

>`allow_offline` defines if getdown is allowed to execute the application if no connection to appbase is established (default it will be set to `false`)

>`ui_background` defines the background color which is shown when the background image is not set or is not loaded (default it will be set to `null`)

>`ui_background_image` defines the location of the background image (default it will be set to `null`)

>`ui_error_background` defines the location of the error background image (default it will be set to `null`)

>`ui_icon` defines the location of the icon image (default it will be set to `null`)

>`ui_progress` defines the dimensions of the rectangle in which the progress displays (default it will be set to `null`)<br>
>>> eg. "17,321,358,22" <br>
	    17 pixels from the left of the window <br>
	    321 pixels from the top of the window <br>
	    458 pixels wide <br>
	    22 pixels tall <br>

>`ui_progress_bar` defines the color of the progressbar (default it will be set to `null`)

>`ui_progress_text` defines the color of the progressbar text (default it will be set to `black`)

>`ui_progress_image` defines the image of the progressbar (default it will be set to `null`)

>`ui_status` defines the color of the status text (default it will be set to `black`)

>`ui_text_shadow` definest the color of the text shadow (default it will be set to `null`so shadow is disabled)

>`ui_hide_decorations` defines if ui decorations should be hidden (default it will be `false`)

>`ui_min_show_seconds` defines the minimum show tim for getdown (default it will be set to `5s`)

>`ui_install_error` defines the URL which will be shown if an error occurs (default it will be set to `null`)

>`ui_mac_dock_icon` defines the location of the dock icon for MACs (default it will be set to `null`)

>`jvmargs` defines the arguments for the JVM (more values possible see [Example](#example), default it will be set to `null`)

>`appargs` defines the arguments for your application (more values possible see [Example](#example), default it will be set to `null`)

>`keystore` defines the location of the keystore.jks for code signing (default it will be set to `null` so signing is disabled)

>`storepass` defines the password for the keystore.jks (default it will be set to `null`)

>`alias` defines the alias for the used key in your keystore (default it will be set to `null`)

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

***

**by** [thomasX](https://github.com/thomasX) and [Maz47](https://github.com/maz47)
