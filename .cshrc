#setenv SWPATH /Users/username/Documents/Software/
#setenv SALSAPATH $SWPATH/Salsa
#setenv SALSAVER 1.1.5
#setenv SALSAOPTS 

# SALSA 1.x aliases
alias salsac="java -cp salsa1.1.5.jar:. salsac.SalsaCompiler concurrent/*.salsa; javac -cp salsa1.1.5.jar:. concurrent/*.java"

alias salsa="java -cp salsa1.1.5.jar:. concurrent.RadialGrowth concurrent.Node config.tsv"

#alias wwcns="java -cp $SALSAPATH/salsa$SALSAVER.jar:. wwc.naming.WWCNamingServer"
#alias wwctheater 'java -cp $SALSAPATH/salsa$SALSAVER.jar:. $SALSAOPTS wwc.messaging.Theater'
