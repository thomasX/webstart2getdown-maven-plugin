# _webstart2getdown_ Maven Plugin 

## Summary:

This plugin creates a complete getdown-stub, based on a webstart, generated with webstart-maven-plugin. 

__Example:__

https://github.com/thomasX/webstart2getdown-maven-plugin-example1 

## How to use the Plugin:
### Required POM - Properties: 

* __jnlpfile__ ... points to the generated jnlpFile  

* __appbase__ ... defines the location of the downloadable sources

### Optional POM - Properties: 

* __appdir__ ... defines the Output-Directory for the generated files _(default = target/)_

* __keystore__ ... defines the location of the keystore, only requested if you want to sign your digest files _(default = null)_ *

* __storepass__ ... contains the keystorepassword (default = null)

* __alias__ ... contains the key alias (default = null)

* __bgImage__ ... contains the path to the backgroundimage  (ui.background_image in getdown documentation )

* __iconImage__ ... contains the path to the iconimage (ui.icon in getdown documentation )

* __progressbarColor__ ... Backgroundcolor (ui.background in getdown documentation)

* __progressTextColor__ ... Progressbarcolor (ui.progress_bar in getdown documentation)

* __bgColor__ ... Progressbartext color (ui.progress_test in getdown documentation)

\*_if you want to sign the digest files enter the path to the your keystorefile (see our example https://github.com/thomasX/webstart2getdown-maven-plugin-example1 in this example we use a selfsigned demokey) this demokey shouldn't be used in real production ... if no keystore is defined signing is deactivated._
