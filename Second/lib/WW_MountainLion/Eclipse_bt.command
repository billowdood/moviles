#!/bin/bash
DIR=$( cd "$( dirname "$0" )" && pwd )
export DYLD_LIBRARY_PATH=$DIR
cd ~/Documents/1_Stuff/1_Programming/0_Android/adt-bundle-mac-x86_64-20131030/eclipse/Eclipse.app/Contents/MacOS
./eclipse