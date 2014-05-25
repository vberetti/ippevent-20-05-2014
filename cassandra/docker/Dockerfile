# Cassandra + supervisor + sshd
#
# VERSION               0.0.1
#
# requires mongodb.conf
# requires supervisord.conf

FROM ubuntu
MAINTAINER vberetti

# make sure the package repository is up to date
RUN echo "deb http://archive.ubuntu.com/ubuntu trusty main universe" > /etc/apt/sources.list
RUN apt-get update

# Install dependencies
RUN apt-get -y update
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y --force-yes software-properties-common python-software-properties
RUN add-apt-repository -y ppa:webupd8team/java
RUN apt-get -y update
RUN /bin/echo debconf shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install oracle-java7-installer oracle-java7-set-default
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y wget dnsmasq-base python2.7 vim less iputils-ping

# Get the latest stable version of Cassandra
RUN wget -q -O - http://downloads.datastax.com/community/dsc-cassandra-2.0.7-bin.tar.gz \
  | tar -C /opt -xz
RUN wget -q -O - http://downloads.datastax.com/community/datastax-agent-4.1.2.tar.gz \
  | tar -C /opt -xz
RUN echo "stomp_interface: 172.17.42.1\nlocal_interface : XXXXXX" >> /opt/datastax-agent-4.1.2/conf/address.yaml

RUN apt-get install -y openssh-server supervisor ufw
RUN mkdir -p /var/run/sshd
RUN mkdir -p /var/log/supervisor

ADD supervisord.conf /etc/supervisor/conf.d/supervisord.conf
ADD cassandra.yaml /opt/dsc-cassandra-2.0.7/conf/cassandra.yaml

RUN echo 'root:pass' | chpasswd
RUN echo 'PermitRootLogin yes' > /etc/ssh/sshd_config

CMD ["/usr/bin/supervisord"]

# SSH
EXPOSE 22
# Cluster communication
EXPOSE 7000
# Thrift
EXPOSE 9160
# Native
EXPOSE 9042
# JMX
EXPOSE 7199
# AGENT
EXPOSE 61621
