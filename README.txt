Programming Languages Assignment 2

Rasika Ekhalikar and Kevin De Leon
RCS ID:ekhalr and deleok

Features
--------------------------------------------------------------------------
Concurrent
	Used the radial growth algorithm to find the next leader a person revolts when the current leader has been leader longer than the person's tolerance when n+1/2 people have revolted, it is time to elect a new leader
-------------------------------------
Distributed
	Use same basic ideas as concurrent, but run the program as a distributed system. For UAN("uan :// nameserver/id"), the "id" used is ID of the node



Bugs
--------------------------------------------------------------------------
No error handling if the connection does not work- Error check ConnectException did not work

Other Notes:
If the prioirity of two nodes are the same, the higher id will take priority to find the leader, we go to the left and right to pass timestamp messages, we only pass to the left as specificed in the hw file



How we Compiled and Ran the program:
--------------------------------------------------------------------------
Concurrent
alias salsac="java -cp salsa1.1.5.jar:. salsac.SalsaCompiler concurrent/*.salsa; javac -cp salsa1.1.5.jar:. concurrent/*.java"

alias salsa="java -cp salsa1.1.5.jar:. concurrent.Run config.tsv"

-------------------------------------
Distributed
alias salsac="java -cp salsa1.1.5.jar:. salsac.SalsaCompiler distributed/*.salsa; javac -cp salsa1.1.5.jar:. distributed/*.java"

alias salsa="java -cp salsa1.1.5.jar:. distributed.Run config.tsv 127.0.0.1:3030"

alias wwcns="java -cp salsa1.1.5.jar:. wwc.naming.WWCNamingServer"

alias wwctheater="java -cp salsa1.1.5.jar:. wwc.messaging.Theater"

-------------------------------------
Both concurrent and distributed were compiled with "salsac", and run with "salsa"
No arguments were needed since we included them in the alias.

The alias for the theater obviously does not include a specific port, so should be added in command line
