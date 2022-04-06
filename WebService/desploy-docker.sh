# Crear y configurar el contenedor de la base de datos
function database() {
  echo "Deploying Mariadb database...";
  docker build ./database -t mariadb-with-database;
  id_container=$(docker run --rm -d -p 3306:3306 --name webservice-database --env MYSQL_ROOT_PASSWORD=rodolfo123456789 mariadb-with-database);
  echo ID container: ${id_container}
  docker exec ${id_container} bash -c 'mysql --user root -p${MYSQL_ROOT_PASSWORD} < Database.sql';
  echo "Try: docker exec ${id_container} bash -c 'mysql --user root -p\${MYSQL_ROOT_PASSWORD} < Database.sql';";

}

function webservice() {
    echo "Deploying Java WebService...";
    mvn package;
    docker build . -t spring-boot-webservice;
    docker run --rm -d -p 8443:8443 --name spri --env spring_profiles_active=default spring-boot-webservice;
}

"$@" # Calls function