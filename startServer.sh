rm -rf server/
mv temp server
mkdir temp

cd server
unzip recipeapp-1.0-SNAPSHOT.zip
sudo systemctl stop lingrecipe
sudo systemctl restart lingrecipe

# start with log
#sudo systemctl stop lingrecipe
#cd server/recipeapp-1.0-SNAPSHOT/bin/
#./recipeapp
