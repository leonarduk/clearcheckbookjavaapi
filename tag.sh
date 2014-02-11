#!/bin/bash
TAG=${1?-Usage $0 TAG e.g. $0 0.3}
svn cp  svn+ssh://leonarduk@svn.code.sf.net/p/clearcheckbookjavaapi/code/trunk  svn+ssh://leonarduk@svn.code.sf.net/p/clearcheckbookjavaapi/code/tags/$TAG
