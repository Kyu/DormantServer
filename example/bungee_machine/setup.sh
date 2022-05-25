# Assuming a linux Machine
# some links will need to be replaced
# This assumes you already have Java installed

# Make the bungee folder
mkdir bungee
cd bungee

# Download the bungeecord executable
wget https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar

# Pre make the plugin directory
mkdir plugins/DormantServer -p

# Download plugin and the pre-made config
wget -O "plugins/DormantServer.jar" INSERT_DORMANT_SERVER_JAR_LINK_HERE ###############
wget -O "plugins/DormantServer/config.yml" INSERT_CONFIG_LINK_HERE ############

# Run the BungeeCord server
echo "java -Xms512M -Xmx512M -jar BungeeCord.jar" > start.sh

chmod +x start.sh
./start.sh



