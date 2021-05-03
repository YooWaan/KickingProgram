package main

import (
	"fmt"
	"flag"
)


func on() {
	var N int
	fmt.Scan(&N)
	count := 0

	for i := 0; i < N ; i++ {
		count++
	}

	fmt.Println(count)
}

func main() {
	r := flag.String("n", "on", "run func name")
	n := flag.Int("c", "count", "calc count")

	flag.Parse()

	switch *r {
	case "on":
		on()
	case "cp":
		CurretnPoint(n)
	}
}
