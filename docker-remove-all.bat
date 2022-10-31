::docker purging commands. WARNING! This will remove every container in your local Docker!
:: flags: -a lists all elements, -q passes each element to the main command

::stops all containers
powershell docker stop $(docker ps -a -q)

::removes all containers
powershell docker rm $(docker ps -a -q)

::removes all images. -a will list all docker images, -q will pass image to rmi command
powershell docker rmi $(docker images -a -q)

::remove all unused volumes
powershell docker volume prune