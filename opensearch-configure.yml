FROM opensearchproject/opensearch:latest

USER root

RUN yum update -y && yum install -y python3 python3-pip

RUN pip3 install nltk

RUN pip3 install opensearch-py

USER opensearch
