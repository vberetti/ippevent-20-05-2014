Ippevent "Regarde les instances tomber"
===================

{:toc}

## Requirements

* Docker
* Java 8
* Maven 3

## MongoDB

### Build docker container
```sh
    cd mongo/docker/
    docker build -t vberetti/ippevent-mongo .
```
### Start a cluster
```sh
    docker run -d --name <name> --privileged vberetti/ippevent-mongo
```
Note : '--privileged' option is required if you want to configure IPTables.

### Example : 5 nodes replicatSet

#### Docker instances

```sh
    docker run -d --name node1 --privileged vberetti/ippevent-mongo
    docker run -d --name node2 --privileged vberetti/ippevent-mongo
    docker run -d --name node3 --privileged vberetti/ippevent-mongo
    docker run -d --name node4 --privileged vberetti/ippevent-mongo
    docker run -d --name node5 --privileged vberetti/ippevent-mongo
```
By default, docker should allocate IP address on 172.17.0.*.
Check this with :
```sh
    docker inspect node1 | grep 'IPAddress'
```
We should have :
* node 1 on IP : 172.17.0.2
* node 2 on IP : 172.17.0.3
* node 3 on IP : 172.17.0.4
* node 4 on IP : 172.17.0.5
* node 5 on IP : 172.17.0.6

#### Configure replicaSet

Connect on node 1 and configure the replicaSet with the file 5-nodes-cluster.txt contained in _mongo_ directory of this repository :
```sh
    mongo 172.17.0.2 < mongo/5-nodes-replica-set.txt
```
This file contains the initialisation of the replicaSet
```
    rs.initiate()
    conf = rs.conf()
    conf.members[0].host="172.17.0.2:27017"
    rs.reconfig(conf)
    rs.add('172.17.0.3')
    rs.add('172.17.0.4')
    rs.add('172.17.0.5')
    rs.add('172.17.0.6')
```
Note : the IP adress is changed after the rs.initiate() to provide externall IP address.

#### Stop and reuse docker instances
Stop all instances
```sh
    docker stop node1 node2 node3 node4 node5
```

Restart instances
Note : before starting the cluster, make sure no other docker container is running on expected IPs 172.17.0.[2-6].
```sh
    docker start node1
    docker start node2
    docker start node3
    docker start node4
    docker start node5
```
### MongoDB Rest API
#### Configure mongo seeds
Edit file located in _mongodb-rest-api/src/main/resources/mongo.properties_
#### Start the Rest API
```sh
    cd mongodb-rest-api
    mvn spring-boot:run
```
After startup, api is available at http://localhost:8080/users

## Cassandra
### Build docker container
```sh
    cd mongo/docker/
    docker build -t vberetti/ippevent-cassandra .
```
### Start a cluster
```sh
    docker run -d --name <name> --privileged vberetti/ippevent-cassandra
```
Note : '--privileged' option is required if you want to configure IPTables.
