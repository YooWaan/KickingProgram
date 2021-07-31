package main

import (
	"flag"
	"fmt"
)

func main() {
	n := flag.Int("n", 5, "count max")
	v := flag.Int("v", 2, "search number")
	flag.Parse()

	data := make([]int, *n)

	for i := range data {
		_, err := fmt.Scan(&data[i])
		if err != nil {
			println(fmt.Sprintf("err $v", err))
			return
		}
	}

	exists := false
	for _, d := range data {
		if d == *v {
			exists = true
		}
	}

	if exists {
		println("yes")
	} else {
		println("no")
	}
}
