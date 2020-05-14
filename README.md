# Sprint statistics

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Gradle](https://img.shields.io/badge/gradle-v6.4-blue)](https://img.shields.io/badge/gradle-v6.4-blue)
[![Build Status](https://travis-ci.org/BranislavBeno/Sprint-Statistics-Collector.svg?branch=master)](https://travis-ci.org/BranislavBeno/Sprint-Statistics-Collector)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=BranislavBeno_SprintStats&metric=alert_status)](https://sonarcloud.io/dashboard?id=BranislavBeno_SprintStats)
[![Coverage](https://img.shields.io/sonar/coverage/BranislavBeno_SprintStats?server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=BranislavBeno_SprintStats)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=BranislavBeno_SprintStats&metric=ncloc)](https://sonarcloud.io/dashboard?id=BranislavBeno_SprintStats)


Abstract
========
This README file describes the console application SprintStats,
which is used for automated sprint statistics gathering from issue tracker tool.
Collected results are according to tool settings sent to database and/or to Excel
file.
Precondition for automated data gathering is, that issue tracker tool
allows communication over REST API.

Table of Contents
=================
1. Installation
2. Usage
3. Known issues


Installation
===============
No installation is required. Just copy file "SprintStats.jar" into requested
directory.


Usage
========
All info, warning and error messages are issued to standard output stream
and to log file as well.

Following command line parameters are available for application usage:

Option                          Description:
---------------------------------------------
-u  | --user [username]         - defines user name for connection to issue
                                  tracker tool

-p  | --password [password]     - defines password for connection to issue
                                  tracker tool

-d  | --dbconnect               - collected data are send to database,
                                  when this parameter is used


Possible combinations of parameters:
------------------------------------
-d -u [username] -p [password]


Known issues
===============
No issues are known at this time.
