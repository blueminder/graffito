# graffito

Anonymous tagging via geolocation.

## About

**graffito** is a mobile webapp that allows you to leave messages in any physical location for other users to find later.

This webapp was largely created as an excuse to experiment with Scalatra, MongoDB, and iUI to make mobile web applications.

## Running graffito

Make sure you have mongod running on your machine in order to connect to the local database.

From there, run the SBT launcher and have it download all the necessary libraries:

`./sbt
reload
update`

Once that's done, just run the following commands in the SBT launcher:

`jetty-run
~prepare-webapp`

You should be able to navigate to the page by hitting port 8080 of your network address. If you're testing on your own machine, http://127.0.0.1:8080/ should do.

## TODO

- Limit displayed tags to match distance radius around user
- Create map link for each tag coordinate

Copyright &copy; 2008-2011 Enrique Santos<br />
This program is released under the terms of the GNU General Public License v3.0
