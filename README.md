# Dormant Server  

For server runners on a budget.  

Scenario: You're a poor CS college student, trying to run a minecraft server with your friends. 
You dont want to host on your own machine, but you also dont want to spend a full $20/month for a server that might only be active
5-10 hours a day.

The plan: You'll host a BungeeCord server on the AWS free tier plan, as that doesn't require much RAM. This server will 
detect if someone wants to join the server, and spin it up on demand. After 30 mins of inactivity, the server will shut 
itself down. This plan will save you 50-75% of costs.  

## Installation  

Set up an EC2 instance with your minecraft server. Make sure `offline-mode=false` in server.properties and `connection-
throttle: -1` in bukkit.yml  

Download and setup BungeeCord. Install this plugin, and set up the config with your AWS credentials, as well as the 
instance ID from above.  

[BungeeCord wiki](https://www.spigotmc.org/wiki/bungeecord/)   
[My Example](example)  



