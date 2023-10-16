package db

import (
	"context"
	"log"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"

	"comp.exp/db/models"
)

const (
	dsn = "host=127.0.0.1 user=guser password=gpass dbname=gdb port=6444 sslmode=disable"
)

type (
	Fetch func(context.Context) error
)

func GormConn() (*gorm.DB, error) {
	return gorm.Open(postgres.Open(dsn), &gorm.Config{})
}

func Print(gs []models.Geo) {
	for i, g := range gs {
		log.Printf("[%d] %v (%v)", i, g, g.MP.Coords())
	}
}

func GormSelect(context.Context) error {
	db, err := GormConn()
	if err != nil {
		return err
	}

	rs, err := models.Select(db)
	if err != nil {
		return err
	}

	Print(rs)
	return nil
}
