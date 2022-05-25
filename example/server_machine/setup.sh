# Assuming a linux machine

# Install Java
sudo apt update; sudo apt install openjdk-18-jre-headless -y;

# Make a server directory
mkdir dormant_server
cd dormant_server

# 1.18.2 as of May 24, 2022. Might need to update this link.
# Download Paper (Backward compatible with spigot, using for easier access to a download link [plus I am seeing that it's much faster!] )
wget https://api.papermc.io/v2/projects/paper/versions/1.18.2/builds/344/downloads/paper-1.18.2-344.jar -O Server.jar

# Agree to the EULA and set config options in advance
echo "eula=true" > eula.txt

echo "online-mode: false" > server.properties
echo "connection-throttle: -1" > bukkit.yml
echo "java -Xms1G -Xmx2G -XX:+UseG1GC -DIReallyKnowWhatIAmDoingISwear -jar Server.jar nogui" > start.sh

# Allow connections to 25565
sudo ufw allow 25565

chmod +x start.sh

./start.sh