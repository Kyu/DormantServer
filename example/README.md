# Example Setup guide  

For this example, the Bungee Machine is assumed to be your own house machine, and the Server Machine is assumed to be
an AWS EC2 instance. The minecraft server port is 25565. You can run the Bungee Machine on an AWS free tier instance though.  

You'll want to set up an AWS EC2 instance, 2-4Gb of RAM is recommended for a small sized server.  
Remember to allow all TCP/UDP connections to 25565 in the network firewall options.

You'll then want to follow [the server machine instructions](server_machine)  


After that, you'll want to follow [the bungee machine instructions](bungee_machine). Use the EC2 instance ID you got in
the config for instructions.  


And Voilà, you'll have a dormant server set up.