FROM ubuntu:14.04

RUN echo "debconf debconf/frontend select Noninteractive" | debconf-set-selections

RUN sudo apt-get update -y

RUN sudo apt-get install dialog -y

RUN sudo apt-get install build-essential -y

ADD checker.py /home/check/

ADD testlib.h /home/check/

ADD data.json /home/check/

WORKDIR /home/check

CMD ["python3", "./checker.py"]