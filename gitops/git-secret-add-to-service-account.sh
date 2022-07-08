#! /usr/bin/bash

oc patch sa pipeline -p '{"secrets": [{"name": "git-user-pass"}]}'