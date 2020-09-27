#!/bin/bash

#The main way to access your Rpi Server through SocketWhisper will be to ssh into your Rpi from a pc on your
#local network. This software should be cloned onto your rpi and any client pc you want to use - the simple
#way to do that is to ssh into your Rpi and git clone the SocketWhisper repository through the Rpi terminal.
#Once cloned, you should have access to this script, so welcome to the SocketWhisper. Running SocketWhisper
#server once you've ssh into your Rpi should be as simple as a click or a few strokes of the keyboard. As you
#know, you won't easily have access to Rpi Desktop to run this program, but you don't need it. This script
#does the job for you: navigating to the proper directory and running SocketWhisper for you. You'll want to
#run this from the scripts directory.
#
#Author: Wali Morris<walimmorris@gmail.com>
#Date  : 09/26/2020
##############################################################################################################

if cd ..; then                                 # Navigate to SocketWhisper
  echo "Starting SocketWhisper"
else
  echo "Backtrack: Fail"                       # Handles failure to backtrack
fi
if cd target/classes/; then                    # Navigates to Socket Whisper in production directory
  echo "Navigation: OK"
else
  echo "Navigation: Fail"
fi
java com.morris.SocketWhisper.Main;            # Runs SocketWhisper
