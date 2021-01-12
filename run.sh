echo "=================================== 拉取代码 ==================================="
sudo git pull
echo "=================================== 开始构建项目 ==================================="
echo "================================= Steep 1 编译打包 ================================="
sudo mvn clean install -Dmaven.test.skip=true
echo "================================ 停止运行的镜像并删除 ==============================="
sudo docker stop dimples-web
sudo docker rm dimples-web
echo "=================================== 删除已有镜像 ==================================="
sudo docker rmi dimples/dimples-web:dimples dimples/dimples-web
echo "================================= Steep 2 构建镜像 ================================="
sudo docker build -t dimples/dimples-web:dimples .
sudo docker rm dimples/dimples-web
sudo docker images
echo "=================================== 构建项目成功 ==================================="
echo "=================================== 开始启动项目 ==================================="
sudo docker run -d -p 8001:8001 --name dimples-web dimples/dimples-web:dimples
sudo docker ps -a
echo "=================================== 启动项目成功 ==================================="


