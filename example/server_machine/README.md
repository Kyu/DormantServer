# Server Machine Setup  

Create your EC2 Instance using AWS, and SSH into it. Save the EC2 
instance ID, you'll need it when you set up the Bungee Server.  


Run the code in `start.sh`. You may need to update the server jar download link in the future. Right now it is 1.18.2.  

When you're done, you'll want to run the command `crontab -e`. In the file provided, you'll want to add the line
`@reboot  /path/to/server/start.sh`. I'm currently unsure of how to automate this via script.

Then follow the instructions in the Bungee Machine folder.