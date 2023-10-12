package main

import (
	"context"
	"flag"
	"log"

	"comp.exp/db"
	"comp.exp/db/models"
)

func main() {

	mg := flag.Bool("m", false, "migration or no")
	ormType := flag.String("orm", "gorm", "orm type")

	flag.Parse()

	if *mg {
		db, err := db.GormConn()
		if err != nil {
			log.Fatal(err)
		}

		if err := db.AutoMigrate(&models.Geo{}); err != nil {
			log.Fatal(err)
		}
		return
	}

	ctx := context.Background()
	var fn db.Fetch

	if *ormType == "gorm" {
		fn = db.GormSelect
	} else if *ormType == "bun" {
		fn = db.BunSelect
	}

	if err := fn(ctx); err != nil {
		log.Fatal(err)
	}
}
