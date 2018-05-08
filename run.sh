#!/usr/bin/env bash
xterm -e "./gradlew bootrun" &
xterm -e "cd website; npm start";