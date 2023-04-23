cd /etc/systemd/system/
sudo nano rest-demo.service (paste systemctl-file)
sudo systemctl start rest-demo.service
sudo systemctl enable rest-demo.service

http://18.195.124.1:8080/swagger-ui.html

DOCKER

-- neophodno je imati nalog na Docker Hub

--samo inicijalno
docker login

docker pull andreajo/employees:1.0

docker pull andreajo/patients:1.0

docker pull andreajo/infirmary:1.0

docker pull andreajo/laboratory:1.0

docker pull andreajo/frontend:1.0

docker network create my_local_network

--nakon toga treba preko komandne linje doci do lokacije docker-compose fajla koji se nalazi u folderu za projekat
Bolnica-1-Backend i Bolnica-1-Frontend
za svaki posebno pokrenuti--

docker-compose up