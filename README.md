# webstart2wetdown Maven Plugin 

this plugin creates a complete getdown stub based on a webstart(generated with webstart-maven-plugin) ... see the example1 on  

# requested pom properties: 

jnlpfile ... points to the generated jnlpFile (see our example https://github.com/thomasX/webstart2getdown-maven-plugin-example1 ) 

appbase ... see Getdown documentation or have a look at (see our example https://github.com/thomasX/webstart2getdown-maven-plugin-example1 ) 

# option po properties: 

appdir .... default = target    (see our example https://github.com/thomasX/webstart2getdown-maven-plugin-example1 ) 

keystore .... if you want to sign the digest files enter the path to the your keystorefile (default = null    (see our example https://github.com/thomasX/webstart2getdown-maven-plugin-example1 )  in this example we use a selfsigned demokey ) this demokey shouldn't be used in real production 
if no keystore is defined signing is deactivated. 

storepass ... contains the keystorepassword (default = null)

alias ... contains the key alias (default = null)








 
