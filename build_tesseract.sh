#!/bin/bash
TESSERACT_FOLDER=""
cd $TESSERACT_FOLDER
export TESSERACT_PATH=${PWD}/external/tesseract-3.01
export LEPTONICA_PATH=${PWD}/external/leptonica-1.68
export LIBJPEG_PATH=${PWD}/external/libjpeg
ndk-build
android update project --path .
ant release
#This creates java libraries
