# MongoDB + supervisor + sshd
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

RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
RUN echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' | tee /etc/apt/sources.list.d/10gen.list

RUN apt-get update -y
RUN apt-get install -y mongodb-org=2.6.1

RUN apt-get install -y openssh-server supervisor ufw
RUN mkdir -p /var/run/sshd
RUN mkdir -p /var/log/supervisor

ADD supervisord.conf /etc/supervisor/conf.d/supervisord.conf
ADD mongodb.conf /data/mongodb.conf

RUN echo 'root:pass' | chpasswd
RUN echo 'PermitRootLogin yes' > /etc/ssh/sshd_config

EXPOSE 22
EXPOSE 27017
EXPOSE 27018
EXPOSE 28017

CMD ["/usr/bin/supervisord"]
# ENTRYPOINT ["mongod", "-f", "/data/mongodb.conf"]
