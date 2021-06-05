package main

import (
	"flag"
	"fmt"
	"math"
)

// chapter 2.4

func calcDist(x1, x2, y1, y2 float64) float64 {
	return math.Sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2))
}

func CurrentPoint(n int) {
	x := make([]float64, n)
	y := make([]float64, n)

	for i := 0; i < n; i++ {
		read := 0.0
		fmt.Scan(&read)
		x[i] = read

		read = 0.0
		fmt.Scan(&read)
		y[i] = read
	}

	min_dist := 1000000000.0

	for i := 0; i < n ; i++ {
		for j := 0; j < n ; j++ {
			dist := calcDist(x[i], y[i], x[j], y[j])

			if dist < min_dist {
				min_dist = dist
			}
		}
	}

	fmt.Printf("min=%v\n", min_dist)
}

func main() {
	n := flag.Int("n", 5, "count max")
	flag.Parse()

	CurrentPoint(*n)
}
