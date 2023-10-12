package db

import (
	"context"
	"database/sql"

	"github.com/uptrace/bun"
	"github.com/uptrace/bun/dialect/pgdialect"
	"github.com/uptrace/bun/driver/pgdriver"

	"comp.exp/db/models"
)

func BunConn() *bun.DB {
	drvDsn := "postgres://guser:gpass@127.0.0.1:6444/gdb?sslmode=disable"
	sqldb := sql.OpenDB(pgdriver.NewConnector(pgdriver.WithDSN(drvDsn)))
	return bun.NewDB(sqldb, pgdialect.New())
}

func BunSelect(ctx context.Context) error {
	db := BunConn()

	gs, err := bunSelectGeo(ctx, db)
	if err != nil {
		return err
	}

	Print(gs)
	return nil
}

func bunSelectGeo(ctx context.Context, db *bun.DB) ([]models.Geo, error) {
	var gs []models.Geo
	err := db.NewSelect().
		ModelTableExpr("geo").
		Model(&gs).
		ColumnExpr("id, name, ST_AsEWKB(mp) as mp, kind").Scan(ctx)
	return gs, err
}
