cmake_minimum_required(VERSION 2.8)


add_library(cli SHARED src/cli.cpp src/on.cpp)
add_executable(cmd src/cmd.cpp)

# add_executable(on2 on2.cpp)
target_link_libraries(cmd cli)

target_include_directories(cli
  PUBLIC ${PROJECT_SOURCE_DIR}/inc
)

target_compile_options(cmd PUBLIC -O2 -Wall)
target_compile_features(cmd PUBLIC cxx_std_17)
