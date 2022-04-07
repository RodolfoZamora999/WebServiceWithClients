# Crear y configurar el contenedor de la base de datos
#Nota, la base de datos se tarda en inicializar completamente
function database() {
  echo "Creating network...";
  docker network create web-service-network;
  echo "Deploying Mariadb database...";
  docker build ./database -t mariadb-with-database;
  docker run --rm -d -p 3306:3306 --name webservice-database --network web-service-network --network-alias mariadb-database \
    --env MARIADB_ROOT_PASSWORD=rodolfo123456789 --env MARIADB_DATABASE=webservice_db mariadb-with-database;
}

function webservice() {
    echo "Deploying Java WebService...";
    mvn package;
    docker build . -t spring-boot-webservice;
    docker run --rm -d -p 8443:8443 --name webservice-spring-boot --network web-service-network --network-alias web-service \
      --env spring_profiles_active=prod spring-boot-webservice;
}

"$@" # Calls function