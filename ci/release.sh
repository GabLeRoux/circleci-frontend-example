#!/usr/bin/env sh

set -e

if [[ ${CIRCLE_TAG} ]]; then
  echo '{"describe":"'${CIRCLE_TAG}'"}' > version.json
  cat version.json
else
  echo "running without tag"
  echo '{"describe":"untagged build number '${CIRCLE_BUILD_NUM}'"}' > version.json
  cat version.json
fi

cat main.css