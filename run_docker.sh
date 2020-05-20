#!/bin/sh

# build docker image
#docker build -t beo1975/sprint-stats-collector:latest .

# run docker container
docker run -v log_data:/app/log -v conf_data:/app/conf --name sprint_stats_collector --network sprintstatsviewer_sprintnet --env APP_USER=user --env APP_PASSWD=passwd --rm beo1975/sprint-stats-collector:latest
