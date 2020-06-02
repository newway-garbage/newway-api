#!/bin/sh

postgres=newway_postgres

declare -a arr=("$postgres")

start_docker() {
  echo "======== opening docker ========"
  open /Applications/Docker.app
  echo "starting docker! please wait..."
  while true; do
    docker_state=$(docker info >/dev/null 2>&1)
    if [[ $? -ne 0 ]]; then
      echo "."
      sleep 5
    else
      echo "> docker started!"
      break
    fi
  done
}

close_running_containers() {
  echo "======== closing running containers ========"
  for i in "${arr[@]}"; do
    for cnt in $(docker ps | awk '{print $1 "|" $2}'); do
      id=$(echo $cnt | awk -F\| '{print $1}')
      image=$(echo $cnt | awk -F\| '{print $2}')
      name=$(echo $i | awk -F\_ '{print $2}')
      if [[ $image == *$name* ]]; then
        docker stop $id
      fi
    done
  done
  echo "> closed running containers"
}

compose_up() {
  echo "======== running: docker-compose up -d ========"
  docker-compose up -d
  for i in "${arr[@]}"; do
    docker start $i
  done
  echo "> docker-compose done"
}

start_docker
close_running_containers
compose_up