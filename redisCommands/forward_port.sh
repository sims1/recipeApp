#!/bin/bash
# This gives you access to the Redis server running at thimblesoft.com
# To connect to the server, use localhost:6378

ssh ling@thimblesoft.com -L 6378:localhost:6379
