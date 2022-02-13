cmake_minimum_required(VERSION 3.19)
project(swe)

set(CMAKE_CXX_STANDARD 14)

add_executable(swe main.cpp LRUCache.cpp)