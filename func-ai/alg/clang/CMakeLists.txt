cmake_minimum_required(VERSION 3.20)

project( my_alg CXX )

#include_directories(./inc)

add_library( other OBJECT src/cmd.cpp src/current_point.cpp)
target_include_directories( other PRIVATE ./inc)
target_compile_options( other PUBLIC -w -Wall )
target_compile_features( other PUBLIC cxx_std_17)
set_target_properties( other PROPERTIES ARCHIVE_OUTPUT_DIRECTORY ./lib)

add_executable(runcmd src/main.cpp)
target_compile_options( runcmd PUBLIC -w -Wall )
target_compile_features( runcmd PUBLIC cxx_std_17)
target_link_libraries(runcmd other )
target_include_directories(runcmd PRIVATE ./inc)
