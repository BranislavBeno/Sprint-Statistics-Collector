# Sprint statistics

| GitLab-CI | Test coverage |
| --- | --- |
| [![pipeline status](https://gitlab.websupport.sk/biea/sprint-statistics/badges/master/pipeline.svg)](https://gitlab.websupport.sk/biea/sprint-statistics/commits/master) | [![coverage report](https://gitlab.websupport.sk/biea/sprint-statistics/badges/master/coverage.svg)](https://gitlab.websupport.sk/biea/sprint-statistics/commits/master) |


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
