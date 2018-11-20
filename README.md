# webstart2getdown Maven Plugin 

this plugin creates a complete getdown-stub based on a webstart (generated with webstart-maven-plugin) ... see the example1 on https://github.com/thomasX/webstart2getdown-maven-plugin-example1 

# requested pom properties: 

`jnlpfile` ... points to the generated jnlpFile (see our example https://github.com/thomasX/webstart2getdown-maven-plugin-example1 ) 

`appbase` ... see Getdown documentation or have a look at (see our example https://github.com/thomasX/webstart2getdown-maven-plugin-example1 ) 

# optional properties: 

`appdir` .... default = target    (see our example https://github.com/thomasX/webstart2getdown-maven-plugin-example1 ) 

`keystore` .... if you want to sign the digest files enter the path to the your keystorefile (default = null    (see our example https://github.com/thomasX/webstart2getdown-maven-plugin-example1 )  in this example we use a selfsigned demokey ) this demokey shouldn't be used in real production 
if no keystore is defined signing is deactivated. 

`storepass` ... contains the keystorepassword (default = null)

`alias` ... contains the key alias (default = null)

`bgImage` ..... contains the path to the backgroundimage  (ui.background_image in getdown documentation )

`iconImage` ..... contains the path to the iconimage (ui.icon in getdown documentation )

`progressbarColor` ..... Backgroundcolor (ui.background in getdown documentation)

`progressTextColor`...... Progressbarcolor (ui.progress_bar in getdown documentation)

`bgColor`...... Progressbartext color (ui.progress_test in getdown documentation)










 
