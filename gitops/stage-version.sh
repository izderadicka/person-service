#! /usr/bin/bash 

if [[ -z "$1" ]]; then
echo "Must provide version/relese name" >&2
exit 1
fi

echo "Releasing $1"

yq "(.spec.params[]| select(.name==\"release-name\").value) = \"$1\""  StagePipelineRun.yaml | oc create -f -