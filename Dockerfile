FROM java:7
MAINTAINER Darius Jazayeri <darius@openmrs.org>

# Modified from https://github.com/mozart-analytics/grails-docker/blob/master/grails-2/Dockerfile

# Set Grails version
ENV GRAILS_VERSION 2.3.7

# Install Grails
WORKDIR /usr/lib/jvm
RUN wget https://github.com/grails/grails-core/releases/download/v$GRAILS_VERSION/grails-$GRAILS_VERSION.zip && \
    unzip grails-$GRAILS_VERSION.zip && \
    rm -rf grails-$GRAILS_VERSION.zip && \
    ln -s grails-$GRAILS_VERSION grails

# Setup Grails path.
ENV GRAILS_HOME /usr/lib/jvm/grails
ENV PATH $GRAILS_HOME/bin:$PATH

# Create App Directory
RUN mkdir /app

# Set Workdir
WORKDIR /app

# COPY . /app
# RUN grails dependency-report

# Set Default Behavior
ENTRYPOINT ["grails"]
