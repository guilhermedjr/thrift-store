#!/usr/bin/env sh

set -e

lein deps
lein test
lein test :integration