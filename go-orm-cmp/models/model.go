package models

import (
	//geom "github.com/twpayne/go-geom"
	geom "github.com/twpayne/go-geom/encoding/ewkb"
	"gorm.io/gorm"
)

type (
	Geo struct {
		ID   uint64            `gorm:"type:serial;primaryKey"`
		Name string            `gorm:"not null"`
		MP   geom.MultiPolygon `gorm:"type:geometry(MULTIPOLYGON, 4326);not null"`
		Kind string            `gorm:"type:geokind;not null"`
		//Kind string            `gorm:"type:geokind[];not null"`
	}
)

func (g Geo) TableName() string { return "geo_gorm" }

func Select(db *gorm.DB) ([]Geo, error) {
	var gs []Geo
	if result := db.Table("geo").Select("id", "name", "ST_AsEWKB(mp) as mp", "kind").Find(&gs); result.Error != nil {
		return nil, result.Error
	}
	return gs, nil
}
