# Dormant Server  
For server runners on a budget.    

## What it does

This plugin monitors a single minecraft server connected to BungeeCord. If the server is inactive for 30 minutes, it sends
a command to AWS to shut the server down. If the server is down, and a player tries to connect, it sends a command to AWS
to spin the server back up.

## Installation

Set up an EC2 instance with your minecraft server. Make sure `offline-mode=false` in server.properties and `connection-throttle: -1` in bukkit.yml

Download and setup BungeeCord. Install this plugin, and set up the config with your AWS credentials, as well as the
instance id you obtained from setting up the EC2 instance. [An example is provided](example).


## Permissions  

`dormantserver.start` - Permission to start the server whenever you want


## Config

A view of what the config is like is shown below:

```yml
server:
  name: lobby 
  port: 25565
  max_idle: 30 # Number of minutes of inactivity until the server is shut down
  check_idle_delay: 5 # Number of minutes to wait to check whether the server is active or not
  max_ping_timeout: 300 # Milliseconds to wait until determining whether the server is up for not
  boot_delay_seconds: 0.5 # Number of seconds to wait, after a request is sent to AWS to start the server
aws:
  login:
    access_key_id: YOUR_AWS_ACCESS_KEY_ID
    aws_secret_access_key: YOUR_AWS_SECRET_ACCESS_KEY
  instance:
    id: INSTANCE_ID_FOR_SERVER
wait_message: Spinning up server! This will take a few seconds. # For players who have permission to start the server
kick_message: Server is down. Ask an admin to bring it back up! # For players who don't have permission to start the server

```  


### Scenario

You're a poor CS college student, trying to run a minecraft server with your friends. 
You don't want to host on your own machine, but you also don't want to spend a full $20/month for a server that might only be active
5-10 hours a day.

The plan: You'll host a BungeeCord server on the AWS free tier plan, as that doesn't require much RAM. This server will 
detect if someone wants to join the server, and spin it up on demand. After 30 minutes of inactivity, the server will shut 
itself down. This plan will save you 50-75% of costs.  





## Other references  
[BungeeCord wiki](https://www.spigotmc.org/wiki/bungeecord/)   
[My Example](example)    





