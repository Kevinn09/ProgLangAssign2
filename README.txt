Programming Languages Assignment 2
Rasika Ekhalikar and Kevin De Leon
ekhalr and deleok

Features
	Concurrent
		Used the radial growth algorithm to find the next leader
		A person revolts when the current leader has been leader longer than 
		the person's tolerance
		When n+1/2 people have revolted, it is time to elect a new leader


	Distributed
		Use same basic ideas as concurrent, but run the program as a distributed system.
		For UAN("uan :// nameserver/id"), the "id" used is ID of the node


Bugs
No error handling if the connection does not work- Error check ConnectException did not work

Other Notes:
To find the leader, we go to the left and right
To pass timestamp messages, we only pass to the left as specificed in the hw file
