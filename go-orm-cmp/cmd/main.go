package main

import (
	"flag"
	"log"

	"comp.exp/db/models"
)

func main() {

	mg := flag.Bool("m", false, "migration or no")

	flag.Parse()

	db, err := models.Conn()
	if err != nil {
		log.Fatal(err)
	}

	if *mg {
		if err := db.AutoMigrate(&models.Geo{}); err != nil {
			log.Fatal(err)
		}
	} else {

		rs, err := models.Select(db)
		if err != nil {
			log.Fatal(err)
		}

		for i, r := range rs {
			log.Printf("[%d] %v (%v)", i, r, r.MP.Coords())
		}
	}
}
