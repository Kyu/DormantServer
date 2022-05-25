# Bungee Machine Setup  

Run the script in `setup.sh`, after updating the links not provided. 

A sample `config.yml` looks like this:  

```yml
server:
  name: lobby
  port: 25565
aws:
  login:
    access_key_id: YOUR_AWS_ACCESS_KEY_ID
    aws_secret_access_key: YOUR_AWS_SECRET_ACCESS_KEY
  instance:
    id: INSTANCE_ID_FOR_SERVER
kick_message: Server is down. Ask an admin to bring it back up!
wait_message: Spinning up server! This will take a few seconds.
```  

Instead of using a download link, you can make the file yourself, as it's generally not 
a good idea to have your API keys floating around the internet.  

