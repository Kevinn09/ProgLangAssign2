#setenv SWPATH /Users/username/Documents/Software/
#setenv SWPATH ./

#setenv SALSAPATH $SWPATH/Salsa
#setenv SALSAPATH $SWPATH/Salsa1.1.5.jar

#setenv SALSAOPTS 

# SALSA 1.x aliases

alias salsac 'java -cp salsa1.1.5.jar:. salsac.SalsaCompiler conurrent/*.salsa; javac -cp salsa1.1.5.jar:. \*.java'
#alias salsa 'java -cp $SALSAPATH:. $SALSAOPTS'

#alias wwcns 'java -cp $SALSAPATH.jar:. wwc.naming.WWCNamingServer'
#alias wwctheater 'java -cp $SALSAPATH/salsa$SALSAVER.jar:. $SALSAOPTS wwc.messaging.Theater'
