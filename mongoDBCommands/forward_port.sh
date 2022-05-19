#!/bin/bash
# This gives you access to the MongoDB server running at thimblesoft.com
# To connect to the server, use localhost:27018

ssh ling@thimblesoft.com -L 27018:localhost:27017
